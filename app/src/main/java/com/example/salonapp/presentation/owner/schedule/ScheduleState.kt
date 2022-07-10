package com.example.salonapp.presentation.owner.schedule

import androidx.compose.ui.geometry.Size
import com.example.salonapp.domain.models.Booking
import com.example.salonapp.domain.models.Salon
import com.example.salonapp.domain.models.User
import java.time.LocalDateTime

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
    val employeeSelectionExpanded: Boolean = false,

    val salonSelectionWidth: Size = Size.Zero,
    val employeeSelectionWidth: Size = Size.Zero,

    val currentWeek: LocalDateTime = LocalDateTime.now(),

    val bookings: List<Booking> = listOf(),

    val scale: Int = 64
)