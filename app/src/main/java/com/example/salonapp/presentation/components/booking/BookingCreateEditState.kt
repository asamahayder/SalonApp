package com.example.salonapp.presentation.components.booking

import androidx.compose.ui.geometry.Size
import com.example.salonapp.domain.models.Salon
import com.example.salonapp.domain.models.Service
import com.example.salonapp.domain.models.User
import java.time.LocalDateTime

data class BookingCreateEditState (

    val bookingId: Int? = null,

    val selectedEmployee: User? = null,

    val serviceSelectionExpanded: Boolean = false,
    val selectedService: Service? = null,

    val services: List<Service> = listOf(),

    val dateTimeSelectionExpanded: Boolean = false,
    val dateTime: LocalDateTime = LocalDateTime.now(),

    val note: String = "",
    val noteError: String? = null,

    val isLoading: Boolean = false,
    val error: String? = null

    val serviceSelectionWidth: Size = Size.Zero,
)