package com.example.salonapp.domain.use_cases.booking

import com.example.salonapp.common.Resource
import com.example.salonapp.domain.models.Booking
import com.example.salonapp.domain.repositories.BookingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.internal.toImmutableList
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMyBookingsUseCase @Inject constructor(
    private val repository: BookingRepository,
){
    operator fun invoke(): Flow<Resource<List<Booking>>> = flow {
        try {
            emit(Resource.Loading())

            val allBookings = repository.getMyBookings()

            val noDuplicates = mutableListOf<Booking>()

            allBookings.forEach { booking ->
                if (booking.pairId != null && noDuplicates.any { it.pairId == booking.pairId }) {

                }
                else{
                    noDuplicates.add(booking)
                }
            }

            emit(Resource.Success(data = noDuplicates.toImmutableList()))
        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach server. Check your internet connection. \n" + e.localizedMessage))
        }
    }
}