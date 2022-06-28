package com.example.salonapp.domain.repositories

import com.example.salonapp.domain.models.UserLogin
import com.example.salonapp.domain.models.UserRegister

interface AuthRepository {

    suspend fun registerOwner(userRegister: UserRegister): String

    suspend fun registerEmployee(userRegister: UserRegister): String

    suspend fun registerCustomer(userRegister: UserRegister): String

    suspend fun login(userLogin: UserLogin): String

}