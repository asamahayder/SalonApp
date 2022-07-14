package com.example.salonapp.data.repositories

import com.example.salonapp.domain.models.AuthResponse
import com.example.salonapp.domain.models.UserLogin
import com.example.salonapp.domain.models.UserRegister
import com.example.salonapp.domain.repositories.AuthRepository

class AuthRepositoryFake :AuthRepository {


    override suspend fun registerOwner(userRegister: UserRegister): AuthResponse {
        TODO("Not yet implemented")
    }

    override suspend fun registerEmployee(userRegister: UserRegister): AuthResponse {
        TODO("Not yet implemented")
    }

    override suspend fun registerCustomer(userRegister: UserRegister): AuthResponse {
        TODO("Not yet implemented")
    }

    override suspend fun login(userLogin: UserLogin): AuthResponse {
        TODO("Not yet implemented")
    }
}