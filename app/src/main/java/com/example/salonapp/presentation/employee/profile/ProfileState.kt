package com.example.salonapp.presentation.employee.profile

import androidx.compose.ui.geometry.Size
import com.example.salonapp.domain.models.*

data class ProfileState (

    val user: Employee? = null,
    val salon: Salon? = null,
    val isLoading: Boolean = false,
    val error: String? = null,

    )