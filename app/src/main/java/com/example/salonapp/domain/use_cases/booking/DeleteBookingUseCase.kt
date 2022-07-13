package com.example.salonapp.domain.use_cases.booking

import com.example.salonapp.common.Resource
import com.example.salonapp.domain.models.Booking
import com.example.salonapp.domain.models.Service
import com.example.salonapp.domain.repositories.BookingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DeleteBookingUseCase @Inject constructor(
    private val repository: BookingRepository,
){
    operator fun invoke(bookingId:Int): Flow<Resource<List<Booking>>> = flow {
        try {
            emit(Resource.Loading())

            val response = repository.deleteBooking(bookingId)

            emit(Resource.Success(data = response))
        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach server. Check your internet connection. \n" + e.localizedMessage))
        }
    }
}