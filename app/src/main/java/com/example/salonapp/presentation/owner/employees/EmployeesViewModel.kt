package com.example.salonapp.presentation.owner.employees

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
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