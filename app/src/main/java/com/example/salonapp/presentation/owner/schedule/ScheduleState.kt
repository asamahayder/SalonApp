package com.example.salonapp.presentation.owner.schedule

import com.example.salonapp.domain.models.Salon
import com.example.salonapp.domain.models.User

data class ScheduleState (
    val activeSalon: Salon? = null,
    val activeEmployee: User? = null,
    val salons: List<Salon> = listOf(),
    val employees: List<User> = listOf(),
    val userRole: String = "",
    val isLoading: Boolean = false,
    val error: String = "",
    val fetchedSalons: Boolean = false,
    val salonSelectionExpanded: Boolean = false,
    val employeeSelectionExpanded: Boolean = false
)