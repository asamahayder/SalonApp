package com.example.salonapp.domain.models

data class UserRegister (
    val email: String = "",
    val password: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val phone: String = "",
    val role: String = ""
)