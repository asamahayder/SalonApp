package com.example.salonapp.presentation.owner.services

import androidx.compose.ui.geometry.Size
import com.example.salonapp.domain.models.Salon
import com.example.salonapp.presentation.owner.schedule.ScheduleEvent

sealed class ServicesEvent {
    object OnCreateService: ServicesEvent()
    object OnReload: ServicesEvent()
    data class OnEditService(val serviceId:Int): ServicesEvent()
    data class OnDeleteService(val serviceId:Int): ServicesEvent()
    data class OnError(val message: String): ServicesEvent()
    object OnToggleSalonMenu: ServicesEvent()
    object OnSalonSelectDismiss: ServicesEvent()
    data class OnSetSalonSelectionWidth(val width: Size): ServicesEvent()
    data class OnSetActiveSalon(val salon: Salon): ServicesEvent()

}