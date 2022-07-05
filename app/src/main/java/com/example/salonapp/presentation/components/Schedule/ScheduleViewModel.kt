package com.example.salonapp.presentation.components.Schedule

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salonapp.common.Constants
import com.example.salonapp.common.Resource
import com.example.salonapp.common.SessionManager
import com.example.salonapp.domain.use_cases.get_salons.GetSalonsByOwnerIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class  ScheduleViewModel @Inject constructor(
) : ViewModel(){
    private val _state = mutableStateOf(ScheduleState())
    val state: State<ScheduleState> = _state

    private val eventChannel = Channel<ScheduleEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onEvent(event: ScheduleEvent) {
        when(event) {
            is ScheduleEvent.OnNextWeek -> {
                val currentWeek = _state.value.currentWeek
                val nextWeek = currentWeek.plusDays(7)
                _state.value = _state.value.copy(currentWeek = nextWeek)
            }
            is ScheduleEvent.OnPreviousWeek -> {
                val currentWeek = _state.value.currentWeek
                val lastWeek = currentWeek.minusDays(7)
                _state.value = _state.value.copy(currentWeek = lastWeek)
            }
            is ScheduleEvent.OnSetCurrentWeek -> {
                _state.value = _state.value.copy(currentWeek = event.week)
            }

        }
    }

}