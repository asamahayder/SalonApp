package com.example.salonapp.presentation.authentication.login

data class LoginState (
    val emailLogin: String = "newowner@test.com",
    val emailLoginError: String? = null,
    val passwordLogin: String = "stringstringst",
    val passwordLoginError: String? = null,
    val message: String = "",
    val error: String = "",
    val role:String = "",
    val isLoading: Boolean = false,
)