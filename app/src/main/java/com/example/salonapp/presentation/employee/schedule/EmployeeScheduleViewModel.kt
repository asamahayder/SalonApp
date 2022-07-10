package com.example.salonapp.presentation.employee.schedule

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
import com.example.salonapp.domain.use_cases.salons.GetSalonUseCase
import com.example.salonapp.domain.use_cases.salons.GetSalonsByOwnerIdUseCase
import com.example.salonapp.domain.use_cases.user.get_user.GetEmployeeUseCase
import com.example.salonapp.presentation.owner.employees.EmployeesEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class  EmployeeScheduleViewModel @Inject constructor(
    private val getEmployeeUseCase: GetEmployeeUseCase,
    private val getSalonUseCase: GetSalonUseCase,
    private val getBookingByEmployeeIdUseCase: GetBookingByEmployeeIdUseCase,
    private val sessionManager: SessionManager
) : ViewModel(){

    private val _state = mutableStateOf(EmployeeScheduleState())
    val state: State<EmployeeScheduleState> = _state

    private val eventChannel = Channel<EmployeeScheduleEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        initialize()
    }


    private fun initialize(){

        _state.value = EmployeeScheduleState()

        val userID: Int? = sessionManager.fetchUserId()

        if (userID != null){
            getEmployeeUseCase(userID).onEach { result ->

                Log.i(Constants.LOGTAG_USECASE_RESULTS, result.message?: "")
                Log.i(Constants.LOGTAG_USECASE_RESULTS, result.data.toString())

                when(result){
                    is Resource.Success -> {

                        _state.value = _state.value.copy(
                            employee = result.data!!
                        )

                        if (result.data.salonId != null){

                            getSalon(salonId = result.data.salonId, userID)

                        }else{
                            _state.value = _state.value.copy(isLoading = false, fetchedSalon = true)
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
            _state.value = EmployeeScheduleState(error = "User id could not be fetched.")
        }
    }

    fun onEvent(event: EmployeeScheduleEvent) {
        when(event) {

            is EmployeeScheduleEvent.OnWeekChanged -> {
                _state.value = _state.value.copy(currentWeek = event.newWeek)
            }
            is EmployeeScheduleEvent.OnInitialize -> {
                initialize()
            }
            is EmployeeScheduleEvent.OnSetZoom -> {
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

    private fun getSalon(salonId:Int, employeeId:Int){
        getSalonUseCase(salonId).onEach {result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(isLoading = false, salon = result.data!!)
                    getBookings(employeeId)
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