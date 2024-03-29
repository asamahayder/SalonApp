package com.example.salonapp.presentation.owner.services

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salonapp.common.Resource
import com.example.salonapp.common.SessionManager
import com.example.salonapp.domain.models.Salon
import com.example.salonapp.domain.use_cases.salons.GetSalonsByOwnerIdUseCase
import com.example.salonapp.domain.use_cases.services.delete_service.DeleteServiceUseCase
import com.example.salonapp.domain.use_cases.services.get_services.GetServicesBySalonIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class  ServicesViewModel @Inject constructor(
    private val getSalonsByOwnerIdUseCase: GetSalonsByOwnerIdUseCase,
    private val getServicesBySalonIdUseCase: GetServicesBySalonIdUseCase,
    private val deleteServiceUseCase: DeleteServiceUseCase,
    private val sessionManager: SessionManager

) : ViewModel(){

    private val _state = mutableStateOf(ServicesState())
    val state: State<ServicesState> = _state

    private val eventChannel = Channel<ServicesEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        initialize()
    }

    private fun initialize(){
        val userID: Int? = sessionManager.fetchUserId()

        if (userID != null){

            getSalonsByOwnerIdUseCase(userID).onEach {result ->
                when(result){
                    is Resource.Success -> {

                        val salonID = sessionManager.fetchSalonId()
                        val activeSalon: Salon? =
                            if (salonID != null) result.data?.first { it.id == salonID }
                            else result.data?.get(0)


                        _state.value = _state.value.copy(
                            isLoading = false,
                            salons = result.data ?: listOf(),
                            activeSalon = activeSalon
                        )

                        getServices()


                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(isLoading = false, error = result.message ?: "Unexpected error")
                        eventChannel.send(ServicesEvent.OnError(result.message ?: "Unexpected error"))
                    }
                }

            }.launchIn(viewModelScope)


        }else{
            _state.value = ServicesState(error = "User id could not be fetched.")
        }
    }

    private fun getServices(){
        if (_state.value.activeSalon != null){
            getServicesBySalonIdUseCase(_state.value.activeSalon!!.id).onEach { result ->
                when(result){
                    is Resource.Success -> {
                        _state.value = _state.value.copy(isLoading = false, services = result.data ?: listOf())
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(error = result.message ?: "Could not fetch services",isLoading = false)
                        eventChannel.send(ServicesEvent.OnError("Could not fetch services"))
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun onEvent(event: ServicesEvent) {
        when(event) {
            is ServicesEvent.OnDeleteService ->{
                deleteService(event.serviceId)
            }
            is ServicesEvent.OnReload -> {
                initialize()
            }
            is ServicesEvent.OnSetSalonSelectionWidth -> {
                _state.value = _state.value.copy(salonSelectionWidth = event.width)
            }
            is ServicesEvent.OnSetActiveSalon -> {
                _state.value = _state.value.copy(
                    activeSalon = event.salon,
                    salonSelectionExpanded = false
                )
                sessionManager.saveSalonId(_state.value.activeSalon!!.id)
                getServices()
            }
            is ServicesEvent.OnToggleSalonMenu -> {
                _state.value = _state.value.copy(salonSelectionExpanded = !state.value.salonSelectionExpanded)
            }
            is ServicesEvent.OnSalonSelectDismiss -> {
                _state.value = _state.value.copy(salonSelectionExpanded = false)
            }
            is ServicesEvent.OnInitialize -> {
                initialize()
            }
            is ServicesEvent.OnShowAlert -> {
                _state.value = _state.value.copy(showDeleteAlert = true)
            }
            is ServicesEvent.OnDismissAlert -> {
                _state.value = _state.value.copy(showDeleteAlert = false)
            }

        }
    }

    private fun deleteService(serviceId:Int){
        deleteServiceUseCase(serviceId = serviceId).onEach {result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        services = result.data!!,
                        error = "",
                        isLoading = false,
                        showDeleteAlert = false
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "Unexpected error occurred",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }


        }.launchIn(viewModelScope)

    }

}