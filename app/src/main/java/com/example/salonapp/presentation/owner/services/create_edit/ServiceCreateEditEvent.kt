package com.example.salonapp.presentation.owner.services.create_edit

sealed class ServiceCreateEditEvent {

    data class NameChanged(val name: String) : ServiceCreateEditEvent()
    data class DescriptionChanged(val description: String) : ServiceCreateEditEvent()
    data class PriceChanged(val price: String) : ServiceCreateEditEvent()
    data class DurationChanged(val duration: String) : ServiceCreateEditEvent()
    data class PauseStartChanged(val pauseStart: String) : ServiceCreateEditEvent()
    data class PauseEndChanged(val pauseEnd: String) : ServiceCreateEditEvent()
    data class Initialize(val serviceID: Int?) : ServiceCreateEditEvent()

    data class AddEmployee(val index:Int) : ServiceCreateEditEvent()
    data class RemoveEmployee(val index:Int) : ServiceCreateEditEvent()

    object Submit: ServiceCreateEditEvent()
    object ServiceCreatedOrEditedSuccessfully: ServiceCreateEditEvent()

    object OnToggleEmployeeMenu: ServiceCreateEditEvent()
    object OnEmployeeSelectDismiss: ServiceCreateEditEvent()

}