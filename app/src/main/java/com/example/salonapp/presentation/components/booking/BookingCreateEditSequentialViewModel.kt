//package com.example.salonapp.presentation.components.booking
//
//import android.util.Log
//import androidx.compose.runtime.State
//import androidx.compose.runtime.mutableStateOf
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.salonapp.common.Constants
//import com.example.salonapp.common.Resource
//import com.example.salonapp.common.SessionManager
//import com.example.salonapp.domain.models.Booking
//import com.example.salonapp.domain.models.ValidationResult
//import com.example.salonapp.domain.models.getEndOfDay
//import com.example.salonapp.domain.models.getStartOfDay
//import com.example.salonapp.domain.use_cases.booking.CreateBookingUseCase
//import com.example.salonapp.domain.use_cases.booking.GetBookingByIdUseCase
//import com.example.salonapp.domain.use_cases.booking.UpdateBookingUseCase
//import com.example.salonapp.domain.use_cases.salons.GetSalonUseCase
//import com.example.salonapp.domain.use_cases.opening_hours.GetOpeningHoursForEmployeeByWeekUseCase
//import com.example.salonapp.domain.use_cases.services.get_services.GetServicesByEmployeeIdUseCase
//import com.example.salonapp.domain.use_cases.user.get_user.GetUserUseCase
//import com.example.salonapp.domain.use_cases.validations.*
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.channels.Channel
//import kotlinx.coroutines.flow.launchIn
//import kotlinx.coroutines.flow.onEach
//import kotlinx.coroutines.flow.receiveAsFlow
//import kotlinx.coroutines.runBlocking
//import java.time.LocalDateTime
//import javax.inject.Inject
//
//@HiltViewModel
//class  BookingCreateEditSequentialViewModel @Inject constructor(
//    private val getUserUseCase: GetUserUseCase,
//    private val getBookingByIdUseCase: GetBookingByIdUseCase,
//    private val getSalonUseCase: GetSalonUseCase,
//    private val getOpeningHoursForEmployeeByWeekUseCase: GetOpeningHoursForEmployeeByWeekUseCase,
//    private val getServicesByEmployeeIdUseCase: GetServicesByEmployeeIdUseCase,
//    private val createBookingUseCase: CreateBookingUseCase,
//    private val updateBookingUseCase: UpdateBookingUseCase,
//    private val validateBookingNoteUseCase: ValidateBookingNoteUseCase,
//    private val sessionManager: SessionManager
//
//) : ViewModel(){
//
//    private val _state = mutableStateOf(BookingCreateEditState())
//    val state: State<BookingCreateEditState> = _state
//
//    private val eventChannel = Channel<BookingCreateEditEvent>()
//    val events = eventChannel.receiveAsFlow()
//
//
//    private fun getSalon(){
//        val salonId = _state.value.salonId
//        val employeeId = _state.value.employeeId
//        if (employeeId == null || salonId == null){
//            _state.value = _state.value.copy(isLoading = false, error = "Could not fetch employee or salon id")
//            return
//        }
//
//        runBlocking {
//            getSalonUseCase(salonId).onEach { result ->
//                when(result){
//                    is Resource.Success -> {
//                        val selectedEmployee = result.data!!.employees.first { it.id == employeeId }
//                        _state.value = _state.value.copy(
//                            selectedEmployee = selectedEmployee
//                        )
//
//                    }
//                    is Resource.Loading -> {
//                        _state.value = _state.value.copy(isLoading = true)
//
//                    }
//                    is Resource.Error -> {
//                        _state.value = _state.value.copy(isLoading = false, error = result.message ?: "Unexpected error")
//                    }
//                }
//            }
//        }.launchIn(viewModelScope)
//    }
//
//    private fun getServices(){
//        val selectedEmployeeId = _state.value.employeeId
//        if (selectedEmployeeId == null){
//            _state.value = _state.value.copy(isLoading = false, error = "Could not fetch employee id")
//            return
//        }
//        runBlocking {
//            getServicesByEmployeeIdUseCase(selectedEmployeeId).onEach { innerResult ->
//                when(innerResult){
//                    is Resource.Success -> {
//                        val selectableServices = innerResult.data!!
//                        _state.value = _state.value.copy(
//                            services = selectableServices,
//                            selectedService = if (selectableServices.isNotEmpty()) selectableServices[0] else null
//                        )
//
//
//
//                    }
//                    is Resource.Loading -> {
//                        _state.value = _state.value.copy(isLoading = true)
//
//                    }
//                    is Resource.Error -> {
//                        _state.value = _state.value.copy(isLoading = false, error = innerResult.message ?: "Unexpected error")
//                    }
//                }
//            }
//        }.launchIn(viewModelScope)
//
//
//    }
//
//    private fun getOpeningHours(){
//        val employeeId = _state.value.employeeId
//        if (employeeId == null){
//            _state.value = _state.value.copy(isLoading = false, error = "Could not fetch employee id")
//            return
//        }
//
//        val date = _state.value.dateTime
//        if (date == null){
//            _state.value = _state.value.copy(isLoading = false, error = "Could not fetch date")
//            return
//        }
//
//        runBlocking {
//            getOpeningHoursForEmployeeByWeekUseCase(employeeId, date).onEach { result ->
//                when(result){
//                    is Resource.Success -> {
//                        val startTime = result.data!!.getStartOfDay(date)
//                        val endTime = result.data.getEndOfDay(date)
//                        _state.value = _state.value.copy(startTimeOfDay = startTime, endTimeOfDay = endTime)
//                    }
//                    is Resource.Loading -> {
//                        _state.value = _state.value.copy(isLoading = true)
//
//                    }
//                    is Resource.Error -> {
//                        _state.value = _state.value.copy(isLoading = false, error = result.message ?: "Unexpected error")
//                    }
//                }
//            }
//
//        }.launchIn(viewModelScope)
//
//    }
//
//
//    private fun initialize(bookingId: Int?){
//        val employeeId = sessionManager.fetchEmployeeId()
//        val salonId = sessionManager.fetchSalonId()
//
//        _state.value = _state.value.copy(salonId = salonId, employeeId = employeeId, bookingId = bookingId)
//
//        getSalon()
//        getServices()
//        getOpeningHours()
//
//        _state.value = _state.value.copy(isLoading = false)
//
//        if (bookingId != null && _state.value.error.isNullOrEmpty()){
//            _state.value = _state.value.copy(bookingId = bookingId)
//            initializeEditBooking(bookingId)
//        }
//    }
//
//    private fun initializeEditBooking(bookingId: Int){
//        getBookingByIdUseCase(bookingId).onEach { result ->
//            when(result){
//                is Resource.Success -> {
//
//                    val booking = result.data!!
//
//                    _state.value = _state.value.copy(
//                        isLoading = false,
//                        error = null,
//                        selectedService = booking.service,
//                        note = booking.note,
//                        dateTime = booking.startTime
//                    )
//                    getOpeningHours()
//                }
//                is Resource.Loading -> {
//                    _state.value = _state.value.copy(
//                        isLoading = true
//                    )
//                }
//                is Resource.Error -> {
//                    _state.value = _state.value.copy(isLoading = false, error = result.message ?: "Unexpected error")
//                }
//            }
//        }.launchIn(viewModelScope)
//    }
//
//    fun onEvent(event: BookingCreateEditEvent) {
//        when(event) {
//            is BookingCreateEditEvent.OnNoteChanged ->{
//                _state.value = _state.value.copy(note = event.note)
//            }
//            is BookingCreateEditEvent.OnToggleServiceMenu ->{
//                _state.value = _state.value.copy(serviceSelectionExpanded = !_state.value.serviceSelectionExpanded)
//            }
//            is BookingCreateEditEvent.OnServiceSelectDismiss ->{
//                _state.value = _state.value.copy(serviceSelectionExpanded = false)
//            }
//            is BookingCreateEditEvent.OnSetActiveService ->{
//                _state.value = _state.value.copy(selectedService = event.service)
//            }
//            is BookingCreateEditEvent.OnToggleDateTimeMenu ->{
//                _state.value = _state.value.copy(dateTimeSelectionExpanded = !_state.value.dateTimeSelectionExpanded)
//            }
//            is BookingCreateEditEvent.OnDateTimeSelectDismiss ->{
//                _state.value = _state.value.copy(dateTimeSelectionExpanded = false)
//            }
//            is BookingCreateEditEvent.OnSetDate ->{
//                val currentDateTime = _state.value.dateTime
//                val newDateTime = LocalDateTime.of(event.date, currentDateTime?.toLocalTime())
//                _state.value = _state.value.copy(dateTime = newDateTime)
//                getOpeningHours()
//            }
//            is BookingCreateEditEvent.OnSetTime ->{
//                val currentDateTime = _state.value.dateTime
//                val newDateTime = LocalDateTime.of(currentDateTime!!.toLocalDate(), event.time)
//                _state.value = _state.value.copy(dateTime = newDateTime)
//            }
//            is BookingCreateEditEvent.Submit ->{
//                runValidation()
//            }
//            is BookingCreateEditEvent.Initialize ->{
//                initialize(event.bookingId)
//            }
//            is BookingCreateEditEvent.OnSetServiceSelectionWidth -> {
//                _state.value = _state.value.copy(serviceSelectionWidth = event.width)
//            }
//
//        }
//    }
//
//    fun runValidation(){
//
//        val validationResultList = mutableListOf<ValidationResult>()
//
//        val noteResult = validateBookingNoteUseCase.execute(_state.value.note)
//
//        val hasError = validationResultList.any { !it.successful }
//
//        if(hasError) {
//            _state.value = _state.value.copy(
//                noteError = noteResult.errorMessage
//            )
//            return
//        }
//
//        if (_state.value.bookingId != null){
//            editBooking()
//        }else{
//            createBooking()
//        }
//
//    }
//
//    private fun editBooking(){
//        if (_state.value.dateTime == null) return
//
//        getUserUseCase().onEach {result ->
//            when(result){
//                is Resource.Success -> {
//                    val bookedBy = result.data!!
//                    val updatedBooking = Booking(
//                        id = _state.value.bookingId!!,
//                        employee = _state.value.selectedEmployee!!,
//                        service = _state.value.selectedService!!,
//                        bookedBy = bookedBy,
//                        startTime = _state.value.dateTime!!,
//                        endTime = LocalDateTime.now(), //This is ignored in the backend as the endTime is calculated from the service duration
//                        note = _state.value.note,
//                        customer = null
//                    )
//
//                    updateBookingUseCase(updatedBooking).onEach { innerResult ->
//                        Log.i(Constants.LOGTAG_USECASE_RESULTS, innerResult.message?: "")
//                        Log.i(Constants.LOGTAG_USECASE_RESULTS, innerResult.data.toString())
//
//                        when(innerResult){
//                            is Resource.Success -> {
//                                _state.value = _state.value.copy(
//                                    error = null,
//                                    isLoading = false
//                                )
//                                eventChannel.send(BookingCreateEditEvent.OnBookingCreatedOrUpdated)
//                            }
//                            is Resource.Error -> {
//                                _state.value = _state.value.copy(
//                                    error = innerResult.message ?: "Unexpected error occurred",
//                                    isLoading = false
//                                )
//                            }
//                            is Resource.Loading -> {
//                                _state.value = _state.value.copy(isLoading = true)
//                            }
//                        }
//                    }
//
//                }
//                is Resource.Loading -> {
//                    _state.value = _state.value.copy(isLoading = true)
//
//                }
//                is Resource.Error -> {
//                    _state.value = _state.value.copy(isLoading = false, error = result.message ?: "Unexpected error")
//                }
//            }
//
//        }.launchIn(viewModelScope)
//
//    }
//
//    private fun createBooking(){
//        if (_state.value.dateTime == null) return
//        getUserUseCase().onEach {result ->
//            when(result){
//                is Resource.Success -> {
//                    val bookedBy = result.data!!
//                    val newBooking = Booking(
//                        id = 0, //ignored by backend as it will generated a new id
//                        employee = _state.value.selectedEmployee!!,
//                        service = _state.value.selectedService!!,
//                        bookedBy = bookedBy,
//                        startTime = _state.value.dateTime!!,
//                        endTime = LocalDateTime.now(), //This is ignored in the backend as the endTime is calculated from the service duration
//                        note = _state.value.note,
//                        customer = null //Only used when the customer himself is creating the booking
//                    )
//
//                    createBookingUseCase(newBooking).onEach { innerResult ->
//                        Log.i(Constants.LOGTAG_USECASE_RESULTS, innerResult.message?: "")
//                        Log.i(Constants.LOGTAG_USECASE_RESULTS, innerResult.data.toString())
//
//                        when(innerResult){
//                            is Resource.Success -> {
//                                _state.value = _state.value.copy(
//                                    error = null,
//                                    isLoading = false
//                                )
//                                eventChannel.send(BookingCreateEditEvent.OnBookingCreatedOrUpdated)
//                            }
//                            is Resource.Error -> {
//                                _state.value = _state.value.copy(
//                                    error = innerResult.message ?: "Unexpected error occurred",
//                                    isLoading = false
//                                )
//                            }
//                            is Resource.Loading -> {
//                                _state.value = _state.value.copy(isLoading = true)
//                            }
//                        }
//                    }.launchIn(viewModelScope)
//
//                }
//                is Resource.Loading -> {
//                    _state.value = _state.value.copy(isLoading = true)
//
//                }
//                is Resource.Error -> {
//                    _state.value = _state.value.copy(isLoading = false, error = result.message ?: "Unexpected error")
//                }
//            }
//
//        }.launchIn(viewModelScope)
//
//    }
//
//}