package com.example.salonapp.presentation.components.booking

import androidx.compose.ui.geometry.Size
import com.example.salonapp.domain.models.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class BookingCreateEditState (

    val bookingId: Int? = null,
    val employeeId: Int? = null,
    val salonId: Int? = null,

    val booking:Booking? = null,
    val salon:Salon? = null,
    val employee:User? = null,

    val currentUser: User? = null,



    val salons:List<Salon> = listOf(),
    val chosenSalon:Salon? = null,
    val employees:List<User> = listOf(),
    val chosenEmployee:User? = null,
    val services:List<Service> = listOf(),
    val chosenService:Service? = null,
    val openingHours: OpeningHours? = null,
    val chosenDate:LocalDate? = null,
    val existingBookings:List<Booking> = listOf(),
    val availableTimes:List<LocalTime> = listOf(),
    val chosenTime:LocalTime? = null,

    val activeStep: Int = 0,

    val showDeleteAlert:Boolean = false,

    val isLoading: Boolean = false,
    val error: String? = null,
)
