package com.example.salonapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.salonapp.common.Constants
import com.example.salonapp.presentation.authentication.login.LoginScreen
import com.example.salonapp.presentation.authentication.register.RegisterDetailsScreen
import com.example.salonapp.presentation.authentication.register.RegisterRoleSelectionScreen
import com.example.salonapp.presentation.components.booking.BookingCreateEditScreen
import com.example.salonapp.presentation.components.profile.profile_edit.ProfileEditScreen
import com.example.salonapp.presentation.employee.hours.HoursEditScreen
import com.example.salonapp.presentation.employee.profile.ProfileScreen
import com.example.salonapp.presentation.employee.request.RequestScreen
import com.example.salonapp.presentation.employee.schedule.EmployeeScheduleScreen
import java.lang.NumberFormatException

//Based on code from https://github.com/stevdza-san/NestedNavigationBottomBarDemo

fun NavGraphBuilder.employeeNavGraph(navController: NavHostController, onLogOut:()->Unit) {
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
                    navController.navigate(OwnerScreen.BookingCreate.route + "/null")
                },
                onGoToProfile = {
                    navController.navigate(EmployeeScreen.Profile.route)
                },
                onEditBooking = { bookingId ->
                    navController.navigate(OwnerScreen.BookingCreate.route + "/" + bookingId)
                }
            )
        }
        composable(route = EmployeeScreen.Profile.route) {
            ProfileScreen(
                onEditHours = {
                    navController.navigate(EmployeeScreen.HoursEdit.route)

                },
                onEditProfile = {
                    navController.navigate(EmployeeScreen.ProfileEdit.route)
                },
                onLogOut = {
                    onLogOut()
                }
            )
        }
        composable(route = EmployeeScreen.Request.route) {
            RequestScreen()
        }

        composable(route = EmployeeScreen.ProfileEdit.route){
            ProfileEditScreen(
                onUpdateSuccess = {navController.popBackStack()},
                onDeleteSuccess = {onLogOut()}
            )
        }

        composable(route = EmployeeScreen.HoursEdit.route){
            HoursEditScreen(
                onUpdateSuccess = {navController.popBackStack()}
            )
        }

        composable(route = EmployeeScreen.BookingCreate.route + "/{bookingId}") {backStackEntry ->
            var bookingId: Int? = try {
                backStackEntry.arguments?.getString("bookingId")?.toInt()
            }catch (e: NumberFormatException){
                null
            }

            BookingCreateEditScreen(
                bookingId = bookingId,
                onBookingCreatedOrUpdated = {
                    navController.popBackStack()
                },
                onReturnToPreviousScreen = {
                    navController.popBackStack()
                }
            )
        }

    }
}

sealed class EmployeeScreen(val route: String) {
    object Schedule : EmployeeScreen(route = "EMPLOYEE_SCHEDULE")
    object Profile : EmployeeScreen(route = "EMPLOYEE_PROFILE")
    object ProfileEdit : EmployeeScreen(route = "EMPLOYEE_PROFILE_EDIT")
    object HoursEdit : EmployeeScreen(route = "EMPLOYEE_HOURS_EDIT")
    object Request : EmployeeScreen(route = "EMPLOYEE_REQUEST")
    object BookingCreate : EmployeeScreen(
        route = "BOOKING_CREATE"
    )
}