package com.example.salonapp.domain.repositories

import com.example.salonapp.domain.models.AuthResponse
import com.example.salonapp.domain.models.UserLogin
import com.example.salonapp.domain.models.UserRegister

interface AuthRepository {

    suspend fun registerOwner(userRegister: UserRegister): AuthResponse

    suspend fun registerEmployee(userRegister: UserRegister): AuthResponse

    suspend fun registerCustomer(userRegister: UserRegister): AuthResponse

    suspend fun login(userLogin: UserLogin): AuthResponse

}