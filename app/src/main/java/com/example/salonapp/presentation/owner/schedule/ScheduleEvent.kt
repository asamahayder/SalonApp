package com.example.salonapp.presentation.owner.schedule

import androidx.compose.ui.geometry.Size
import com.example.salonapp.domain.models.Salon
import com.example.salonapp.domain.models.User
import java.time.LocalDateTime

sealed class ScheduleEvent {
    object OnCreateSalon: ScheduleEvent()
    object OnToggleSalonMenu: ScheduleEvent()
    object OnSalonSelectDismiss: ScheduleEvent()
    data class OnSetActiveSalon(val salon: Salon): ScheduleEvent()

    object OnToggleEmployeeMenu: ScheduleEvent()
    object OnEmployeeSelectDismiss: ScheduleEvent()
    data class OnSetActiveEmployee(val employee: User): ScheduleEvent()

    data class OnSetSalonSelectionWidth(val width: Size): ScheduleEvent()
    data class OnSetEmployeeSelectionWidth(val width: Size): ScheduleEvent()

    data class OnWeekChanged(val newWeek: LocalDateTime): ScheduleEvent()

}