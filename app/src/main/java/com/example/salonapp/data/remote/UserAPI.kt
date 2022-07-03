package com.example.salonapp.data.remote

import com.example.salonapp.data.dtos.UserDTO
import retrofit2.http.*

interface UserAPI {

    @GET("User/GetUser")
    suspend fun getUser(): UserDTO

    @GET("User/GetUserById")
    suspend fun getUserById(@Query("id") id:Int): UserDTO

    @GET("User/GetUserByBookingId")
    suspend fun getUserByBookingId(@Query("bookingId") bookingId:Int): UserDTO

    @PUT("User/UpdateUser")
    suspend fun updateUser(@Body userDTO: UserDTO): UserDTO

    @DELETE("User/DeleteUser")
    suspend fun deleteUser(@Query("userId") userId:Int): UserDTO

}