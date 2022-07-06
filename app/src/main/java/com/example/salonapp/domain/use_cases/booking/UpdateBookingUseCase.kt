package com.example.salonapp.domain.use_cases.booking

import com.example.salonapp.common.Resource
import com.example.salonapp.domain.models.Booking
import com.example.salonapp.domain.models.Service
import com.example.salonapp.domain.repositories.BookingRepository
import com.example.salonapp.domain.repositories.ServicesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class UpdateBookingUseCase @Inject constructor(
    private val repository: BookingRepository
){
    operator fun invoke(booking: Booking): Flow<Resource<List<Booking>>> = flow {
        try {
            emit(Resource.Loading())
            val services = repository.updateBooking(booking)
            emit(Resource.Success(services))
        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}