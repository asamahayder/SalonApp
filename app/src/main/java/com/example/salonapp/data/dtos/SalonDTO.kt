package com.example.salonapp.data.dtos

import com.example.salonapp.domain.models.Salon

data class SalonDTO(
    val city: String = "",
    val door: String = "",
    val email: String = "",
    val id: Int = 0,
    val phone: String = "",
    val postCode: String = "",
    val streetName: String = "",
    val streetNumber: String = "",
    val suit: String = ""
)