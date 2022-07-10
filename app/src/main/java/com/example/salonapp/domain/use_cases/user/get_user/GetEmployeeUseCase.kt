package com.example.salonapp.domain.use_cases.user.get_user

import com.example.salonapp.common.Resource
import com.example.salonapp.domain.models.Employee
import com.example.salonapp.domain.models.User
import com.example.salonapp.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetEmployeeUseCase @Inject constructor(
    private val repository: UserRepository,
){
    operator fun invoke(employeeId:Int): Flow<Resource<Employee>> = flow {
        try {

            emit(Resource.Loading())

            val response = repository.geEmployeeById(employeeId = employeeId)

            emit(Resource.Success(data = response))

        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach server. Check your internet connection. \n" + e.localizedMessage))
        }
    }
}