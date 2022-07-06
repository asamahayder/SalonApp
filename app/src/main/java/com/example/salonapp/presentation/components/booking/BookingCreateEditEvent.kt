package com.example.salonapp.presentation.components.booking

import androidx.compose.ui.geometry.Size
import com.example.salonapp.domain.models.Salon
import com.example.salonapp.domain.models.Service
import com.example.salonapp.presentation.owner.schedule.ScheduleEvent
import java.time.LocalDateTime

sealed class BookingCreateEditEvent {

    object OnToggleServiceMenu: BookingCreateEditEvent()
    object OnServiceSelectDismiss: BookingCreateEditEvent()
    data class OnSetActiveService(val service: Service): BookingCreateEditEvent()

    object OnToggleDateTimeMenu: BookingCreateEditEvent()
    object OnDateTimeSelectDismiss: BookingCreateEditEvent()
    data class OnSetDateTimeService(val dateString: String): BookingCreateEditEvent()

    data class OnNoteChanged(val note: String): BookingCreateEditEvent()

    object Submit: BookingCreateEditEvent()
    object OnBookingCreatedOrUpdated: BookingCreateEditEvent()
    data class Initialize(val bookingId: Int?): BookingCreateEditEvent()

    data class OnSetServiceSelectionWidth(val width: Size): BookingCreateEditEvent()
}