package com.example.salonapp.presentation.login_and_register

data class LoginAndRegisterState (
    val isLoading: Boolean = false,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val passwordConfirm: String = "",
    val passwordConfirmError: String? = null,
    val firstName: String = "",
    val firstNameError: String? = null,
    val lastName: String = "",
    val lastNameError: String? = null,
    val phone: String = "",
    val phoneError: String? = null,
    val role: String = "",
    val currentScreen: LoginAndRegisterScreen = LoginAndRegisterScreen.RegisterRoleSelection,
    val message: String = "",
    val error: String = ""
)

enum class LoginAndRegisterScreen{
    Login,
    RegisterRoleSelection,
    RegisterDetails
}