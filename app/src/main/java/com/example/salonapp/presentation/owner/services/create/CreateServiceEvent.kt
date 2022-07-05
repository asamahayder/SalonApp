package com.example.salonapp.presentation.owner.services.create

import androidx.compose.ui.geometry.Size
import com.example.salonapp.domain.models.Salon
import com.example.salonapp.presentation.owner.schedule.ScheduleEvent

sealed class CreateServiceEvent {
    object OnCreateService: CreateServiceEvent()
    object OnReload: CreateServiceEvent()
    data class OnEditService(val serviceId:Int): CreateServiceEvent()
    data class OnDeleteService(val serviceId:Int): CreateServiceEvent()
    data class OnError(val message: String): CreateServiceEvent()
    object OnToggleSalonMenu: CreateServiceEvent()
    object OnSalonSelectDismiss: CreateServiceEvent()
    data class OnSetSalonSelectionWidth(val width: Size): CreateServiceEvent()
    data class OnSetActiveSalon(val salon: Salon): CreateServiceEvent()

}