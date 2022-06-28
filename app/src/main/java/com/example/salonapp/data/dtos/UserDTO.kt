package com.example.salonapp.data.dtos

import com.example.salonapp.domain.models.User

data class UserDTO(
    val email: String = "",
    val firstName: String = "",
    val id: Int = 0,
    val lastName: String = "",
    val phone: String = "",
    val role: String = ""
)