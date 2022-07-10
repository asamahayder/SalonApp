package com.example.salonapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.salonapp.common.Constants
import com.example.salonapp.presentation.authentication.login.LoginScreen
import com.example.salonapp.presentation.authentication.register.RegisterDetailsScreen
import com.example.salonapp.presentation.authentication.register.RegisterRoleSelectionScreen
import com.example.salonapp.presentation.employee.request.RequestScreen
import com.example.salonapp.presentation.employee.schedule.EmployeeScheduleScreen

//Based on code from https://github.com/stevdza-san/NestedNavigationBottomBarDemo

fun NavGraphBuilder.employeeNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.EMPLOYEE,
        startDestination = EmployeeScreen.Schedule.route
    ) {
        composable(route = EmployeeScreen.Schedule.route) {
            EmployeeScheduleScreen(
                onGoToRequestCreation = {
                    navController.navigate(EmployeeScreen.Request.route)
                },
                onCreateBooking = {

                },
                onGoToProfile = {

                }
            )

        }
        composable(route = EmployeeScreen.Profile.route) {

        }
        composable(route = EmployeeScreen.Request.route) {
            RequestScreen()
        }
    }
}

sealed class EmployeeScreen(val route: String) {
    object Schedule : AuthScreen(route = "EMPLOYEE_SCHEDULE")
    object Profile : AuthScreen(route = "EMPLOYEE_PROFILE")
    object Request : AuthScreen(route = "EMPLOYEE_REQUEST")
}