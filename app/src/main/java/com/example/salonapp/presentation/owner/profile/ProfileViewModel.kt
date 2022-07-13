package com.example.salonapp.presentation.owner.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salonapp.common.Resource
import com.example.salonapp.common.SessionManager
import com.example.salonapp.domain.use_cases.salons.GetSalonsByOwnerIdUseCase
import com.example.salonapp.domain.use_cases.user.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class  ProfileViewModel @Inject constructor(
    private val getSalonsByOwnerIdUseCase: GetSalonsByOwnerIdUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val sessionManager: SessionManager

) : ViewModel(){

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    private val eventChannel = Channel<ProfileEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        initialize()
    }

    private fun getUser(){
        getUserUseCase().onEach { result ->

            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(user = result.data!!, error = null)
                    getSalons()

                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)

                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false, error = "Error, try again.")
                }
            }

        }.launchIn(viewModelScope)
    }

    private fun getSalons(){
        val userID: Int? = sessionManager.fetchUserId()

        if (userID != null){

            getSalonsByOwnerIdUseCase(userID).onEach {result ->
                when(result){
                    is Resource.Success -> {

                        _state.value = _state.value.copy(
                            salons = result.data ?: listOf(),
                            isLoading = false,
                            error = null,
                        )
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
            _state.value = ProfileState(error = "User id could not be fetched.")
        }
    }

    private fun initialize(){
        _state.value = ProfileState()

        getUser()
    }


    fun onEvent(event: ProfileEvent) {
        when(event) {
            is ProfileEvent.OnInitialize ->{
                initialize()
            }

        }
    }



}