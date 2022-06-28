package com.example.salonapp.domain.use_cases.register

import com.example.salonapp.common.Constants
import com.example.salonapp.common.Resource
import com.example.salonapp.domain.models.UserRegister
import com.example.salonapp.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
){
    operator fun invoke(user: UserRegister): Flow<Resource<String>> = flow {
        try {
            println("************************* \n invoked")
            var message: String = ""

            emit(Resource.Loading())

            when(user.role){
                Constants.ROLE_OWNER -> message = repository.registerOwner(user)
                Constants.ROLE_EMPLOYEE -> message = repository.registerEmployee(user)
                Constants.ROLE_CUSTOMER -> message = repository.registerCustomer(user)
                else -> {
                    emit(Resource.Error("Role was not correctly specified"))
                }
            }

            if (!message.isNullOrBlank()) emit(Resource.Success(message))
            else emit(Resource.Error("Couldn't retrieve message from server."))


        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}