package com.example.salonapp.data.remote.dtos

import com.example.salonapp.domain.models.User

data class UserDTO(
    val email: String = "",
    val firstName: String = "",
    val id: Int = 0,
    val lastName: String = "",
    val phone: String = "",
    val role: String = ""
)

//TODO: move this to repositories and remove the import at the top
fun UserDTO.toUser(): User{
    return User(
        id = id,
        email = email,
        firstName = firstName,
        lastName = lastName,
        phone = phone,
        role = role
    )
}