package com.example.salonapp.presentation

sealed class Screen(val route: String) {
    object LoginAndRegisterScreen: Screen("login_register_screen")
    object HomeScreen: Screen("home_screen")
    object CreateSalonScreen: Screen("create_salon_screen")
}