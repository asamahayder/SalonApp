package com.example.salonapp.presentation.components.Schedule

import androidx.compose.ui.geometry.Size
import com.example.salonapp.domain.models.Salon
import com.example.salonapp.domain.models.User
import java.time.LocalDate
import java.time.LocalDateTime

sealed class ScheduleEvent {
    object OnNextWeek: ScheduleEvent()
    object OnPreviousWeek: ScheduleEvent()
    object OnWeekChanged: ScheduleEvent()
    data class OnSetCurrentWeek(val week: LocalDateTime): ScheduleEvent()



}