package com.example.salonapp.data.repositories

import com.example.salonapp.data.dtos.ServiceDTO
import com.example.salonapp.data.remote.ServiceAPI
import com.example.salonapp.domain.models.Service
import com.example.salonapp.domain.repositories.SalonRepository
import com.example.salonapp.domain.repositories.ServicesRepository
import com.example.salonapp.domain.repositories.UserRepository
import javax.inject.Inject

class ServiceRepositoryImplementation @Inject constructor(
    private val api: ServiceAPI,
    private val salonRepository: SalonRepository,
    private val userRepository: UserRepository
): ServicesRepository
{

    override suspend fun getServicesBySalonId(salonId: Int): List<Service> {
        return api.getServicesBySalon(salonId).map { toModel(it) }
    }

    override suspend fun getServicesByEmployeeId(employeeId: Int): List<Service> {
        return api.getServicesByEmployee(employeeId).map { toModel(it) }
    }

    override suspend fun getService(serviceId: Int): Service {
        return toModel(api.getService(serviceId))
    }

    override suspend fun createService(service: Service): List<Service> {
        return api.createService(toDTO(service)).map { toModel(it) }
    }

    override suspend fun updateService(service: Service): List<Service> {
        return api.updateService(toDTO(service)).map { toModel(it) }
    }

    override suspend fun deleteService(serviceId: Int): List<Service> {
        return api.deleteService(serviceId).map { toModel(it) }
    }

    private fun toDTO(service: Service): ServiceDTO{
        return ServiceDTO(
            id = service.id,
            name = service.name,
            description = service.description,
            durationInMinutes = service.durationInMinutes,
            pauseStartInMinutes = service.pauseStartInMinutes,
            pauseEndInMinutes = service.pauseEndInMinutes,
            price = service.price,
            salonId = service.salon.id,
            employeesIds = service.employees.map { it.id }
        )
    }

    private suspend fun toModel(serviceDTO: ServiceDTO): Service{

        val salon = salonRepository.getSalonById(serviceDTO.salonId)

        val employees = serviceDTO.employeesIds.map { userRepository.getUserById(it)}

        return Service(
            id = serviceDTO.id,
            name = serviceDTO.name,
            description = serviceDTO.description,
            durationInMinutes = serviceDTO.durationInMinutes,
            pauseStartInMinutes = serviceDTO.pauseStartInMinutes,
            pauseEndInMinutes = serviceDTO.pauseEndInMinutes,
            price = serviceDTO.price,
            salon = salon,
            employees = employees
        )
    }




}