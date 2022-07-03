package com.example.salonapp.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Ballot
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.PeopleAlt
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.salonapp.R
import com.example.salonapp.presentation.owner.employees.EmployeesScreen
import com.example.salonapp.presentation.owner.profile.ProfileScreen
import com.example.salonapp.presentation.owner.salon_create.SalonCreateScreen
import com.example.salonapp.presentation.owner.schedule.ScheduleScreen
import com.example.salonapp.presentation.owner.services.ServicesScreen

@Composable
fun OwnerNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.OWNER,
        startDestination = BottomNavScreen.Schedule.route
    ) {
        composable(route = BottomNavScreen.Schedule.route) {
            ScheduleScreen(onCreateSalon = {firstSalon ->
                if (firstSalon) navController.popBackStack()
                navController.navigate(OwnerScreen.SalonCreate.route)
            })
        }

        composable(route = BottomNavScreen.Services.route) {
            ServicesScreen()
        }

        composable(route = BottomNavScreen.Employees.route) {
            EmployeesScreen()
        }

        composable(route = BottomNavScreen.Profile.route) {
            ProfileScreen()
        }

        composable(route = OwnerScreen.SalonCreate.route) {
            SalonCreateScreen(onSalonCreated = {
                navController.popBackStack()
                navController.navigate(BottomNavScreen.Schedule.route)
            })
        }

    }
}

sealed class OwnerScreen(
    val route: String,
){
    object SalonCreate : OwnerScreen(
        route = "salon_create"
    )
}

sealed class BottomNavScreen(
    val route: String,
    @StringRes val titleId: Int,
    val imageVector: ImageVector,
    val description: String
)
{
    object Schedule : BottomNavScreen(
        route = "schedule",
        titleId =  R.string.bottom_nav_schedule,
        imageVector = Icons.Outlined.Event,
        description = "Schedule"
    )

    object Services : BottomNavScreen(
        route = "services",
        titleId =  R.string.bottom_nav_services,
        imageVector = Icons.Outlined.Ballot,
        description = "Services"
    )

    object Employees : BottomNavScreen(
        route = "employees",
        titleId =  R.string.bottom_nav_employees,
        imageVector = Icons.Outlined.PeopleAlt,
        description = "Employees"
    )

    object Profile : BottomNavScreen(
        route = "profile",
        titleId =  R.string.bottom_nav_profile,
        imageVector = Icons.Outlined.Person,
        description = "Profile"
    )
}