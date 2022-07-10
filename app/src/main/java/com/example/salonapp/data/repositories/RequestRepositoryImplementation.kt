package com.example.salonapp.data.repositories

import com.example.salonapp.data.dtos.RequestDTO
import com.example.salonapp.data.remote.RequestAPI
import com.example.salonapp.domain.models.Request
import com.example.salonapp.domain.models.RequestStatus
import com.example.salonapp.domain.repositories.RequestRepository
import com.example.salonapp.domain.repositories.SalonRepository
import com.example.salonapp.domain.repositories.UserRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class RequestRepositoryImplementation @Inject constructor(
    private val api: RequestAPI,
    private val userRepository: UserRepository,
    private val salonRepository: SalonRepository
): RequestRepository
{

    override suspend fun getRequestsBySalonId(salonId: Int): List<Request> {
        return api.getRequestsBySalonId(salonId).map { toModel(it) }
    }

    override suspend fun getRequestsByEmployeeId(): List<Request> {
        return api.getRequestsByEmployee().map { toModel(it) }
    }

    override suspend fun getRequests(): List<Request> {
        return api.getRequests().map { toModel(it) }
    }

    override suspend fun createRequest(salonId: Int): List<Request> {
        return api.createRequest(salonId).map { toModel(it) }
    }

    override suspend fun approveRequest(requestId: Int): List<Request> {
        return api.approveRequest(requestId).map { toModel(it) }
    }

    override suspend fun denyRequest(requestId: Int): List<Request> {
        return api.denyRequest(requestId).map { toModel(it) }
    }

    override suspend fun deleteRequest(requestId: Int): List<Request> {
        return api.deleteRequest(requestId).map { toModel(it) }
    }


    private suspend fun toModel(requestDTO: RequestDTO): Request{
        val employee = userRepository.getUserById(requestDTO.employeeId)
        val salon = salonRepository.getSalonById(requestDTO.salonId)

        return Request(
            id = requestDTO.id,
            employee = employee,
            date = LocalDateTime.parse(requestDTO.date, DateTimeFormatter.ISO_DATE_TIME),
            requestStatus = RequestStatus.values()[requestDTO.requestStatus],
            salon = salon
        )

    }






}