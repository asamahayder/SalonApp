package com.example.salonapp.data.remote.dtos

data class UserRegisterDTO(
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val password: String = "",
    val phone: String = ""
)