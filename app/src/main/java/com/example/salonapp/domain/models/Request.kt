package com.example.salonapp.domain.models

import java.time.LocalDateTime

data class Request(
    val date: LocalDateTime,
    val employee: User,
    val id: Int,
    val requestStatus: RequestStatus,
    val salon: Salon
)



enum class RequestStatus {
    Pending, Approved, Denied;


}