package com.example.salonapp.presentation.components.profile.profile_card

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salonapp.common.Resource
import com.example.salonapp.common.SessionManager
import com.example.salonapp.domain.use_cases.salons.GetSalonUseCase
import com.example.salonapp.presentation.components.profile.salon_card.SalonCardEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class  SalonCardViewModel @Inject constructor(
    private val getSalonUseCase: GetSalonUseCase,

) : ViewModel(){

    private val _state = mutableStateOf(SalonCardState())
    val state: State<SalonCardState> = _state

    private val eventChannel = Channel<SalonCardEvent>()
    val events = eventChannel.receiveAsFlow()


    private fun initialize(salonId:Int){

        _state.value = SalonCardState(salonId = salonId)

        getSalonUseCase(salonId).onEach { result ->

            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(isLoading = false, salon = result.data!!, error = null)
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

    fun onEvent(event: SalonCardEvent) {
        when(event) {
            is SalonCardEvent.OnInitialize ->{
                initialize(event.salonId)
            }
        }
    }



}