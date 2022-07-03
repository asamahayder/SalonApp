package com.example.salonapp.presentation.authentication.register

sealed class RegisterEvent {
    data class EmailRegisterChanged(val email: String) : RegisterEvent()
    data class FirstnameChanged(val firstname: String) : RegisterEvent()
    data class LastnameChanged(val lastname: String) : RegisterEvent()
    data class PhoneChanged(val phone: String) : RegisterEvent()
    data class PasswordRegisterChanged(val password: String) : RegisterEvent()
    data class ConfirmPasswordChanged(val passwordConfirm: String) : RegisterEvent()
    data class SetRole(val role: String) : RegisterEvent()
    object Submit: RegisterEvent()
    object RegisterSuccess: RegisterEvent()
}