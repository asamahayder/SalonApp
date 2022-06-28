package com.example.salonapp.data.repositories

import com.example.salonapp.data.dtos.SalonDTO
import com.example.salonapp.data.dtos.UserDTO
import javax.inject.Inject
import com.example.salonapp.data.remote.SalonAPI
import com.example.salonapp.domain.models.Salon
import com.example.salonapp.domain.models.User

import com.example.salonapp.domain.repositories.SalonRepository

class SalonRepositoryImplementation @Inject constructor(
    private val api: SalonAPI
): SalonRepository
{

    override suspend fun getSalons(): List<Salon> {
        return api.getSalons().map { toSalon(it) }
    }

    override suspend fun getSalonById(id: Int): Salon {
        return toSalon(api.getSalonById(id))
    }

    private fun toSalon(salonDTO: SalonDTO): Salon{
        return Salon(
            id = salonDTO.id,
            city = salonDTO.city,
            streetName = salonDTO.streetName,
            streetNumber = salonDTO.streetNumber,
            suit = salonDTO.suit,
            postCode = salonDTO.postCode,
            phone = salonDTO.phone,
            door = salonDTO.door,
            email = salonDTO.email,
        )
    }


}