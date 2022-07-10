package com.example.salonapp.presentation.components.profile.salon_card

sealed class SalonCardEvent {

    data class OnInitialize(val salonId:Int): SalonCardEvent()

}