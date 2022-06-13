package com.example.salonapp.domain.use_cases.register

import com.example.salonapp.common.Resource
import com.example.salonapp.data.remote.dtos.UserRegisterDTO
import com.example.salonapp.domain.repositories.SalonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: SalonRepository
){
    operator fun invoke(user: UserRegisterDTO): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val status = repository.registerOwner(user)

        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}