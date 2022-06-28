package com.example.salonapp.data.remote

import com.example.salonapp.data.dtos.SalonDTO
import com.example.salonapp.data.dtos.UserDTO
import com.example.salonapp.data.dtos.UserLoginDTO
import com.example.salonapp.data.dtos.UserRegisterDTO
import retrofit2.http.*

interface AuthAPI {

    @POST("Auth/RegisterOwner")
    suspend fun registerOwner(@Body user: UserRegisterDTO): String

    @POST("Auth/RegisterEmployee")
    suspend fun registerEmployee(@Body user: UserRegisterDTO): String

    @POST("Auth/RegisterCustomer")
    suspend fun registerCustomer(@Body user: UserRegisterDTO): String

    @POST("Auth/Login")
    suspend fun login(@Body user: UserLoginDTO): String

}