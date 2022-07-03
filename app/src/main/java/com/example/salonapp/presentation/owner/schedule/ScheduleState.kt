package com.example.salonapp.presentation.owner.schedule

import com.example.salonapp.domain.models.Salon

data class ScheduleState (
    val salons: List<Salon> = listOf(),
    val userRole: String = "",
    val isLoading: Boolean = false,
    val error: String = "",
    val fetchedSalons: Boolean = false
)