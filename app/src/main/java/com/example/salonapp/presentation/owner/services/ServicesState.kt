package com.example.salonapp.presentation.owner.services

import androidx.compose.ui.geometry.Size
import com.example.salonapp.domain.models.Salon
import com.example.salonapp.domain.models.Service

data class ServicesState (

    val salons: List<Salon> = listOf(),
    val services: List<Service> = listOf(),
    val isLoading: Boolean = false,
    val error: String = "",
    val activeSalon: Salon? = null,
    val salonSelectionExpanded: Boolean = false,
    val salonSelectionWidth: Size = Size.Zero,
    val showDeleteAlert: Boolean = false
    )