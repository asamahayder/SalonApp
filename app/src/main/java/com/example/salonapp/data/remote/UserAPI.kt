package com.example.salonapp.data.remote

import com.example.salonapp.data.dtos.SalonDTO
import com.example.salonapp.data.dtos.UserDTO
import com.example.salonapp.data.dtos.UserLoginDTO
import com.example.salonapp.data.dtos.UserRegisterDTO
import retrofit2.http.*

interface UserAPI {

    @GET("User/GetUser")
    suspend fun getUser(): UserDTO

}