package com.example.salonapp.presentation.employee.profile.hours_card

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salonapp.common.Resource
import com.example.salonapp.common.SessionManager
import com.example.salonapp.domain.use_cases.opening_hours.GetDefaultOpeningHoursUseCase
import com.example.salonapp.domain.use_cases.user.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class  HoursCardViewModel @Inject constructor(
    private val getDefaultOpeningHoursUseCase: GetDefaultOpeningHoursUseCase,
    private val sessionManager: SessionManager,

    ) : ViewModel(){

    private val _state = mutableStateOf(HoursCardState())
    val state: State<HoursCardState> = _state

    private val eventChannel = Channel<HoursCardEvent>()
    val events = eventChannel.receiveAsFlow()


    private fun initialize(){
        _state.value = HoursCardState()

        val userId = sessionManager.fetchUserId()
        if (userId != null){

            getDefaultOpeningHoursUseCase(userId).onEach { result ->

                when(result){
                    is Resource.Success -> {
                        _state.value = _state.value.copy(isLoading = false, openingHours = result.data!!, error = null)
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)

                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(isLoading = false, error = "Error, try again.")
                    }
                }

            }.launchIn(viewModelScope)

        }else{
            _state.value = _state.value.copy(error = "Error, try again.")
        }

    }

    fun onEvent(event: HoursCardEvent) {
        when(event) {
            is HoursCardEvent.OnInitialize ->{
                initialize()
            }
        }
    }



}