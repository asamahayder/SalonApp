package com.example.salonapp.presentation.owner.employees

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salonapp.common.Constants
import com.example.salonapp.common.Resource
import com.example.salonapp.domain.models.UserLogin
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
class  EmployeesViewModel @Inject constructor(

) : ViewModel(){

    private val _state = mutableStateOf(EmployeesState())
    val state: State<EmployeesState> = _state

    private val eventChannel = Channel<EmployeesEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onEvent(event: EmployeesEvent) {
        when(event) {
        }
    }

}