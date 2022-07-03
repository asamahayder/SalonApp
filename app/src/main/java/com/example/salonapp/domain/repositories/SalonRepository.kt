package com.example.salonapp.domain.repositories

import com.example.salonapp.domain.models.Salon


interface SalonRepository {

    suspend fun getSalons(): List<Salon>

    suspend fun getSalonById(id: Int): Salon

    suspend fun getSalonsByOwnerId(id: Int): List<Salon>

    suspend fun createSalon(salon: Salon): List<Salon>

    suspend fun updateSalon(salon: Salon): List<Salon>

    suspend fun deleteSalon(salonId:Int): List<Salon>

}