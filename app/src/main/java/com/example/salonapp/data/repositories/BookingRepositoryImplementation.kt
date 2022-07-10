package com.example.salonapp.data.repositories

import com.example.salonapp.data.dtos.BookingDTO
import com.example.salonapp.data.remote.BookingAPI
import com.example.salonapp.domain.models.Booking
import com.example.salonapp.domain.repositories.BookingRepository
import com.example.salonapp.domain.repositories.ServicesRepository
import com.example.salonapp.domain.repositories.UserRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class BookingRepositoryImplementation @Inject constructor(
    private val api: BookingAPI,
    private val userRepository: UserRepository,
    private val servicesRepository: ServicesRepository
): BookingRepository
{

    override suspend fun getBookingsByEmployeeId(employeeId: Int): List<Booking> {
        return api.bookingsByEmployeeId(employeeId).map { toModel(it) }
    }

    override suspend fun getMyBookings(): List<Booking> {
        return api.getMyBookings().map { toModel(it) }
    }

    override suspend fun getBookingsByUserId(userId: Int): List<Booking> {
        return api.getBookingsByUserId(userId).map { toModel(it) }
    }

    override suspend fun getBooking(bookingId: Int): Booking {
        return toModel(api.getById(bookingId))
    }

    override suspend fun createBooking(booking: Booking): List<Booking> {
        val dto = toDTO(booking)
        return api.createBooking(dto).map { toModel(it) }
    }

    override suspend fun updateBooking(booking: Booking): List<Booking> {
        return api.updateBooking(toDTO(booking)).map { toModel(it) }
    }

    override suspend fun deleteBooking(id: Int): List<Booking> {
        return api.deleteBooking(id).map { toModel(it) }
    }

    private fun toDTO(booking: Booking): BookingDTO{
        return BookingDTO(
            id = booking.id,
            bookedById = booking.bookedBy.id,
            customerId = booking.customer?.id,
            employeeId = booking.employee.id,
            serviceId = booking.service.id,
            startTime = booking.startTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).toString(),
            endTime = booking.endTime.toString(),
            note = booking.note
        )
    }

    private suspend fun toModel(bookingDTO: BookingDTO): Booking{
        val bookedBy = userRepository.getUserById(bookingDTO.bookedById)

        val customerId = bookingDTO.customerId
        val customer = if (customerId != null) userRepository.getUserById(customerId) else null

        val employee = userRepository.getUserById(bookingDTO.employeeId)

        val service = servicesRepository.getService(bookingDTO.serviceId)

        return Booking(
            id = bookingDTO.id,
            bookedBy = bookedBy,
            customer = customer,
            employee = employee,
            service = service,
            startTime = LocalDateTime.parse(bookingDTO.startTime),
            endTime = LocalDateTime.parse(bookingDTO.endTime),
            note = bookingDTO.note
        )
    }




}