package com.example.salonapp.data.remote

import com.example.salonapp.data.dtos.OpeningHoursDTO
import com.example.salonapp.data.dtos.SpecialOpeningHoursDTO
import retrofit2.http.*

interface OpeningHoursAPI {

    @GET("OpeningHours/GetOpeningHoursForEmployeeByWeek")
    suspend fun getOpeningHoursForEmployeeByWeek(
        @Query("employeeID") employeeId: Int,
        @Query("week") week: String,
    ): OpeningHoursDTO

    @GET("OpeningHours/GetNormalHours")
    suspend fun getNormalHours(
        @Query("employeeID") employeeId: Int,
    ): OpeningHoursDTO

    @GET("OpeningHours/GetSpecialHoursByWeek")
    suspend fun getSpecialHoursByWeek(
        @Query("employeeID") employeeId: Int,
        @Query("week") week: String,
    ): SpecialOpeningHoursDTO


    @POST("OpeningHours/CreateSpecialOpeningHours")
    suspend fun createSpecialOpeningHours(
        @Body specialOpeningHoursDTO: SpecialOpeningHoursDTO
    ): List<SpecialOpeningHoursDTO>

    @PUT("OpeningHours/UpdateSpecialOpeningHours")
    suspend fun updateSpecialOpeningHours(
        @Body specialOpeningHoursDTO: SpecialOpeningHoursDTO
    ): List<SpecialOpeningHoursDTO>

    @PUT("OpeningHours/UpdateOpeningHours")
    suspend fun updateOpeningHours(
        @Body openingHoursDTO: OpeningHoursDTO
    ): OpeningHoursDTO

    @DELETE("OpeningHours/DeleteSpecialOpeningHoursByWeek")
    suspend fun deleteSpecialOpeningHours(
        @Query("week") week: String
    ): List<SpecialOpeningHoursDTO>

}