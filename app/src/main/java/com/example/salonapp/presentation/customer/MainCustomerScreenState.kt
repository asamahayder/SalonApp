package com.example.salonapp.presentation.customer

import androidx.compose.ui.geometry.Size
import com.example.salonapp.domain.models.Booking
import com.example.salonapp.domain.models.Employee
import com.example.salonapp.domain.models.Salon
import com.example.salonapp.domain.models.User
import java.time.LocalDateTime

data class MainCustomerScreenState (

    val myBookings: List<Booking> = listOf(),
    val isLoading: Boolean = false,
    val error: String? = null,
)