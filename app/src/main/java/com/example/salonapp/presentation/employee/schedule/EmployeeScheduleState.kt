package com.example.salonapp.presentation.employee.schedule

import androidx.compose.ui.geometry.Size
import com.example.salonapp.domain.models.Booking
import com.example.salonapp.domain.models.Employee
import com.example.salonapp.domain.models.Salon
import com.example.salonapp.domain.models.User
import java.time.LocalDateTime

data class EmployeeScheduleState (
    val salon: Salon? = null,
    val employee: Employee? = null,
    val userRole: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val fetchedSalon: Boolean = false,

    val currentWeek: LocalDateTime = LocalDateTime.now(),
    val bookings: List<Booking> = listOf(),

    val scale: Int = 64
)