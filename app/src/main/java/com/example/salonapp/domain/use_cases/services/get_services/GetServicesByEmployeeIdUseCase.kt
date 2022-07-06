package com.example.salonapp.domain.use_cases.services.get_services

import com.example.salonapp.common.Resource
import com.example.salonapp.domain.models.Service
import com.example.salonapp.domain.repositories.ServicesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetServicesByEmployeeIdUseCase @Inject constructor(
    private val repository: ServicesRepository,
){
    operator fun invoke(employeeId:Int): Flow<Resource<List<Service>>> = flow {
        try {

            emit(Resource.Loading())

            val response = repository.getServicesByEmployeeId(employeeId)

            emit(Resource.Success(data = response))

        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach server. Check your internet connection. \n" + e.localizedMessage))
        }
    }
}