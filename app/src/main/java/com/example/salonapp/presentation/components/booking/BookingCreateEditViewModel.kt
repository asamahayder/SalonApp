package com.example.salonapp.presentation.components.booking

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salonapp.common.Constants
import com.example.salonapp.common.Resource
import com.example.salonapp.common.SessionManager
import com.example.salonapp.domain.models.*
import com.example.salonapp.domain.use_cases.booking.*
import com.example.salonapp.domain.use_cases.opening_hours.GetDefaultOpeningHoursUseCase
import com.example.salonapp.domain.use_cases.salons.GetSalonUseCase
import com.example.salonapp.domain.use_cases.salons.GetSalonsUseCase
import com.example.salonapp.domain.use_cases.services.get_services.GetServicesByEmployeeIdUseCase
import com.example.salonapp.domain.use_cases.user.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class  BookingCreateEditViewModel @Inject constructor(
    private val getSalonsUseCase: GetSalonsUseCase,
    private val getSalonUseCase: GetSalonUseCase,
    private val getServicesByEmployeeIdUseCase: GetServicesByEmployeeIdUseCase,
    private val getDefaultOpeningHoursUseCase: GetDefaultOpeningHoursUseCase,
    private val getBookingByEmployeeIdUseCase: GetBookingByEmployeeIdUseCase,
    private val getBookingByIdUseCase: GetBookingByIdUseCase,
    private val createBookingUseCase: CreateBookingUseCase,
    private val updateBookingUseCase: UpdateBookingUseCase,
    private val deleteBookingUseCase: DeleteBookingUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val sessionManager: SessionManager,

) : ViewModel(){

    private val _state = mutableStateOf(BookingCreateEditState())
    val state: State<BookingCreateEditState> = _state

    private val eventChannel = Channel<BookingCreateEditEvent>()
    val events = eventChannel.receiveAsFlow()

    private fun initialize(bookingId:Int?, salonId:Int?, employeeId:Int?){
        _state.value = BookingCreateEditState()

        getUserUseCase().onEach {result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(currentUser = result.data!!)

                    if(bookingId != null){
                        //Go to 6th step and initialize all steps.
                        //We want to edit an already existing booking
                        getBookingByIdUseCase(bookingId).onEach {result ->
                            when(result){
                                is Resource.Success -> {
                                    _state.value = _state.value.copy(booking = result.data!!)
                                    initializeSalons(booking = result.data)
                                }
                                is Resource.Loading -> {
                                    _state.value = _state.value.copy(isLoading = true)
                                }
                                is Resource.Error -> {
                                    _state.value = _state.value.copy(isLoading = false, error = "Error, try again.", activeStep = 0)
                                }
                            }
                        }.launchIn(viewModelScope)

                    }
                    else if (salonId != null && employeeId != null){
                        //Go to 3rd step which is services
                        //We want to create a salon but we are an employee/owner, therefore the first 2 steps are locked.


                        getSalonUseCase(salonId).onEach {result ->
                            when(result){
                                is Resource.Success -> {
                                    val salon = result.data!!
                                    val employee = salon.employees.first { it.id == employeeId }

                                    _state.value = _state.value.copy(salon = salon, employee = employee)

                                    initializeSalons(salon = salon, employee = employee)

                                }
                                is Resource.Loading -> {
                                    _state.value = _state.value.copy(isLoading = true)
                                }
                                is Resource.Error -> {
                                    _state.value = _state.value.copy(isLoading = false, error = "Error, try again.", activeStep = 0)
                                }
                            }

                        }.launchIn(viewModelScope)

                    }else{
                        //Start from the 1st step
                        //We are a customer trying to create a booking.
                        initializeSalons()
                    }



                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false, error = "Error, try again.", activeStep = 0)
                }
            }

        }.launchIn(viewModelScope)

    }

    private fun initializeSalons(booking:Booking? = null, salon:Salon? = null, employee: User? = null){
        _state.value = _state.value.copy(chosenSalon = null, salons = listOf())

        getSalonsUseCase().onEach {result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(salons = result.data!!)
                    val salons = result.data

                    if (booking != null){

                        val chosenSalon = salons.first { salon ->
                            salon.employees.any {
                                    employee -> employee.id == booking.employee.id
                            }
                        }

                        _state.value = _state.value.copy(chosenSalon = chosenSalon)

                        initializeEmployees(booking = booking, employee = employee)
                    }
                    else if(salon != null){

                        _state.value = _state.value.copy(chosenSalon = salon)
                        initializeEmployees(booking = booking, employee = employee)
                    }
                    else{
                        _state.value = _state.value.copy(isLoading = false, error = null, salons = result.data, activeStep = 1)
                    }

                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false, error = "Error, try again.", activeStep = 0)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun initializeEmployees(booking:Booking? = null, employee: User? = null){
        _state.value = _state.value.copy(chosenEmployee = null, employees = listOf())

        getSalonUseCase(_state.value.chosenSalon!!.id).onEach {result ->
            when(result){
                is Resource.Success -> {

                    _state.value = _state.value.copy(employees = result.data!!.employees)
                    if (booking != null){
                        _state.value = _state.value.copy(chosenEmployee = booking.employee)
                        initializeServices(booking = booking)
                    }
                    else if(employee != null){
                        _state.value = _state.value.copy(chosenEmployee = employee)
                        initializeServices(booking = booking)
                    }
                    else{
                        _state.value = _state.value.copy(isLoading = false, error = null, activeStep = 2)
                    }
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false, error = "Error, try again.", activeStep = 0)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun initializeServices(booking:Booking? = null){
        _state.value = _state.value.copy(chosenService = null, services = listOf())
        getServicesByEmployeeIdUseCase(_state.value.chosenEmployee!!.id).onEach {result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(services = result.data!!)
                    if (booking != null){
                        _state.value = _state.value.copy(chosenService = booking.service)
                        initializeDates(booking)
                    }
                    else{
                        _state.value = _state.value.copy(isLoading = false, error = null,  activeStep = 3)
                    }

                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false, error = "Error, try again.", activeStep = 0)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun initializeDates(booking:Booking? = null){
        _state.value = _state.value.copy(chosenDate = null, openingHours = null)
        getDefaultOpeningHoursUseCase(_state.value.chosenEmployee!!.id).onEach {result ->
            when(result){
                is Resource.Success -> {

                    _state.value = _state.value.copy(openingHours = result.data!!)
                    if (booking != null){
                        _state.value = _state.value.copy(chosenDate = booking.startTime.toLocalDate())
                        initializeTimes(booking)
                    }else{
                        _state.value = _state.value.copy(isLoading = false, error = null,  activeStep = 4)
                    }

                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false, error = "Error, try again.", activeStep = 0)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun initializeTimes(booking:Booking? = null){
        _state.value = _state.value.copy(chosenTime = null, availableTimes = listOf())
        getBookingByEmployeeIdUseCase(_state.value.chosenEmployee!!.id).onEach {result ->
            when(result){
                is Resource.Success -> {

                    val bookings = result.data!!
                    val existingBookingsForChosenDate = bookings.filter { it.startTime.toLocalDate().isEqual(_state.value.chosenDate) }
                    _state.value = _state.value.copy(existingBookings = existingBookingsForChosenDate)

                    val availableTimes:List<LocalTime> = getAvailableTimes()

                    _state.value = _state.value.copy(availableTimes = availableTimes)

                    if (booking != null){
                        _state.value = _state.value.copy(isLoading = false, error = null, chosenTime = booking.startTime.toLocalTime(), activeStep = 6)
                    }else{
                        _state.value = _state.value.copy(isLoading = false, error = null, activeStep = 5)
                    }
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false, error = "Error, try again.", activeStep = 0)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getAvailableTimes(): List<LocalTime>{
        //enumerates through the day by 15 minute intervals starting from start of day to end of day
        //and checks if
        val possibleTimes:MutableList<LocalTime> = mutableListOf()
        val startTime = _state.value.openingHours!!.getStartOfDay(_state.value.chosenDate!!)
        val endTime = _state.value.openingHours!!.getEndOfDay(_state.value.chosenDate!!)
        val chosenService = _state.value.chosenService!!

        var currentTime = startTime.minusMinutes(15)

        while (!currentTime.isAfter(endTime)){

            currentTime = currentTime.plusMinutes(15)

            //Checks if before now and also for current day
            if (currentTime.isBefore(LocalTime.now()) && _state.value.chosenDate!!.isEqual(LocalDate.now())) continue

            //checks if booking goes over the end of day
            if (currentTime.plusMinutes(chosenService.durationInMinutes.toLong()).isAfter(endTime)){
                continue
            }


            //Checks if the booking is overlapping with any existing bookings
            //Checks if the service has a pause
            if (chosenService.pauseStartInMinutes != null && chosenService.pauseEndInMinutes != null){
                //We create to separate time ranges and check overlap for each
                val beforePauseStart = currentTime
                val beforePauseEnd = currentTime.plusMinutes(chosenService.pauseStartInMinutes.toLong())
                val afterPauseStart = currentTime.plusMinutes(chosenService.pauseEndInMinutes.toLong())
                val afterPauseEnd = currentTime.plusMinutes(chosenService.durationInMinutes.toLong())

                if (isOverlapping(_state.value.existingBookings, beforePauseStart, beforePauseEnd)) continue
                if (isOverlapping(_state.value.existingBookings, afterPauseStart, afterPauseEnd)) continue

            }else{
                val start = currentTime
                val end = currentTime.plusMinutes(chosenService.durationInMinutes.toLong())

                if (isOverlapping(_state.value.existingBookings, start, end)) continue
            }

            possibleTimes.add(currentTime)
        }

        return possibleTimes
    }

    private fun isOverlapping(existingBookings:List<Booking>, start: LocalTime, end: LocalTime): Boolean{
        existingBookings.forEach { booking ->
            if (
                booking.startTime.toLocalTime().isBefore(end)
                && start.isBefore(booking.endTime.toLocalTime())
            ){
                return true
            }
        }

        return false
    }

    private fun onReturnToPreviousScreen(){
        //this dont work
        runBlocking {
            launch {
                eventChannel.send(BookingCreateEditEvent.OnReturnToPreviousScreen)
            }
        }

    }

    fun onEvent(event: BookingCreateEditEvent) {
        when(event) {
            is BookingCreateEditEvent.OnInitialize -> {


                initialize(event.bookingId, sessionManager.fetchSalonId(), sessionManager.fetchEmployeeId())
            }
            is BookingCreateEditEvent.OnInitializeEmployees -> {
                _state.value = _state.value.copy(chosenSalon = event.salon)
                initializeEmployees()
            }
            is BookingCreateEditEvent.OnInitializeServices -> {
                _state.value = _state.value.copy(chosenEmployee = event.employee)
                initializeServices()
            }
            is BookingCreateEditEvent.OnInitializeDates -> {
                _state.value = _state.value.copy(chosenService = event.service)
                initializeDates()
            }
            is BookingCreateEditEvent.OnInitializeTimes -> {
                _state.value = _state.value.copy(chosenDate = event.date)
                initializeTimes()
            }
            is BookingCreateEditEvent.OnInitializeSummary -> {
                _state.value = _state.value.copy(chosenTime = event.time, activeStep = 6)
            }

            is BookingCreateEditEvent.OnBack -> {
                val currentUserRole = _state.value.currentUser!!.role

                if ((currentUserRole == Constants.ROLE_OWNER || currentUserRole == Constants.ROLE_EMPLOYEE)
                    && _state.value.activeStep == 3){
                    onReturnToPreviousScreen()
                }else if (_state.value.activeStep > 1){
                    val previousStep = _state.value.activeStep-1
                    _state.value = _state.value.copy(activeStep = previousStep)
                }else{
                    onReturnToPreviousScreen()
                }
            }
            is BookingCreateEditEvent.OnSubmit -> {
                if (_state.value.booking != null){
                    updateBooking()
                }else{
                    createBooking()
                }
            }
            is BookingCreateEditEvent.OnDeleteBooking -> {
                deleteBooking()
            }
            is BookingCreateEditEvent.OnShowDeleteAlert -> {
                _state.value = _state.value.copy(showDeleteAlert = true)

            }is BookingCreateEditEvent.OnDismissDeleteAlert -> {
                _state.value = _state.value.copy(showDeleteAlert = false)
            }

        }

    }

    private fun createBooking(){
        val currentUser = _state.value.currentUser!!

        val customer = if (currentUser.role == Constants.ROLE_CUSTOMER) currentUser else null

        val chosenTime = _state.value.chosenTime!!
        val chosenDate = _state.value.chosenDate!!
        val startTime = LocalDateTime.of(chosenDate, chosenTime)

        val newBooking = Booking(
            bookedBy = currentUser,
            customer = customer,
            employee = _state.value.chosenEmployee!!,
            service = _state.value.chosenService!!,
            startTime = startTime,
            endTime = LocalDateTime.now()
        )

        createBookingUseCase(newBooking).onEach { result ->
            when(result){
                is Resource.Success -> {
                    eventChannel.send(BookingCreateEditEvent.OnSubmitSuccess)
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false, error = "Error, try again.", activeStep = 0)
                }
            }

        }.launchIn(viewModelScope)

    }

    private fun updateBooking(){
        val chosenTime = _state.value.chosenTime!!
        val chosenDate = _state.value.chosenDate!!
        val startTime = LocalDateTime.of(chosenDate, chosenTime)

        val updatedBooking = Booking(
            id = _state.value.booking!!.id,
            bookedBy = _state.value.booking!!.bookedBy,
            customer = _state.value.booking!!.customer,
            employee = _state.value.chosenEmployee!!,
            service = _state.value.chosenService!!,
            startTime = startTime,
            endTime = LocalDateTime.now()
        )

        updateBookingUseCase(updatedBooking).onEach { result ->
            when(result){
                is Resource.Success -> {
                    eventChannel.send(BookingCreateEditEvent.OnSubmitSuccess)
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false, error = "Error, try again.", activeStep = 0)
                }
            }
        }.launchIn(viewModelScope)

    }

    private fun deleteBooking(){
        deleteBookingUseCase(_state.value.booking!!.id).onEach {result ->
            when(result){
                is Resource.Success -> {
                    eventChannel.send(BookingCreateEditEvent.OnSubmitSuccess)
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false, error = "Error, try again.", activeStep = 0)
                }
            }

        }.launchIn(viewModelScope)

    }







}