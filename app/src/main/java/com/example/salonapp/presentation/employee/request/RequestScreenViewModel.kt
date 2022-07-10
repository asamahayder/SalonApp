package com.example.salonapp.presentation.employee.request

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salonapp.common.Constants
import com.example.salonapp.common.Resource
import com.example.salonapp.common.SessionManager
import com.example.salonapp.domain.models.Request
import com.example.salonapp.domain.models.RequestStatus
import com.example.salonapp.domain.models.Salon
import com.example.salonapp.domain.use_cases.booking.GetBookingByEmployeeIdUseCase
import com.example.salonapp.domain.use_cases.requests.*
import com.example.salonapp.domain.use_cases.salons.GetSalonUseCase
import com.example.salonapp.domain.use_cases.salons.GetSalonsByOwnerIdUseCase
import com.example.salonapp.domain.use_cases.salons.GetSalonsUseCase
import com.example.salonapp.domain.use_cases.user.get_user.GetEmployeeUseCase
import com.example.salonapp.presentation.owner.employees.EmployeesEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class  RequestScreenViewModel @Inject constructor(
    private val getSalonsUseCase: GetSalonsUseCase,
    private val getRequestsByEmployeeIdUseCase: GetRequestsByEmployeeIdUseCase,
    private val deleteRequestUseCase: DeleteRequestUseCase,
    private val createRequestUseCase: CreateRequestUseCase,
    private val getEmployeeUseCase: GetEmployeeUseCase,
    private val sessionManager: SessionManager
) : ViewModel(){

    private val _state = mutableStateOf(RequestScreenState())
    val state: State<RequestScreenState> = _state

    private val eventChannel = Channel<RequestScreenEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        initialize()
    }


    private fun deleteRequest(requestId:Int){
        deleteRequestUseCase(requestId).onEach {result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(isLoading = false)
                    eventChannel.send(RequestScreenEvent.OnRequestDeleted)
                    initialize()
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message ?: "Unknown error"
                    )
                }
            }


        }.launchIn(viewModelScope)


    }

    private fun createRequest(salonId:Int){
        createRequestUseCase(salonId).onEach {result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(isLoading = false)
                    eventChannel.send(RequestScreenEvent.OnRequestCreated)
                    initialize()
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message ?: "Unknown error"
                    )
                }
            }

        }.launchIn(viewModelScope)

    }

    private fun getSalons(){
        getSalonsUseCase().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(salons = result.data!!, isLoading = false, fetchedSalons = true)
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message ?: "Unknown error"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getRequests(){
        getRequestsByEmployeeIdUseCase().onEach {result ->
            when(result) {
                is Resource.Success -> {

                    var pendingRequest: Request?
                    try {
                        pendingRequest = result.data!!.first { it.requestStatus == RequestStatus.Pending }
                    }catch (e:NoSuchElementException){
                        pendingRequest = null
                    }

                    _state.value = _state.value.copy(pendingRequest = pendingRequest)

                    if (pendingRequest != null){
                        //Show the status of the
                        _state.value = _state.value.copy(isLoading = false, fetchedSalons = true)
                    }else{
                        getSalons()
                    }


                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message ?: "Unknown error"
                    )
                }

            }

        }.launchIn(viewModelScope)
    }

    private fun initialize(){
        //Clearing state
        _state.value = RequestScreenState()
        getRequests()

    }

    fun onEvent(event: RequestScreenEvent) {
        when(event) {
            is RequestScreenEvent.OnInitialize -> {
                initialize()
            }
            is RequestScreenEvent.OnCreateRequest -> {
                createRequest(_state.value.selectedSalon!!.id)
            }
            is RequestScreenEvent.OnDeleteRequest -> {
                deleteRequest(_state.value.pendingRequest!!.id)
            }
            is RequestScreenEvent.OnShowCreateRequestDialog -> {
                _state.value = _state.value.copy(showCreateRequestDialog = true, selectedSalon = event.salon)
            }
            is RequestScreenEvent.OnShowDeleteRequestDialog -> {
                _state.value = _state.value.copy(showDeleteRequestDialog = true)
            }
            is RequestScreenEvent.OnDismissCreateRequestDialog -> {
                _state.value = _state.value.copy(showCreateRequestDialog = false)
            }
            is RequestScreenEvent.OnDismissDeleteRequestDialog -> {
                _state.value = _state.value.copy(showDeleteRequestDialog = false)
            }



        }
    }

}