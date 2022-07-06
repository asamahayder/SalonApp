package com.example.salonapp.domain.use_cases.services.create_service

import com.example.salonapp.common.Resource
import com.example.salonapp.domain.models.Service
import com.example.salonapp.domain.repositories.ServicesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class CreateServiceUseCase @Inject constructor(
    private val repository: ServicesRepository
){
    operator fun invoke(service: Service): Flow<Resource<List<Service>>> = flow {
        try {
            emit(Resource.Loading())
            val services = repository.createService(service)
            emit(Resource.Success(services))
        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}