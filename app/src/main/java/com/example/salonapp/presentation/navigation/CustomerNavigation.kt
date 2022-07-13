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
import com.example.salonapp.presentation.customer.MainScreenCustomer
import com.example.salonapp.presentation.employee.hours.HoursEditScreen
import com.example.salonapp.presentation.employee.profile.ProfileScreen
import com.example.salonapp.presentation.employee.request.RequestScreen
import com.example.salonapp.presentation.employee.schedule.EmployeeScheduleScreen
import java.lang.NumberFormatException

//Based on code from https://github.com/stevdza-san/NestedNavigationBottomBarDemo

fun NavGraphBuilder.customerNavGraph(navController: NavHostController, onLogOut:()->Unit) {
    navigation(
        route = Graph.CUSTOMER,
        startDestination = CustomerScreen.MainScreen.route
    ) {
        composable(route = CustomerScreen.MainScreen.route) {
            MainScreenCustomer(
                onLogOut = {
                    onLogOut()
                },
                onProfileEdit = {
                    navController.navigate(CustomerScreen.ProfileEdit.route)
                },
                onGoToBooking = { bookingId ->

                    if (bookingId == null){
                        navController.navigate(CustomerScreen.BookingCreate.route + "/null")
                    }else{
                        navController.navigate(CustomerScreen.BookingCreate.route + "/" + bookingId)
                    }

                }
            )
        }

        composable(route = CustomerScreen.ProfileEdit.route){
            ProfileEditScreen(
                onUpdateSuccess = {navController.popBackStack()},
                onDeleteSuccess = {onLogOut()}
            )
        }

        composable(route = CustomerScreen.BookingCreate.route + "/{bookingId}") {backStackEntry ->
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

sealed class CustomerScreen(val route: String) {
    object MainScreen : CustomerScreen(route = "CUSTOMER_MAIN")
    object ProfileEdit : CustomerScreen(route = "CUSTOMER_PROFILE_EDIT")
    object BookingCreate : CustomerScreen(
        route = "CUSTOMER_BOOKING_CREATE"
    )
}