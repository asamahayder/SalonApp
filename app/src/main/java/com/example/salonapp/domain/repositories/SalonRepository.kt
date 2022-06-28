package com.example.salonapp.domain.repositories

import com.example.salonapp.domain.models.Salon
import com.example.salonapp.domain.models.User


interface SalonRepository {

    suspend fun getSalons(): List<Salon>

    suspend fun getSalonById(id: Int): Salon

}