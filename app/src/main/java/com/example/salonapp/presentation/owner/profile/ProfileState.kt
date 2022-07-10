package com.example.salonapp.presentation.owner.profile

import androidx.compose.ui.geometry.Size
import com.example.salonapp.domain.models.Request
import com.example.salonapp.domain.models.Salon
import com.example.salonapp.domain.models.Service
import com.example.salonapp.domain.models.User

data class ProfileState (

    val user:User? = null,
    val salons: List<Salon> = listOf(),
    val isLoading: Boolean = false,
    val error: String? = null,

)