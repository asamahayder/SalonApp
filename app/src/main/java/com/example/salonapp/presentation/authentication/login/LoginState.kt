package com.example.salonapp.presentation.authentication.login

data class LoginState (
    val emailLogin: String = "",
    val emailLoginError: String? = null,
    val passwordLogin: String = "",
    val passwordLoginError: String? = null,
    val message: String = "",
    val error: String = "",
    val role:String = "",
    val isLoading: Boolean = false,
)