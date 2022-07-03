package com.example.salonapp.domain.models

data class Service(
    val description: String,
    val durationInMinutes: Int,
    val employees: List<User>,
    val id: Int,
    val name: String,
    val pauseEndInMinutes: Int,
    val pauseStartInMinutes: Int,
    val price: Int,
    val salon: Salon
)