package com.example.salonapp.data.dtos

data class EmployeeDTO(
    val email: String = "",
    val firstName: String = "",
    val id: Int = 0,
    val salonId: Int? = 0,
    val lastName: String = "",
    val phone: String = "",
    val role: String = ""
)