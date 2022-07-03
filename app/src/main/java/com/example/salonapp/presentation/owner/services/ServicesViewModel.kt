package com.example.salonapp.presentation.owner.services

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class  ServicesViewModel @Inject constructor(

) : ViewModel(){

    private val _state = mutableStateOf(ServicesState())
    val state: State<ServicesState> = _state

    private val eventChannel = Channel<ServicesEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onEvent(event: ServicesEvent) {
        when(event) {
        }
    }

}