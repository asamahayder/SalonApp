package com.example.salonapp.presentation.owner

sealed class MainScreenEvent {
    object GoToCreateSalon: MainScreenEvent()
    object FetchSalons: MainScreenEvent()

}