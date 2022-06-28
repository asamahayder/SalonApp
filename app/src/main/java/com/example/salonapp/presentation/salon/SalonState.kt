package com.example.salonapp.presentation.salon

import com.example.salonapp.domain.models.Salon

data class SalonState (
    val isLoading: Boolean = false,
    val salon: Salon = Salon(),
    val error: String = ""
)