package com.example.salonapp.domain.repositories

import com.example.salonapp.domain.models.Salon
import com.example.salonapp.domain.models.User


interface SalonRepository {

    suspend fun getSalons(): List<Salon>

    suspend fun getSalonById(id: Int): Salon

    suspend fun getSalonsByOwnerId(id: Int): List<Salon>

    suspend fun createSalon(salon: Salon): List<Salon>

}