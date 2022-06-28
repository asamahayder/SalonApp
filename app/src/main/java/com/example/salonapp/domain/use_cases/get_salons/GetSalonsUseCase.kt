package com.example.salonapp.domain.use_cases.get_salons

import com.example.salonapp.common.Resource
import com.example.salonapp.domain.models.Salon
import com.example.salonapp.domain.repositories.SalonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetSalonsUseCase @Inject constructor(
    private val repository: SalonRepository
){
    operator fun invoke(): Flow<Resource<List<Salon>>> = flow {
        println("inside flow!")

        try {
            emit(Resource.Loading())
            val salons = repository.getSalons()
            emit(Resource.Success(salons))
        }catch (e: HttpException){
            emit(Resource.Error(
                e.localizedMessage ?: ("An unexpected error occurred. " + e.stackTraceToString())
            ))
        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach server. Check your internet connection. " + e.stackTraceToString()))
        }

    }
}