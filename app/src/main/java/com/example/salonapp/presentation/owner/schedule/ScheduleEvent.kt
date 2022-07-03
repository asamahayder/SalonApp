package com.example.salonapp.presentation.owner.schedule

import com.example.salonapp.domain.models.Salon
import com.example.salonapp.domain.models.User

sealed class ScheduleEvent {
    object OnCreateSalon: ScheduleEvent()
    object OnToggleSalonMenu: ScheduleEvent()
    object OnSalonSelectDismiss: ScheduleEvent()
    data class OnSetActiveSalon(val salon: Salon): ScheduleEvent()

    object OnToggleEmployeeMenu: ScheduleEvent()
    object OnEmployeeSelectDismiss: ScheduleEvent()
    data class OnSetActiveEmployee(val employee: User): ScheduleEvent()

}