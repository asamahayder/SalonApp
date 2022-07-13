package com.example.salonapp.domain.models

data class Employee (
    val id: Int = 0,
    val salonId: Int? = null, //Due to cyclical dependencies, it is not possible to have a salon object here
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val phone: String = "",
    val role: String = ""
)

fun Employee.toUser(): User{
    return User(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        phone = this.phone,
        role = this.role

    )
}