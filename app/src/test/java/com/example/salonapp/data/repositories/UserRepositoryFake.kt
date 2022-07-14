package com.example.salonapp.data.repositories

import com.example.salonapp.domain.models.Employee
import com.example.salonapp.domain.models.User
import com.example.salonapp.domain.repositories.UserRepository

class UserRepositoryFake constructor(private val currentUserId:Int, generatedUsers:List<User>): UserRepository {

    private var users = mutableListOf<User>()

    init {
        users = generatedUsers.toMutableList()
    }

    private val employees = mutableListOf<Employee>()

    override suspend fun getUser(): User {
        return  users.first { it.id == currentUserId }
    }

    override suspend fun getUserById(id: Int): User {

        try {
            return users.first{it.id == id}
        }catch (e:NoSuchElementException){
            return User()
        }




    }

    override suspend fun geEmployeeById(employeeId: Int): Employee {
        return employees.first { it.id == employeeId }
    }

    override suspend fun getUserByBookingId(bookingId: Int): User {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(user: User): User {
        users.removeIf { it.id == user.id }
        users.add(user)
        return user
    }

    override suspend fun deleteUser(userId: Int): User {
        val user = users.first { it.id == userId }
        users.removeIf { it.id == userId }
        return user
    }
}