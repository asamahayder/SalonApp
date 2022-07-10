package com.example.salonapp.data.repositories

import com.example.salonapp.data.dtos.EmployeeDTO
import com.example.salonapp.data.dtos.UserDTO
import com.example.salonapp.data.remote.UserAPI
import com.example.salonapp.domain.models.Employee
import com.example.salonapp.domain.models.Salon
import com.example.salonapp.domain.models.User
import com.example.salonapp.domain.repositories.SalonRepository
import com.example.salonapp.domain.repositories.UserRepository
import javax.inject.Inject

class UserRepositoryImplementation @Inject constructor(
    private val api: UserAPI
): UserRepository
{
    override suspend fun getUser(): User {
        return toModel(api.getUser())
    }

    override suspend fun getUserById(id: Int): User {
        return toModel(api.getUserById(id))
    }

    override suspend fun geEmployeeById(employeeId: Int): Employee {
        return toModel(api.getEmployeeById(employeeId))
    }

    override suspend fun getUserByBookingId(bookingId: Int): User {
        return toModel(api.getUserByBookingId(bookingId))
    }

    override suspend fun updateUser(user: User): User {
        return toModel(api.updateUser(toDTO(user)))
    }

    override suspend fun deleteUser(userId: Int): User {
        return toModel(api.deleteUser(userId))
    }

    private fun toModel(userDTO: UserDTO): User{
        return User(
            id = userDTO.id,
            firstName = userDTO.firstName,
            lastName = userDTO.lastName,
            email = userDTO.email,
            phone = userDTO.phone,
            role = userDTO.role
        )
    }

    private fun toModel(employeeDTO: EmployeeDTO): Employee{

        return Employee(
            id = employeeDTO.id,
            salonId = employeeDTO.salonId,
            firstName = employeeDTO.firstName,
            lastName = employeeDTO.lastName,
            email = employeeDTO.email,
            phone = employeeDTO.phone,
            role = employeeDTO.role
        )
    }

    private fun toDTO(user: User): UserDTO{
        return UserDTO(
            email = user.email,
            firstName = user.firstName,
            id = user.id,
            lastName = user.lastName,
            phone = user.phone,
            role = user.role
        )
    }


}