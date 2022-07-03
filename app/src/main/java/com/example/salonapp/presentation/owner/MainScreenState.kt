package com.example.salonapp.presentation.owner

import com.example.salonapp.domain.models.Salon

data class MainScreenState (
    val salons: List<Salon> = listOf(),
    val userRole: String = "",
    val isLoading: Boolean = false,
    val error: String = "",
    val fetchedSalons: Boolean = false
)

//enum class HomeScreen{
//    Login,
//    RegisterRoleSelection,
//    RegisterDetails
//}