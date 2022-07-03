package com.example.salonapp.presentation.owner.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class  ProfileViewModel @Inject constructor(

) : ViewModel(){

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    private val eventChannel = Channel<ProfileEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onEvent(event: ProfileEvent) {
        when(event) {
        }
    }

}