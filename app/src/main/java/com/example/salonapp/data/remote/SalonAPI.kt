package com.example.salonapp.data.remote

import com.example.salonapp.data.dtos.SalonDTO
import retrofit2.http.*

interface SalonAPI {

    @GET("Salon")
    suspend fun getSalons(): List<SalonDTO>

    @GET("Salon/{id}")
    suspend fun getSalonById(@Path("id") id: Int): SalonDTO

}