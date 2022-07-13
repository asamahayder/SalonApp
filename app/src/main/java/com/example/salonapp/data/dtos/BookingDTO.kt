package com.example.salonapp.data.dtos

data class BookingDTO(
    val id: Int,
    val bookedById: Int,
    val customerId: Int?,
    val employeeId: Int,
    val serviceId: Int,
    val startTime: String,
    val endTime: String,
    val note: String?,
    val pairId: String?
)