package com.example.salonapp.data.remote

import com.example.salonapp.data.dtos.RequestDTO
import retrofit2.http.*

interface RequestAPI {

    @GET("Request/GetRequestsBySalonId")
    suspend fun getRequestsBySalonId(
        @Query("salonId") salonId: Int,
    ): List<RequestDTO>

    @GET("Request/GetRequestsByEmployee")
    suspend fun getRequestsByEmployee(): List<RequestDTO>

    @GET("Request/GetRequests")
    suspend fun getRequests(): List<RequestDTO>


    @POST("Request/CreateRequest")
    suspend fun createRequest(
        @Query("salonId") salonId: Int
    ): List<RequestDTO>

    @PUT("Request/ApproveRequest")
    suspend fun approveRequest(
        @Query("requestID") requestId: Int
    ): List<RequestDTO>

    @PUT("Request/DenyRequest")
    suspend fun denyRequest(
        @Query("requestID") requestId: Int
    ): List<RequestDTO>

    @DELETE("Request/DeleteRequest")
    suspend fun deleteRequest(
        @Query("requestId") requestId: Int
    ): List<RequestDTO>

}