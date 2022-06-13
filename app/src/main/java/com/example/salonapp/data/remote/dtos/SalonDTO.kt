package com.example.salonapp.data.remote.dtos

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

//TODO: move this to repositories and remove the import at the top
fun SalonDTO.toSalon(): Salon {
    return Salon(
        id = id,
        city = city,
        door = door,
        email = email,
        phone = phone,
        postCode = postCode,
        streetName = streetName,
        streetNumber = streetNumber,
        suit = suit
    )
}