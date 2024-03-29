package com.example.salonapp.data.repositories

import com.example.salonapp.data.dtos.SalonDTO
import com.example.salonapp.data.remote.SalonAPI
import com.example.salonapp.domain.models.Salon
import com.example.salonapp.domain.models.User
import com.example.salonapp.domain.repositories.SalonRepository
import com.example.salonapp.domain.repositories.UserRepository
import javax.inject.Inject

class SalonRepositoryImplementation @Inject constructor(
    private val api: SalonAPI,
    private val userRepository: UserRepository
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

    override suspend fun updateSalon(salon: Salon): List<Salon> {
        return api.updateSalon(toDTO(salon)).map { toModel(it) }
    }

    override suspend fun deleteSalon(salonId: Int): List<Salon> {
        return api.deleteSalon(salonId).map { toModel(it) }
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
            door = salon.door,
            employeesIds = salon.employees.map { it.id }
        )
    }

    private suspend fun toModel(salonDTO: SalonDTO): Salon{
        var employees: List<User> = listOf()
        if (!salonDTO.employeesIds.isNullOrEmpty()){
            employees = salonDTO.employeesIds.map { userRepository.getUserById(it) }
        }

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
            email = salonDTO.email,
            employees = employees
        )
    }




}