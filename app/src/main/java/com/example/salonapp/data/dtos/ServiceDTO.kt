package com.example.salonapp.data.dtos

data class ServiceDTO(
    val description: String,
    val durationInMinutes: Int,
    val employeesIds: List<Int>,
    val id: Int,
    val name: String,
    val pauseEndInMinutes: Int,
    val pauseStartInMinutes: Int,
    val price: Int,
    val salonId: Int
)