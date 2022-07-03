package com.example.salonapp.presentation.authentication.register


data class RegisterState (
    val isLoading: Boolean = false,
    val emailRegister: String = "",
    val emailRegisterError: String? = null,
    val passwordRegister: String = "",
    val passwordRegisterError: String? = null,
    val passwordConfirm: String = "",
    val passwordConfirmError: String? = null,
    val firstName: String = "",
    val firstNameError: String? = null,
    val lastName: String = "",
    val lastNameError: String? = null,
    val phone: String = "",
    val phoneError: String? = null,
    val role: String = "",
    val message: String = "",
    val error: String = ""
)