package com.example.salonapp.presentation.owner.schedule

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salonapp.common.Constants
import com.example.salonapp.common.Resource
import com.example.salonapp.common.SessionManager
import com.example.salonapp.domain.models.UserLogin
import com.example.salonapp.domain.use_cases.get_salons.GetSalonsByOwnerIdUseCase
import com.example.salonapp.domain.use_cases.login.LoginUseCase
import com.example.salonapp.domain.use_cases.validations.ValidateEmailUseCase
import com.example.salonapp.domain.use_cases.validations.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class  ScheduleViewModel @Inject constructor(
    private val getSalonsByOwnerIdUseCase: GetSalonsByOwnerIdUseCase,
    private val sessionManager: SessionManager
) : ViewModel(){

    private val _state = mutableStateOf(ScheduleState())
    val state: State<ScheduleState> = _state

    private val eventChannel = Channel<ScheduleEvent>()
    val events = eventChannel.receiveAsFlow()

    init {

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
                        if (_state.value.salons.isNullOrEmpty())eventChannel.send(ScheduleEvent.GoToCreateSalon)

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
        }
    }

}