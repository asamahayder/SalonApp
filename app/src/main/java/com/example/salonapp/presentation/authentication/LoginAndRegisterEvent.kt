package com.example.salonapp.presentation.authentication

sealed class LoginAndRegisterEvent {
    data class EmailRegisterChanged(val email: String) : LoginAndRegisterEvent()
    data class EmailLoginChanged(val email: String) : LoginAndRegisterEvent()
    data class FirstnameChanged(val firstname: String) : LoginAndRegisterEvent()
    data class LastnameChanged(val lastname: String) : LoginAndRegisterEvent()
    data class PhoneChanged(val phone: String) : LoginAndRegisterEvent()
    data class PasswordRegisterChanged(val password: String) : LoginAndRegisterEvent()
    data class PasswordLoginChanged(val password: String) : LoginAndRegisterEvent()
    data class ConfirmPasswordChanged(val passwordConfirm: String) : LoginAndRegisterEvent()
    data class SetRole(val role: String) : LoginAndRegisterEvent()
    object GoToRegister: LoginAndRegisterEvent()
    object SubmitRegister: LoginAndRegisterEvent()
    object SubmitLogin: LoginAndRegisterEvent()
    object BackCalledFromRegisterDetails: LoginAndRegisterEvent()
    object BackCalledFromRegisterRoles: LoginAndRegisterEvent()
    object LoginOrRegisterSuccess: LoginAndRegisterEvent()
}