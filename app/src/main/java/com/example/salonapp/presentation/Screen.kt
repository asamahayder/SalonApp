package com.example.salonapp.presentation

sealed class Screen(val route: String) {
    object LoginAndRegisterScreen: Screen("login_register_screen")
    object MainScreenOwner: Screen("main_owner")
}