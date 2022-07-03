package com.example.salonapp.data.remote

import com.example.salonapp.data.dtos.SalonDTO
import retrofit2.http.*

interface SalonAPI {

    @GET("Salon")
    suspend fun getSalons(): List<SalonDTO>

    @GET("Salon/{id}")
    suspend fun getSalonById(@Path("id") id: Int): SalonDTO

    @GET("Salon/GetByOwnerId")
    suspend fun getSalonsByOwnerId(@Query("OwnerId") id: Int): List<SalonDTO>

    @POST("Salon/CreateSalon")
    suspend fun createSalon(@Body salonDTO: SalonDTO): List<SalonDTO>

    @PUT("Salon/UpdateSalon")
    suspend fun updateSalon(@Body salonDTO: SalonDTO): List<SalonDTO>

    @DELETE("Salon/DeleteSalon")
    suspend fun deleteSalon(@Query("Id") salonId:Int): List<SalonDTO>

}