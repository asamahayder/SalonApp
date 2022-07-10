package com.example.salonapp.domain.use_cases.salons

import com.example.salonapp.common.Resource
import com.example.salonapp.domain.models.Salon
import com.example.salonapp.domain.repositories.SalonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetSalonUseCase @Inject constructor(
    private val repository: SalonRepository
){
    operator fun invoke(id: Int): Flow<Resource<Salon>> = flow {
        try {
            emit(Resource.Loading())
            val salon = repository.getSalonById(id)
            emit(Resource.Success(salon))
        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}