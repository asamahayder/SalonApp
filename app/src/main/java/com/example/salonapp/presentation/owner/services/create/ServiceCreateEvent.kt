package com.example.salonapp.presentation.owner.services.create

import androidx.compose.ui.geometry.Size
import com.example.salonapp.domain.models.Salon
import com.example.salonapp.presentation.owner.salon_create.SalonCreateEvent
import com.example.salonapp.presentation.owner.services.ServicesEvent

sealed class ServiceCreateEvent {

    data class NameChanged(val name: String) : ServiceCreateEvent()
    data class DescriptionChanged(val description: String) : ServiceCreateEvent()
    data class PriceChanged(val price: String) : ServiceCreateEvent()
    data class DurationChanged(val duration: String) : ServiceCreateEvent()
    data class PauseStartChanged(val pauseStart: String) : ServiceCreateEvent()
    data class PauseEndChanged(val pauseEnd: String) : ServiceCreateEvent()

    data class AddEmployee(val index:Int) : ServiceCreateEvent()
    data class RemoveEmployee(val index:Int) : ServiceCreateEvent()

    object Submit: ServiceCreateEvent()
    object ServiceCreatedSuccessfully: ServiceCreateEvent()

    object OnToggleEmployeeMenu: ServiceCreateEvent()
    object OnEmployeeSelectDismiss: ServiceCreateEvent()

}