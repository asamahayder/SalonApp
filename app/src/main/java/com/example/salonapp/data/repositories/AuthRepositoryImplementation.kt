package com.example.salonapp.data.repositories

import com.example.salonapp.data.dtos.UserDTO
import com.example.salonapp.data.dtos.UserLoginDTO
import com.example.salonapp.data.dtos.UserRegisterDTO
import com.example.salonapp.data.remote.AuthAPI
import javax.inject.Inject
import com.example.salonapp.data.remote.SalonAPI
import com.example.salonapp.domain.models.Salon
import com.example.salonapp.domain.models.User
import com.example.salonapp.domain.models.UserLogin
import com.example.salonapp.domain.models.UserRegister
import com.example.salonapp.domain.repositories.AuthRepository

import com.example.salonapp.domain.repositories.SalonRepository

class AuthRepositoryImplementation @Inject constructor(
    private val api: AuthAPI
): AuthRepository
{
    override suspend fun registerOwner(userRegister: UserRegister): String {
        return api.registerOwner(toDTO(userRegister))
    }

    override suspend fun registerEmployee(userRegister: UserRegister): String {
        return api.registerEmployee(toDTO(userRegister))
    }

    override suspend fun registerCustomer(userRegister: UserRegister): String {
        return api.registerCustomer(toDTO(userRegister))
    }

    override suspend fun login(userLogin: UserLogin): String {
        return api.login(toDTO(userLogin))
    }

    private fun toDTO(userRegister: UserRegister): UserRegisterDTO{
        return UserRegisterDTO(
            email = userRegister.email,
            password = userRegister.password,
            firstName = userRegister.firstName,
            lastName = userRegister.lastName,
            phone = userRegister.phone,
        )
    }

    private fun toDTO(userLogin: UserLogin): UserLoginDTO{
        return UserLoginDTO(
            email = userLogin.email,
            password = userLogin.password
        )
    }



}