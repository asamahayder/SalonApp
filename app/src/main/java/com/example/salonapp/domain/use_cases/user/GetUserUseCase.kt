package com.example.salonapp.domain.use_cases.user

import com.example.salonapp.common.Resource
import com.example.salonapp.domain.models.User
import com.example.salonapp.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: UserRepository,
){
    operator fun invoke(): Flow<Resource<User>> = flow {
        try {

            emit(Resource.Loading())

            val response = repository.getUser()

            emit(Resource.Success(data = response))

        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach server. Check your internet connection. \n" + e.localizedMessage))
        }
    }
}