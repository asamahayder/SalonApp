package com.example.salonapp.presentation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Ballot
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.PeopleAlt
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.salonapp.R

sealed class Screen(val route: String) {
    object LoginAndRegisterScreen: Screen("login_register_screen")
    object HomeScreen: Screen("home_screen")
    object CreateSalonScreen: Screen("create_salon_screen")
    object MainScreenOwner: Screen("main_owner_screen")
}

//sealed class BottomNavScreen(
//    val route: String,
//    @StringRes val titleId: Int,
//    val imageVector: ImageVector,
//    val description: String
//)
//{
//    object Schedule : BottomNavScreen(
//        route = "schedule",
//        titleId =  R.string.bottom_nav_schedule,
//        imageVector = Icons.Outlined.Event,
//        description = "Schedule"
//    )
//
//    object Services : BottomNavScreen(
//        route = "services",
//        titleId =  R.string.bottom_nav_services,
//        imageVector = Icons.Outlined.Ballot,
//        description = "Services"
//    )
//
//    object Employees : BottomNavScreen(
//        route = "employees",
//        titleId =  R.string.bottom_nav_employees,
//        imageVector = Icons.Outlined.PeopleAlt,
//        description = "Employees"
//    )
//
//    object Profile : BottomNavScreen(
//        route = "profile",
//        titleId =  R.string.bottom_nav_profile,
//        imageVector = Icons.Outlined.Person,
//        description = "Profile"
//    )
//}