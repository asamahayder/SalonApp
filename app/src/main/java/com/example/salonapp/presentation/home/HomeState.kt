package com.example.salonapp.presentation.home

import androidx.compose.animation.fadeIn
import com.example.salonapp.domain.models.Salon

data class HomeState (
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