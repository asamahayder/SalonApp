package com.example.salonapp.presentation.owner.services.create

import com.example.salonapp.domain.models.Salon
import com.example.salonapp.domain.models.User

data class ServiceCreateState (

    val salon: Salon? = null,
    val name: String = "",
    val description: String = "",
    val price: String = "",
    val durationInMinutes: String = "",
    val pauseStartInMinutes: String = "",
    val pauseEndInMinutes: String = "",
    val selectedEmployees: List<User> = listOf(),
    val selectableEmployees: List<User> = listOf(),
    val employeeSelectionExpanded: Boolean = false,


    val nameError: String? = null,
    val descriptionError: String? = null,
    val priceError: String? = null,
    val durationError: String? = null,
    val pauseError: String? = null,

    val isLoading: Boolean = false,
    val error: String? = null


)