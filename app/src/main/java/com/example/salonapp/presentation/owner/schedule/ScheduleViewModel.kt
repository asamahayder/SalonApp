package com.example.salonapp.presentation.owner.schedule

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salonapp.common.Constants
import com.example.salonapp.common.Resource
import com.example.salonapp.common.SessionManager
import com.example.salonapp.domain.models.Salon
import com.example.salonapp.domain.use_cases.booking.GetBookingByEmployeeIdUseCase
import com.example.salonapp.domain.use_cases.salons.GetSalonsByOwnerIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class  ScheduleViewModel @Inject constructor(
    private val getSalonsByOwnerIdUseCase: GetSalonsByOwnerIdUseCase,
    private val getBookingByEmployeeIdUseCase: GetBookingByEmployeeIdUseCase,
    private val sessionManager: SessionManager
) : ViewModel(){

    private val _state = mutableStateOf(ScheduleState())
    val state: State<ScheduleState> = _state

    private val eventChannel = Channel<ScheduleEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        initialize()
    }

    private fun initialize(){
        val userID: Int? = sessionManager.fetchUserId()

        if (userID != null){
            getSalonsByOwnerIdUseCase(userID).onEach { result ->

                Log.i(Constants.LOGTAG_USECASE_RESULTS, result.message?: "")
                Log.i(Constants.LOGTAG_USECASE_RESULTS, result.data.toString())

                when(result){
                    is Resource.Success -> {

                        _state.value = _state.value.copy(
                            isLoading = false,
                            salons = result.data ?: listOf(),
                            fetchedSalons = true
                        )

                        if (!result.data.isNullOrEmpty()){

                            val salonID = sessionManager.fetchSalonId()
                            val activeSalon: Salon =
                                if (salonID != null) result.data.first { it.id == salonID }
                                else result.data[0]

                            onEvent(ScheduleEvent.OnSetActiveSalon(activeSalon))

                            if (_state.value.activeSalon?.employees?.isNotEmpty() == true){
                                onEvent(ScheduleEvent.OnSetActiveEmployee(_state.value.activeSalon!!.employees[0]))
                            }

                        }
                        else{
                            eventChannel.send(ScheduleEvent.OnCreateSalon)
                        }
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(isLoading = false, error = result.message ?: "Unknown error")
                    }
                }
            }.launchIn(viewModelScope)
        }else{
            _state.value = ScheduleState(error = "User id could not be fetched.")
        }
    }

    fun onEvent(event: ScheduleEvent) {
        when(event) {
            is ScheduleEvent.OnToggleSalonMenu -> {
                _state.value = _state.value.copy(salonSelectionExpanded = !state.value.salonSelectionExpanded)
            }
            is ScheduleEvent.OnSalonSelectDismiss -> {
                _state.value = _state.value.copy(salonSelectionExpanded = false)
            }
            is ScheduleEvent.OnSetActiveSalon -> {
                _state.value = _state.value.copy(
                    activeSalon = event.salon,
                    salonSelectionExpanded = false,
                    employees = event.salon.employees
                )

                sessionManager.saveSalonId(event.salon.id)

                if (!event.salon.employees.isNullOrEmpty()){
                    onEvent(ScheduleEvent.OnSetActiveEmployee(event.salon.employees[0]))
                }

            }
            is ScheduleEvent.OnToggleEmployeeMenu -> {
                _state.value = _state.value.copy(employeeSelectionExpanded = !state.value.employeeSelectionExpanded)
            }
            is ScheduleEvent.OnEmployeeSelectDismiss -> {
                _state.value = _state.value.copy(employeeSelectionExpanded = false)
            }
            is ScheduleEvent.OnSetActiveEmployee -> {
                _state.value = _state.value.copy(activeEmployee = event.employee, employeeSelectionExpanded = false)
                sessionManager.saveEmployeeId(event.employee.id)
                getBookings(event.employee.id)
            }
            is ScheduleEvent.OnSetSalonSelectionWidth -> {
                _state.value = _state.value.copy(salonSelectionWidth = event.width)
            }
            is ScheduleEvent.OnSetEmployeeSelectionWidth -> {
                _state.value = _state.value.copy(employeeSelectionWidth = event.width)
            }
            is ScheduleEvent.OnWeekChanged -> {
                _state.value = _state.value.copy(currentWeek = event.newWeek)
            }
            is ScheduleEvent.OnInitialize -> {
                initialize()
            }
            is ScheduleEvent.OnSetZoom -> {
                _state.value = _state.value.copy(scale = event.scale)
            }

        }
    }

    private fun getBookings(employeeId:Int){
        getBookingByEmployeeIdUseCase(employeeId).onEach {result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(isLoading = false, bookings = result.data!!)
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false, error = result.message ?: "Unknown error")
                }
            }
        }.launchIn(viewModelScope)
    }

}