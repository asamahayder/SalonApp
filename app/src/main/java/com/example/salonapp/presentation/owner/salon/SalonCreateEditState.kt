package com.example.salonapp.presentation.owner.salon

import com.example.salonapp.domain.models.User

data class SalonCreateEditState (
    val salonId:Int? = null,
    val showAlert:Boolean = false,

    val name: String = "",
    val phone: String = "",
    val email: String? = "",
    val city: String = "",
    val postCode: String = "",
    val streetName: String = "",
    val streetNumber: String = "",
    val suit: String? = "",
    val door: String? = "",
    val employees: List<User> = listOf(),

    val nameError: String? = null,
    val phoneError: String? = null,
    val emailError: String? = null,
    val cityError: String? = null,
    val postCodeError: String? = null,
    val streetNameError: String? = null,
    val streetNumberError: String? = null,
    val suitError: String? = null,
    val doorError: String? = null,

    val isLoading: Boolean = false,
    val error: String? = null
)