package com.example.salonapp.domain.repositories

import com.example.salonapp.domain.models.Service


interface ServicesRepository {

    suspend fun getServicesBySalonId(salonId: Int): List<Service>

    suspend fun getServicesByEmployeeId(employeeId:Int): List<Service>

    suspend fun getService(serviceId:Int): Service

    suspend fun createService(service:Service): List<Service>

    suspend fun updateService(service:Service): List<Service>

    suspend fun deleteService(serviceId: Int): List<Service>

}