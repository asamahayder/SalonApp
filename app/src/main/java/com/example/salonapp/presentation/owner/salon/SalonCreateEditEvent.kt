package com.example.salonapp.presentation.owner.salon

sealed class SalonCreateEditEvent {
    data class NameChanged(val name: String) : SalonCreateEditEvent()
    data class EmailChanged(val email: String) : SalonCreateEditEvent()
    data class PhoneChanged(val phone: String) : SalonCreateEditEvent()
    data class CityChanged(val city: String) : SalonCreateEditEvent()
    data class PostCodeChanged(val postCode: String) : SalonCreateEditEvent()
    data class StreetNameChanged(val streetName: String) : SalonCreateEditEvent()
    data class StreetNumberChanged(val streetNumber: String) : SalonCreateEditEvent()
    data class SuitChanged(val suit: String) : SalonCreateEditEvent()
    data class DoorChanged(val door: String) : SalonCreateEditEvent()

    data class OnInitialize(val salonId: Int?) : SalonCreateEditEvent()

    object OnDeleteSalon: SalonCreateEditEvent()
    object OnShowDeleteAlert: SalonCreateEditEvent()
    object OnDismissDeleteAlert: SalonCreateEditEvent()

    object Submit: SalonCreateEditEvent()
    object OnFinishedAction: SalonCreateEditEvent()
}