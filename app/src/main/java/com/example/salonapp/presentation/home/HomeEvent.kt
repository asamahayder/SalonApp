package com.example.salonapp.presentation.home

sealed class HomeEvent {
    object GoToCreateSalon: HomeEvent()
    object FetchSalons: HomeEvent()

}