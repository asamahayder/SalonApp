package com.example.salonapp.domain.repositories

import com.example.salonapp.data.remote.dtos.SalonDTO
import com.example.salonapp.data.remote.dtos.UserDTO
import com.example.salonapp.data.remote.dtos.UserLoginDTO
import com.example.salonapp.data.remote.dtos.UserRegisterDTO

import retrofit2.http.Path

interface SalonRepository {

    suspend fun registerOwner(user: UserRegisterDTO): String

    suspend fun login(user: UserLoginDTO): String

    suspend fun getUser(): UserDTO

    suspend fun getSalons(): List<SalonDTO>

    suspend fun getSalonById(id: Int): SalonDTO

}