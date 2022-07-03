package com.example.salonapp.domain.models

import java.time.LocalDateTime

data class Booking(
    val id: Int,
    val bookedBy: User,
    val customer: User?,
    val employee: User,
    val service: Service,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val note: String
)