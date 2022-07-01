package com.example.salonapp.presentation.login_and_register

data class LoginAndRegisterState (
    val isLoading: Boolean = false,
    val emailRegister: String = "",
    val emailRegisterError: String? = null,
    val emailLogin: String = "newowner@test.com",
    val emailLoginError: String? = null,
    val passwordRegister: String = "",
    val passwordRegisterError: String? = null,
    val passwordLogin: String = "stringstringst",
    val passwordLoginError: String? = null,
    val passwordConfirm: String = "",
    val passwordConfirmError: String? = null,
    val firstName: String = "",
    val firstNameError: String? = null,
    val lastName: String = "",
    val lastNameError: String? = null,
    val phone: String = "",
    val phoneError: String? = null,
    val role: String = "",
    val currentScreen: LoginAndRegisterScreen = LoginAndRegisterScreen.Login,
    val message: String = "",
    val error: String = ""
)

enum class LoginAndRegisterScreen{
    Login,
    RegisterRoleSelection,
    RegisterDetails
}