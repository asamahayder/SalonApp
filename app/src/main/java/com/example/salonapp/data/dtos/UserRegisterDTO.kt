package com.example.salonapp.data.dtos

data class UserRegisterDTO(
    var email: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var password: String = "",
    var phone: String = ""
)