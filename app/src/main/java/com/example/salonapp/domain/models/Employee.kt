package com.example.salonapp.domain.models

data class Employee (
    val id: Int = 0,
    val salonId: Int? = null, //Due to cyclical dependencies, it is not possible to have a salon object here
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val phone: String = "",
    val role: String = ""
)