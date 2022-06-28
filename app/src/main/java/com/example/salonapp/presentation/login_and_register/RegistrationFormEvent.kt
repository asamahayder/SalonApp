package com.example.salonapp.presentation.login_and_register

sealed class RegistrationFormEvent {
    data class EmailChanged(val email: String) : RegistrationFormEvent()
    data class FirstnameChanged(val firstname: String) : RegistrationFormEvent()
    data class LastnameChanged(val lastname: String) : RegistrationFormEvent()
    data class PhoneChanged(val phone: String) : RegistrationFormEvent()
    data class PasswordChanged(val password: String) : RegistrationFormEvent()
    data class ConfirmPasswordChanged(val passwordConfirm: String) : RegistrationFormEvent()
    data class SetRole(val role: String) : RegistrationFormEvent()
    object Submit: RegistrationFormEvent()
}