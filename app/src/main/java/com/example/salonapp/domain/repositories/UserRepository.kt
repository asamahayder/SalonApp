package com.example.salonapp.domain.repositories

import com.example.salonapp.domain.models.User

interface UserRepository {
    suspend fun getUser(): User
}