package com.example.salonapp.presentation.components.Schedule

import androidx.compose.ui.geometry.Size
import com.example.salonapp.domain.models.Booking
import com.example.salonapp.domain.models.Salon
import com.example.salonapp.domain.models.User
import java.time.LocalDateTime

data class ScheduleState (
    val currentWeek: LocalDateTime = LocalDateTime.now(),
    val bookings: List<Booking> = listOf(),

)