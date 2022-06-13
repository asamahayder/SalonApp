package com.example.salonapp.data.remote

import com.example.salonapp.data.remote.dtos.SalonDTO
import com.example.salonapp.data.remote.dtos.UserDTO
import com.example.salonapp.data.remote.dtos.UserLoginDTO
import com.example.salonapp.data.remote.dtos.UserRegisterDTO
import retrofit2.http.*

interface SalonAPI {

    @POST("Auth/RegisterOwner")
    suspend fun registerOwner(user: UserRegisterDTO): String

    @POST("Auth/Login")
    suspend fun login(user: UserLoginDTO): String

    @GET("User/GetUser")
    suspend fun getUser(): UserDTO

    @GET("Salon")
    suspend fun getSalons(): List<SalonDTO>

    @GET("Salon/{id}")
    suspend fun getSalonById(@Path("id") id: Int): SalonDTO

}