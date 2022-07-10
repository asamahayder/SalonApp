package com.example.salonapp.presentation.employee.schedule

import java.time.LocalDateTime

sealed class EmployeeScheduleEvent {


    object OnInitialize: EmployeeScheduleEvent()

    data class OnWeekChanged(val newWeek: LocalDateTime): EmployeeScheduleEvent()

    data class OnSetZoom(val scale: Int): EmployeeScheduleEvent()

    object OnGoToRequestCreation: EmployeeScheduleEvent()

}