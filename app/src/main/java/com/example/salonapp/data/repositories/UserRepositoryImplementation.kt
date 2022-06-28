package com.example.salonapp.data.repositories

import com.example.salonapp.data.dtos.UserDTO
import javax.inject.Inject
import com.example.salonapp.data.remote.SalonAPI
import com.example.salonapp.data.remote.UserAPI
import com.example.salonapp.domain.models.Salon
import com.example.salonapp.domain.models.User

import com.example.salonapp.domain.repositories.SalonRepository
import com.example.salonapp.domain.repositories.UserRepository

class UserRepositoryImplementation @Inject constructor(
    private val api: UserAPI
): UserRepository
{
    override suspend fun getUser(): User {
        return toUser(api.getUser())
    }

    private fun toUser(userDTO: UserDTO): User{
        return User(
            id = userDTO.id,
            firstName = userDTO.firstName,
            lastName = userDTO.lastName,
            email = userDTO.email,
            phone = userDTO.phone,
            role = userDTO.role
        )
    }

}