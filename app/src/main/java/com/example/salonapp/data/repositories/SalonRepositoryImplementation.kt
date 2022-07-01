package com.example.salonapp.data.repositories

import com.example.salonapp.data.dtos.SalonDTO
import javax.inject.Inject
import com.example.salonapp.data.remote.SalonAPI
import com.example.salonapp.domain.models.Salon

import com.example.salonapp.domain.repositories.SalonRepository

class SalonRepositoryImplementation @Inject constructor(
    private val api: SalonAPI
): SalonRepository
{

    override suspend fun getSalons(): List<Salon> {
        return api.getSalons().map { toModel(it) }
    }

    override suspend fun getSalonById(id: Int): Salon {
        return toModel(api.getSalonById(id))
    }

    override suspend fun getSalonsByOwnerId(id: Int): List<Salon> {
        return api.getSalonsByOwnerId(id).map { toModel(it) }
    }

    override suspend fun createSalon(salon: Salon): List<Salon> {
        return api.createSalon(toDTO(salon)).map { toModel(it) }
    }

    private fun toDTO(salon: Salon): SalonDTO{
        return SalonDTO(
            id = salon.id,
            name = salon.name,
            email = salon.email,
            phone = salon.phone,
            city = salon.city,
            postCode = salon.postCode,
            streetName = salon.streetName,
            streetNumber = salon.streetNumber,
            suit = salon.suit,
            door = salon.door
        )
    }

    private fun toModel(salonDTO: SalonDTO): Salon{
        return Salon(
            id = salonDTO.id,
            name = salonDTO.name,
            city = salonDTO.city,
            streetName = salonDTO.streetName,
            streetNumber = salonDTO.streetNumber,
            suit = salonDTO.suit,
            postCode = salonDTO.postCode,
            phone = salonDTO.phone,
            door = salonDTO.door,
            email = salonDTO.email
        )
    }




}