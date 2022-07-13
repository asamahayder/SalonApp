package com.example.salonapp.domain.models

import java.time.LocalDateTime

data class Booking(
    val id: Int = 0,
    val bookedBy: User,
    val customer: User? = null,
    val employee: User,
    val service: Service,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val note: String? = null,
    val pairId: String? = null
)