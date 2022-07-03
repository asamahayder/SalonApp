package com.example.salonapp.presentation.authentication.login

sealed class LoginEvent {
    data class EmailLoginChanged(val email: String) : LoginEvent()
    data class PasswordLoginChanged(val password: String) : LoginEvent()
    data class LoginSuccess(val role: String): LoginEvent()
    object SubmitLogin: LoginEvent()

}