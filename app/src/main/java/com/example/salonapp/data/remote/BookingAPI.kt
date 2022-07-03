package com.example.salonapp.data.remote

import com.example.salonapp.data.dtos.BookingDTO
import retrofit2.http.*

interface BookingAPI {


    @GET("Booking/BookingsByEmployee")
    suspend fun bookingsByEmployeeId(@Query("employeeId") id: Int): List<BookingDTO>

    @GET("Booking/GetMyBookings")
    suspend fun getMyBookings(): List<BookingDTO>

    @GET("Booking/BookingsByUser")
    suspend fun getBookingsByUserId(@Query("userId") id: Int): List<BookingDTO>

    @GET("Booking/Id")
    suspend fun getById(@Query("id") id: Int): BookingDTO

    @POST("Booking/CreateBooking")
    suspend fun createBooking(@Body bookingDTO: BookingDTO): List<BookingDTO>

    @PUT("Booking/UpdateBooking")
    suspend fun updateBooking(@Body bookingDTO: BookingDTO): List<BookingDTO>

    @DELETE("Booking/DeleteBooking")
    suspend fun deleteBooking(@Query("id") id: Int): List<BookingDTO>

}