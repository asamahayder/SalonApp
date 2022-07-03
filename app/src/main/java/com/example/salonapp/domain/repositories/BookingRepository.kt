package com.example.salonapp.domain.repositories

import com.example.salonapp.domain.models.Booking

interface BookingRepository {

    suspend fun getBookingsByEmployeeId(employeeId:Int): List<Booking>

    suspend fun getMyBookings(): List<Booking>

    suspend fun getBookingsByUserId(userId:Int): List<Booking>

    suspend fun getBooking(bookingId:Int): Booking

    suspend fun createBooking(booking: Booking): List<Booking>

    suspend fun updateBooking(booking: Booking): List<Booking>

    suspend fun deleteBooking(id:Int): List<Booking>

}