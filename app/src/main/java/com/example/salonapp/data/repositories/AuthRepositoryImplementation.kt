package com.example.salonapp.data.repositories

import com.example.salonapp.data.dtos.AuthResponseDTO
import com.example.salonapp.data.dtos.UserLoginDTO
import com.example.salonapp.data.dtos.UserRegisterDTO
import com.example.salonapp.data.remote.AuthAPI
import javax.inject.Inject
import com.example.salonapp.domain.models.*
import com.example.salonapp.domain.repositories.AuthRepository

class AuthRepositoryImplementation @Inject constructor(
    private val api: AuthAPI
): AuthRepository
{
    override suspend fun registerOwner(userRegister: UserRegister): AuthResponse {
        return toModel(api.registerOwner(toDTO(userRegister)))
    }

    override suspend fun registerEmployee(userRegister: UserRegister): AuthResponse {
        return toModel(api.registerEmployee(toDTO(userRegister)))
    }

    override suspend fun registerCustomer(userRegister: UserRegister): AuthResponse {
        return toModel(api.registerCustomer(toDTO(userRegister)))
    }

    override suspend fun login(userLogin: UserLogin): AuthResponse {
        return toModel(api.login(toDTO(userLogin)))
    }


    private fun toModel(authResponseDTO: AuthResponseDTO): AuthResponse{
        return AuthResponse(
            message = authResponseDTO.message,
            userId = authResponseDTO.userId
        )
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