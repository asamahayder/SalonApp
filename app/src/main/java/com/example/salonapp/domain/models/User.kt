package com.example.salonapp.domain.models

data class User (
    val id: Int = 0,
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val phone: String = "",
    val role: String = ""
)