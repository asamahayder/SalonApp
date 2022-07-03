package com.example.salonapp.data.remote

import com.example.salonapp.data.dtos.TextDTO
import retrofit2.http.*

interface TextAPI {

    @GET("Text/GetAllText")
    suspend fun getAllText(): List<TextDTO>

    @GET("Text/GetTextByKey")
    suspend fun getTextByKey(@Query("key") key: String): TextDTO

    @POST("Text/CreateText")
    suspend fun createText(
        @Body textDTO: TextDTO
    ): List<TextDTO>

    @PUT("Text/UpdateText")
    suspend fun updateText(
        @Body textDTO: TextDTO
    ): List<TextDTO>

    @DELETE("Text/DeleteText")
    suspend fun deleteText(
        @Query("key") key: String
    ): List<TextDTO>

}