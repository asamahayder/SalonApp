package com.example.salonapp.data.repositories

import javax.inject.Inject
import com.example.salonapp.data.remote.SalonAPI
import com.example.salonapp.data.remote.dtos.SalonDTO
import com.example.salonapp.data.remote.dtos.UserDTO
import com.example.salonapp.data.remote.dtos.UserLoginDTO
import com.example.salonapp.data.remote.dtos.UserRegisterDTO
import com.example.salonapp.domain.repositories.SalonRepository

class SalonRepositoryImplementation @Inject constructor(
    private val api: SalonAPI
): SalonRepository
{
    override suspend fun registerOwner(user: UserRegisterDTO): String {
        return api.registerOwner(user)
    }

    override suspend fun login(user: UserLoginDTO): String {
        return api.login(user)
    }

    override suspend fun getUser(): UserDTO {
        return api.getUser()
    }

    override suspend fun getSalons(): List<SalonDTO> {
        return api.getSalons()
    }

    override suspend fun getSalonById(id: Int): SalonDTO {
        return api.getSalonById(id)
    }
}