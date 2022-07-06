package com.example.salonapp.presentation.components.booking

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salonapp.common.Constants
import com.example.salonapp.common.Resource
import com.example.salonapp.common.SessionManager
import com.example.salonapp.domain.models.Booking
import com.example.salonapp.domain.models.ValidationResult
import com.example.salonapp.domain.use_cases.booking.CreateBookingUseCase
import com.example.salonapp.domain.use_cases.booking.GetBookingByIdUseCase
import com.example.salonapp.domain.use_cases.booking.UpdateBookingUseCase
import com.example.salonapp.domain.use_cases.get_salon.GetSalonUseCase
import com.example.salonapp.domain.use_cases.services.get_services.GetServicesByEmployeeIdUseCase
import com.example.salonapp.domain.use_cases.user.get_user.GetUserUseCase
import com.example.salonapp.domain.use_cases.validations.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class  BookingCreateEditViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getBookingByIdUseCase: GetBookingByIdUseCase,
    private val getSalonUseCase: GetSalonUseCase,
    private val getServicesByEmployeeIdUseCase: GetServicesByEmployeeIdUseCase,
    private val createBookingUseCase: CreateBookingUseCase,
    private val updateBookingUseCase: UpdateBookingUseCase,
    private val validateBookingNoteUseCase: ValidateBookingNoteUseCase,
    private val sessionManager: SessionManager

) : ViewModel(){

    private val _state = mutableStateOf(BookingCreateEditState())
    val state: State<BookingCreateEditState> = _state

    private val eventChannel = Channel<BookingCreateEditEvent>()
    val events = eventChannel.receiveAsFlow()


    private fun initializeEditBooking(bookingId: Int){
        getBookingByIdUseCase(bookingId).onEach { result ->
                when(result){
                    is Resource.Success -> {

                        val booking = result.data!!

                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = null,
                            selectedService = booking.service,
                            note = booking.note ?: "",
                            dateTime = booking.startTime
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(isLoading = false, error = result.message ?: "Unexpected error")
                    }
                }
        }.launchIn(viewModelScope)
    }


    private fun initialize(bookingId: Int?){
        val employeeId = sessionManager.fetchEmployeeId()
        val salonId = sessionManager.fetchSalonId()

        if (employeeId != null && salonId != null){
            getSalonUseCase(salonId).onEach { result ->
                when(result){
                    is Resource.Success -> {
                        val selectedEmployee = result.data!!.employees.first { it.id == employeeId }
                        _state.value = _state.value.copy(
                            selectedEmployee = selectedEmployee
                        )

                        getServicesByEmployeeIdUseCase(selectedEmployee.id).onEach { innerResult ->
                            when(innerResult){
                                is Resource.Success -> {
                                    val selectableServices = innerResult.data!!
                                    _state.value = _state.value.copy(
                                        services = selectableServices
                                    )

                                    if (bookingId != null){
                                        _state.value = _state.value.copy(bookingId = bookingId)
                                        initializeEditBooking(bookingId)
                                    }else{
                                        _state.value = _state.value.copy(
                                            isLoading = false,
                                            selectedService = if (selectableServices.isNotEmpty()) selectableServices[0] else null
                                        )
                                    }

                                }
                                is Resource.Loading -> {
                                    _state.value = _state.value.copy(isLoading = true)

                                }
                                is Resource.Error -> {
                                    _state.value = _state.value.copy(isLoading = false, error = innerResult.message ?: "Unexpected error")
                                }
                            }

                        }

                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)

                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(isLoading = false, error = result.message ?: "Unexpected error")
                    }
                }

            }.launchIn(viewModelScope)


        }else{
            _state.value = _state.value.copy(error = "Error, Employee or Salon ID not found")
        }
    }

    fun onEvent(event: BookingCreateEditEvent) {
        when(event) {
            is BookingCreateEditEvent.OnNoteChanged ->{
                _state.value = _state.value.copy(note = event.note)
            }
            is BookingCreateEditEvent.OnToggleServiceMenu ->{
                _state.value = _state.value.copy(serviceSelectionExpanded = !_state.value.serviceSelectionExpanded)
            }
            is BookingCreateEditEvent.OnServiceSelectDismiss ->{
                _state.value = _state.value.copy(serviceSelectionExpanded = false)
            }
            is BookingCreateEditEvent.OnSetActiveService ->{
                _state.value = _state.value.copy(selectedService = event.service)
            }
            is BookingCreateEditEvent.OnToggleDateTimeMenu ->{
                _state.value = _state.value.copy(dateTimeSelectionExpanded = !_state.value.dateTimeSelectionExpanded)
            }
            is BookingCreateEditEvent.OnDateTimeSelectDismiss ->{
                _state.value = _state.value.copy(dateTimeSelectionExpanded = false)
            }
            is BookingCreateEditEvent.OnSetDateTimeService ->{

                val dateTime = LocalDateTime.parse(event.dateString)

                _state.value = _state.value.copy(dateTime = dateTime)
            }
            is BookingCreateEditEvent.Submit ->{
                runValidation()
            }
            is BookingCreateEditEvent.Initialize ->{
                initialize(event.bookingId)
            }
            is BookingCreateEditEvent.OnSetServiceSelectionWidth -> {
                _state.value = _state.value.copy(serviceSelectionWidth = event.width)
            }

        }
    }

    fun runValidation(){

        val validationResultList = mutableListOf<ValidationResult>()

        val noteResult = validateBookingNoteUseCase.execute(_state.value.note)

        val hasError = validationResultList.any { !it.successful }

        if(hasError) {
            _state.value = _state.value.copy(
                noteError = noteResult.errorMessage
            )
            return
        }

        if (_state.value.bookingId != null){
            editBooking()
        }else{
            createBooking()
        }

    }

    private fun editBooking(){
        getUserUseCase().onEach {result ->
            when(result){
                is Resource.Success -> {
                    val bookedBy = result.data!!
                    val updatedBooking = Booking(
                        id = _state.value.bookingId!!,
                        employee = _state.value.selectedEmployee!!,
                        service = _state.value.selectedService!!,
                        bookedBy = bookedBy,
                        startTime = _state.value.dateTime ?: LocalDateTime.now(),
                        endTime = LocalDateTime.now(), //This is ignored in the backend as the endTime is calculated from the service duration
                        note = _state.value.note,
                        customer = null
                    )

                    updateBookingUseCase(updatedBooking).onEach { innerResult ->
                        Log.i(Constants.LOGTAG_USECASE_RESULTS, innerResult.message?: "")
                        Log.i(Constants.LOGTAG_USECASE_RESULTS, innerResult.data.toString())

                        when(innerResult){
                            is Resource.Success -> {
                                _state.value = _state.value.copy(
                                    error = null,
                                    isLoading = false
                                )
                                eventChannel.send(BookingCreateEditEvent.OnBookingCreatedOrUpdated)
                            }
                            is Resource.Error -> {
                                _state.value = _state.value.copy(
                                    error = innerResult.message ?: "Unexpected error occurred",
                                    isLoading = false
                                )
                            }
                            is Resource.Loading -> {
                                _state.value = _state.value.copy(isLoading = true)
                            }
                        }
                    }

                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)

                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false, error = result.message ?: "Unexpected error")
                }
            }

        }.launchIn(viewModelScope)

    }

    private fun createBooking(){
        getUserUseCase().onEach {result ->
            when(result){
                is Resource.Success -> {
                    val bookedBy = result.data!!
                    val newBooking = Booking(
                        id = 0, //ignored by backend as it will generated a new id
                        employee = _state.value.selectedEmployee!!,
                        service = _state.value.selectedService!!,
                        bookedBy = bookedBy,
                        startTime = _state.value.dateTime,
                        endTime = LocalDateTime.now(), //This is ignored in the backend as the endTime is calculated from the service duration
                        note = _state.value.note,
                        customer = null //Only used when the customer himself is creating the booking
                    )

                    createBookingUseCase(newBooking).onEach { innerResult ->
                        Log.i(Constants.LOGTAG_USECASE_RESULTS, innerResult.message?: "")
                        Log.i(Constants.LOGTAG_USECASE_RESULTS, innerResult.data.toString())

                        when(innerResult){
                            is Resource.Success -> {
                                _state.value = _state.value.copy(
                                    error = null,
                                    isLoading = false
                                )
                                eventChannel.send(BookingCreateEditEvent.OnBookingCreatedOrUpdated)
                            }
                            is Resource.Error -> {
                                _state.value = _state.value.copy(
                                    error = innerResult.message ?: "Unexpected error occurred",
                                    isLoading = false
                                )
                            }
                            is Resource.Loading -> {
                                _state.value = _state.value.copy(isLoading = true)
                            }
                        }
                    }

                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)

                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false, error = result.message ?: "Unexpected error")
                }
            }

        }.launchIn(viewModelScope)

    }

}