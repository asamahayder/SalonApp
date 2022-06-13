package com.example.salonapp.presentation.login_and_register

import com.example.salonapp.data.remote.dtos.UserRegisterDTO

data class RegisterState (
    val isLoading: Boolean = false,
    val userRegisterDTO: UserRegisterDTO = UserRegisterDTO(),
    val message: String = "",
    val error: String = ""
)