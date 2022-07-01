package com.example.salonapp.domain.use_cases.login

import com.example.salonapp.common.Constants
import com.example.salonapp.common.Resource
import com.example.salonapp.common.SessionManager
import com.example.salonapp.domain.models.UserLogin
import com.example.salonapp.domain.models.UserRegister
import com.example.salonapp.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val sessionManager: SessionManager
){
    operator fun invoke(user: UserLogin): Flow<Resource<String>> = flow {
        try {

            var message: String = ""

            emit(Resource.Loading())

            val response = repository.login(user)
            message = response.message


            if (!message.isNullOrBlank() && response.userId != null) {
                sessionManager.saveAuthToken(message)
                sessionManager.saveUserId(response.userId)
                emit(Resource.Success("Successful login"))
            }
            else emit(Resource.Error("Couldn't retrieve message or user id from server."))


        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach server. Check your internet connection. \n" + e.localizedMessage))
        }
    }
}