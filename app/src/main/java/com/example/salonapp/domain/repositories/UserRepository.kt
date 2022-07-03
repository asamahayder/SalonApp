package com.example.salonapp.domain.repositories

import com.example.salonapp.domain.models.User

interface UserRepository {
    suspend fun getUser(): User

    suspend fun getUserById(id:Int): User

    suspend fun getUserByBookingId(bookingId: Int): User

    suspend fun updateUser(user:User): User

    suspend fun deleteUser(userId:Int): User

}