package com.example.salonapp.data.dtos

import com.example.salonapp.domain.models.Salon

data class AuthResponseDTO(
    val message: String = "",
    val userId: Int?
)