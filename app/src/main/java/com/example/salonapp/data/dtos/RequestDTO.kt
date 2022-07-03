package com.example.salonapp.data.dtos

data class RequestDTO(
    val date: String,
    val employeeId: Int,
    val id: Int,
    val requestStatus: Int,
    val salonId: Int
)