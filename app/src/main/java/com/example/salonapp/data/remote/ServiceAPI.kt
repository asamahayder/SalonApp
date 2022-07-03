package com.example.salonapp.data.remote

import com.example.salonapp.data.dtos.ServiceDTO
import retrofit2.http.*

interface ServiceAPI {

    @GET("Services/ServicesBySalon")
    suspend fun getServicesBySalon(
        @Query("salonId") salonId: Int,
    ): List<ServiceDTO>

    @GET("Services/ServicesByEmployee")
    suspend fun getServicesByEmployee(@Query("employeeId") employeeId: Int): List<ServiceDTO>

    @GET("Services/Id")
    suspend fun getService(@Query("Id") serviceId:Int): ServiceDTO


    @POST("Services/CreateService")
    suspend fun createService(
        @Body serviceDTO: ServiceDTO
    ): List<ServiceDTO>

    @PUT("Services/UpdateService")
    suspend fun updateService(
        @Body serviceDTO: ServiceDTO
    ): List<ServiceDTO>

    @DELETE("Services/DeleteService")
    suspend fun deleteService(
        @Query("Id") serviceId: Int
    ): List<ServiceDTO>

}