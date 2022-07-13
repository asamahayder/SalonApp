package com.example.salonapp.presentation.components.booking

import androidx.compose.ui.geometry.Size
import com.example.salonapp.domain.models.Booking
import com.example.salonapp.domain.models.Salon
import com.example.salonapp.domain.models.Service
import com.example.salonapp.domain.models.User
import com.example.salonapp.presentation.navigation.OwnerScreen
import com.example.salonapp.presentation.owner.schedule.ScheduleEvent
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

sealed class BookingCreateEditEvent {

    data class OnInitialize(val bookingId: Int?): BookingCreateEditEvent()
    data class OnInitializeEmployees(val salon:Salon): BookingCreateEditEvent()
    data class OnInitializeServices(val employee: User): BookingCreateEditEvent()
    data class OnInitializeDates(val service:Service): BookingCreateEditEvent()
    data class OnInitializeTimes(val date:LocalDate): BookingCreateEditEvent()
    data class OnInitializeSummary(val time:LocalTime): BookingCreateEditEvent()

    object OnSubmit: BookingCreateEditEvent()
    object OnSubmitSuccess: BookingCreateEditEvent()

    object OnDeleteBooking: BookingCreateEditEvent()
    object OnShowDeleteAlert: BookingCreateEditEvent()
    object OnDismissDeleteAlert: BookingCreateEditEvent()

    object OnBack:BookingCreateEditEvent()
    object OnReturnToPreviousScreen:BookingCreateEditEvent()




}