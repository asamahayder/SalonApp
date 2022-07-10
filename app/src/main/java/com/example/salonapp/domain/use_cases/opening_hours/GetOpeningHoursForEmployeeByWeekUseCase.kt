package com.example.salonapp.domain.use_cases.opening_hours

import com.example.salonapp.common.Resource
import com.example.salonapp.domain.models.Booking
import com.example.salonapp.domain.models.OpeningHours
import com.example.salonapp.domain.models.Service
import com.example.salonapp.domain.repositories.BookingRepository
import com.example.salonapp.domain.repositories.OpeningHoursRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

class GetOpeningHoursForEmployeeByWeekUseCase @Inject constructor(
    private val repository: OpeningHoursRepository,
){
    operator fun invoke(employeeId:Int, week:LocalDate): Flow<Resource<OpeningHours>> = flow {
        try {
            emit(Resource.Loading())

            val response = repository.getOpeningHoursForEmployeeByWeek(employeeId, LocalDateTime.of(week, LocalTime.now()))

            emit(Resource.Success(data = response))
        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach server. Check your internet connection. \n" + e.localizedMessage))
        }
    }
}