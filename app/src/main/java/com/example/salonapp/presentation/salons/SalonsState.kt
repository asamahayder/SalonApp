package com.example.salonapp.presentation.salons

import com.example.salonapp.data.remote.dtos.UserRegisterDTO
import com.example.salonapp.domain.models.Salon

data class SalonsState (
    val isLoading: Boolean = false,
    val salons: List<Salon> = emptyList(),
    val error: String = ""
)