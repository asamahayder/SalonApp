package com.example.salonapp.presentation.owner.salon

import com.example.salonapp.domain.models.User

data class SalonCreateEditState (
    val salonId:Int? = null,
    val showAlert:Boolean = false,

    val name: String = "newsalon",
    val phone: String = "12345678",
    val email: String? = "newsalon@tst.com",
    val city: String = "gdfgdfgdfg",
    val postCode: String = "1234",
    val streetName: String = "dfgdfgdfgdfg",
    val streetNumber: String = "12",
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