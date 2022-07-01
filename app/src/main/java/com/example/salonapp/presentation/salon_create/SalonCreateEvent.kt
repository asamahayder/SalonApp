package com.example.salonapp.presentation.salon_create

import com.example.salonapp.presentation.login_and_register.LoginAndRegisterEvent

sealed class SalonCreateEvent {
    data class NameChanged(val name: String) : SalonCreateEvent()
    data class EmailChanged(val email: String) : SalonCreateEvent()
    data class PhoneChanged(val phone: String) : SalonCreateEvent()
    data class CityChanged(val city: String) : SalonCreateEvent()
    data class PostCodeChanged(val postCode: String) : SalonCreateEvent()
    data class StreetNameChanged(val streetName: String) : SalonCreateEvent()
    data class StreetNumberChanged(val streetNumber: String) : SalonCreateEvent()
    data class SuitChanged(val suit: String) : SalonCreateEvent()
    data class DoorChanged(val door: String) : SalonCreateEvent()

    object Submit: SalonCreateEvent()
    object SalonCreatedSuccessfully: SalonCreateEvent()
}