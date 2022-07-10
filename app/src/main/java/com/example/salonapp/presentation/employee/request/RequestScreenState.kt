package com.example.salonapp.presentation.employee.request

import androidx.compose.ui.geometry.Size
import com.example.salonapp.domain.models.*
import java.time.LocalDateTime

data class RequestScreenState (

    val salons:List<Salon> = listOf(),
    val employee: Employee? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val fetchedSalons: Boolean = false,

    val pendingRequest: Request? = null,

    val selectedSalon:Salon? = null,

    val showCreateRequestDialog:Boolean = false,
    val showDeleteRequestDialog:Boolean = false,
)