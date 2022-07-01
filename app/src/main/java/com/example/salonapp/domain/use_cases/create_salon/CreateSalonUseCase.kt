package com.example.salonapp.domain.use_cases.create_salon

import com.example.salonapp.common.Resource
import com.example.salonapp.domain.models.Salon
import com.example.salonapp.domain.repositories.SalonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CreateSalonUseCase @Inject constructor(
    private val repository: SalonRepository
){
    operator fun invoke(salon: Salon): Flow<Resource<List<Salon>>> = flow {
        try {
            emit(Resource.Loading())
            val salons = repository.createSalon(salon)
            emit(Resource.Success(salons))
        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}