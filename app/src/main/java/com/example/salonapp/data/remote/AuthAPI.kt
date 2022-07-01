package com.example.salonapp.data.remote

import com.example.salonapp.data.dtos.*
import retrofit2.http.*

interface AuthAPI {

    @POST("Auth/RegisterOwner")
    suspend fun registerOwner(@Body user: UserRegisterDTO): AuthResponseDTO

    @POST("Auth/RegisterEmployee")
    suspend fun registerEmployee(@Body user: UserRegisterDTO): AuthResponseDTO

    @POST("Auth/RegisterCustomer")
    suspend fun registerCustomer(@Body user: UserRegisterDTO): AuthResponseDTO

    @POST("Auth/Login")
    suspend fun login(@Body user: UserLoginDTO): AuthResponseDTO

}