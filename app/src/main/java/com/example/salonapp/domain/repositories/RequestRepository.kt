package com.example.salonapp.domain.repositories

import com.example.salonapp.domain.models.Request

interface RequestRepository {

    suspend fun getRequestsBySalonId(salonId:Int): List<Request>

    suspend fun getRequestsByEmployeeId(): List<Request>

    suspend fun getRequests():List<Request>

    suspend fun createRequest(salonId: Int): List<Request>

    suspend fun approveRequest(requestId:Int): List<Request>

    suspend fun denyRequest(requestId:Int): List<Request>

    suspend fun deleteRequest(requestId:Int): List<Request>

}