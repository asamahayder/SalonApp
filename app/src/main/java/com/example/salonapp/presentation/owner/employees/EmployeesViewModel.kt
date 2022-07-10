package com.example.salonapp.presentation.owner.employees

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salonapp.common.Resource
import com.example.salonapp.common.SessionManager
import com.example.salonapp.domain.models.RequestStatus
import com.example.salonapp.domain.models.Salon
import com.example.salonapp.domain.use_cases.salons.GetSalonsByOwnerIdUseCase
import com.example.salonapp.domain.use_cases.requests.AcceptRequestUseCase
import com.example.salonapp.domain.use_cases.requests.DenyRequestUseCase
import com.example.salonapp.domain.use_cases.requests.GetRequestsBySalonIdUseCase
import com.example.salonapp.domain.use_cases.salons.UpdateSalonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class  EmployeesViewModel @Inject constructor(
    private val getSalonsByOwnerIdUseCase: GetSalonsByOwnerIdUseCase,
    private val updateSalonUseCase: UpdateSalonUseCase,
    private val getRequestsBySalonIdUseCase: GetRequestsBySalonIdUseCase,
    private val acceptRequestUseCase: AcceptRequestUseCase,
    private val denyRequestUseCase: DenyRequestUseCase,
    private val sessionManager: SessionManager

) : ViewModel(){

    private val _state = mutableStateOf(EmployeesState())
    val state: State<EmployeesState> = _state

    private val eventChannel = Channel<EmployeesEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        initialize()
    }

    private fun initialize(){
        _state.value = EmployeesState()

        val userID: Int? = sessionManager.fetchUserId()

        if (userID != null){

            getSalonsByOwnerIdUseCase(userID).onEach {result ->
                when(result){
                    is Resource.Success -> {

                        val salonID = sessionManager.fetchSalonId()
                        val activeSalon: Salon =
                            if (salonID != null) result.data?.first { it.id == salonID }!!
                            else result.data?.get(0)!!


                        _state.value = _state.value.copy(
                            salons = result.data,
                            activeSalon = activeSalon,
                            employees = activeSalon.employees
                        )

                        getRequests(activeSalon.id)

                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(isLoading = false, error = result.message ?: "Unexpected error")
                        eventChannel.send(EmployeesEvent.OnError(result.message ?: "Unexpected error"))
                    }
                }

            }.launchIn(viewModelScope)


        }else{
            _state.value = EmployeesState(error = "User id could not be fetched.")
        }
    }


    private fun acceptRequest(requestId:Int){
        acceptRequestUseCase(requestId).onEach { result ->
            when(result){
                is Resource.Success -> {

                    initialize()

                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false, error = result.message ?: "Unexpected error")
                    eventChannel.send(EmployeesEvent.OnError(result.message ?: "Unexpected error"))
                }
            }

        }.launchIn(viewModelScope)

    }

    private fun denyRequest(requestId:Int){
        denyRequestUseCase(requestId).onEach { result ->
            when(result){
                is Resource.Success -> {

                    initialize()

                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false, error = result.message ?: "Unexpected error")
                    eventChannel.send(EmployeesEvent.OnError(result.message ?: "Unexpected error"))
                }
            }

        }.launchIn(viewModelScope)
    }

    private fun removeEmployee(employeeId:Int){

        if (_state.value.activeSalon != null){
            val salon = _state.value.activeSalon

            val newEmployeeList = salon!!.employees.filter { employee -> employee.id != employeeId }

            val updatedSalon = salon.copy(employees = newEmployeeList)


            updateSalonUseCase(updatedSalon).onEach {result ->
                when(result){
                    is Resource.Success -> {

                        initialize()

                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(isLoading = false, error = result.message ?: "Unexpected error")
                        eventChannel.send(EmployeesEvent.OnError(result.message ?: "Unexpected error"))
                    }
                }
            }.launchIn(viewModelScope)

        }

    }

    private fun getRequests(salonId:Int){
        getRequestsBySalonIdUseCase(salonId).onEach {result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        requests = result.data!!.filter { it.requestStatus == RequestStatus.Pending }
                    )

                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false, error = result.message ?: "Unexpected error")
                    eventChannel.send(EmployeesEvent.OnError(result.message ?: "Unexpected error"))
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: EmployeesEvent) {
        when(event) {
            is EmployeesEvent.OnAcceptRequest -> {
                acceptRequest(event.requestId)
            }
            is EmployeesEvent.OnDenyRequest -> {
                denyRequest(event.requestId)
            }
            is EmployeesEvent.OnRemoveEmployeeFromSalon -> {
                removeEmployee(event.employeeId)
            }
            is EmployeesEvent.OnReload -> {
                initialize()
            }
            is EmployeesEvent.OnSetSalonSelectionWidth -> {
                _state.value = _state.value.copy(salonSelectionWidth = event.width)
            }
            is EmployeesEvent.OnSetActiveSalon -> {
                _state.value = _state.value.copy(
                    activeSalon = event.salon,
                    salonSelectionExpanded = false
                )
                sessionManager.saveSalonId(_state.value.activeSalon!!.id)
                initialize()
            }
            is EmployeesEvent.OnToggleSalonMenu -> {
                _state.value = _state.value.copy(salonSelectionExpanded = !state.value.salonSelectionExpanded)
            }
            is EmployeesEvent.OnSalonSelectDismiss -> {
                _state.value = _state.value.copy(salonSelectionExpanded = false)
            }
            is EmployeesEvent.OnInitialize -> {
                initialize()
            }
            is EmployeesEvent.OnShowAlert -> {
                _state.value = _state.value.copy(showDeleteAlert = true)
            }
            is EmployeesEvent.OnDismissAlert -> {
                _state.value = _state.value.copy(showDeleteAlert = false)
            }
            is EmployeesEvent.OnShowRequestDialog -> {
                _state.value = _state.value.copy(showRequestDialog = true)
            }
            is EmployeesEvent.OnDismissRequestDialog -> {
                _state.value = _state.value.copy(showRequestDialog = false)
            }

        }
    }



}