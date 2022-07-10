package com.example.salonapp.presentation.components.profile.profile_card

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salonapp.common.Resource
import com.example.salonapp.domain.use_cases.user.get_user.GetUserUseCase
import com.example.salonapp.presentation.components.profile.salon_card.SalonCardEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class  ProfileCardViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,

) : ViewModel(){

    private val _state = mutableStateOf(ProfileCardState())
    val state: State<ProfileCardState> = _state

    private val eventChannel = Channel<ProfileCardEvent>()
    val events = eventChannel.receiveAsFlow()


    private fun initialize(){

        _state.value = ProfileCardState()

        getUserUseCase().onEach { result ->

            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(isLoading = false, user = result.data!!, error = null)
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

    fun onEvent(event: ProfileCardEvent) {
        when(event) {
            is ProfileCardEvent.OnInitialize ->{
                initialize()
            }
        }
    }



}