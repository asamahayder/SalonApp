package com.example.salonapp.presentation.owner.services.create

import androidx.compose.ui.geometry.Size
import com.example.salonapp.domain.models.Salon
import com.example.salonapp.domain.models.Service
import com.example.salonapp.domain.models.User

data class CreateServiceState (

    val name: String = "",
    val description: String = "",
    val price: Int = 0,
    val DurationInMinutes: Int = 0,
    val PauseStartInMinutes: Int? = null,
    val PauseEndInMinutes: Int? = null,
    val employees: List<User> = listOf()


)