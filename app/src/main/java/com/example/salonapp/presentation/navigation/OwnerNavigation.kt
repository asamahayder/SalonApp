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
import com.example.salonapp.presentation.components.booking.BookingCreateEditScreen
import com.example.salonapp.presentation.components.profile.profile_edit.ProfileEditScreen
import com.example.salonapp.presentation.owner.employees.EmployeesScreen
import com.example.salonapp.presentation.owner.profile.ProfileScreen
import com.example.salonapp.presentation.owner.salon.SalonCreateEditScreen
import com.example.salonapp.presentation.owner.schedule.ScheduleScreen
import com.example.salonapp.presentation.owner.services.ServicesScreen
import com.example.salonapp.presentation.owner.services.create_edit.ServicesCreateEditScreen
import java.lang.NumberFormatException

@Composable
fun OwnerNavGraph(navController: NavHostController, onLogOut:()->Unit) {
    NavHost(
        navController = navController,
        route = Graph.OWNER,
        startDestination = BottomNavScreen.Schedule.route,
    ) {
        composable(route = BottomNavScreen.Schedule.route) {
            ScheduleScreen(
                onCreateSalon = {firstSalon ->

                    if (firstSalon){
                        navController.navigate(OwnerScreen.SalonCreateEditFirst.route)
                    }else{
                        navController.navigate(OwnerScreen.SalonCreateEdit.route + "/null")
                    }
                },
                onCreateBooking = { navController.navigate(OwnerScreen.BookingCreate.route + "/null")},
                onEditBooking = {bookingId ->
                    navController.navigate(OwnerScreen.BookingCreate.route + "/" + bookingId)
                }
            )
        }

        composable(route = BottomNavScreen.Services.route) {
            ServicesScreen(
                onCreateService = {
                    navController.navigate(OwnerScreen.ServiceCreate.route + "/null")

                },
                onEditService = { serviceId ->
                    navController.navigate(OwnerScreen.ServiceCreate.route + "/" + serviceId)

                },
                onCreateSalon = {
                    navController.navigate(OwnerScreen.SalonCreateEdit.route + "/null")
                }
            )
        }

        composable(route = BottomNavScreen.Employees.route) {
            EmployeesScreen(
                onCreateSalon = {
                navController.navigate(OwnerScreen.SalonCreateEdit.route + "/null")
            })
        }

        composable(route = BottomNavScreen.Profile.route) {
            ProfileScreen(
                onEditSalon = { salonId ->
                    navController.navigate(OwnerScreen.SalonCreateEdit.route + "/" + salonId)
                },
                onEditProfile = {
                    navController.navigate(OwnerScreen.ProfileEdit.route)
                },
                onLogOut = {
                    onLogOut()
                }
            )
        }

        composable(route = OwnerScreen.SalonCreateEdit.route + "/{salonId}") { backStackEntry ->
            var salonId: Int?
            try {
                salonId = backStackEntry.arguments?.getString("salonId")?.toInt()
            }catch (e: NumberFormatException){
                salonId = null
            }

            SalonCreateEditScreen(
                salonId = salonId,
                onSalonCreated = {

                    if (salonId != null){
                        navController.popBackStack()
                    }else{
                        navController.popBackStack(BottomNavScreen.Schedule.route, false)
                    }
            })

        }

        composable(route = OwnerScreen.SalonCreateEditFirst.route ) {

            SalonCreateEditScreen(
                salonId = null,
                firstSalon = true,
                onSalonCreated = {
                    navController.popBackStack(BottomNavScreen.Schedule.route, false)
                })

        }

        composable(route = OwnerScreen.BookingCreate.route + "/{bookingId}") {backStackEntry ->
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


        composable(route = OwnerScreen.ServiceCreate.route + "/{serviceId}") {backStackEntry ->

            var serviceID: Int? = try {
                backStackEntry.arguments?.getString("serviceId")?.toInt()
            }catch (e: NumberFormatException){
                null
            }

            ServicesCreateEditScreen(
                serviceId = serviceID,
                onServiceCreatedOrEdited = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = OwnerScreen.ProfileEdit.route) {
            ProfileEditScreen(
                onUpdateSuccess = {
                    navController.popBackStack()
                },
                onDeleteSuccess = {
                    onLogOut()
                }
            )
        }

    }
}

//For screens in the owner-flow that do not use a nav bar
sealed class OwnerScreen(
    val route: String,
){
    object SalonCreateEditFirst : OwnerScreen(
        route = "salon_create_first"
    )
    object SalonCreateEdit : OwnerScreen(
        route = "salon_create"
    )
    object BookingCreate : OwnerScreen(
        route = "booking_create"
    )
    object ServiceCreate : OwnerScreen(
        route = "service_create"
    )
    object ProfileEdit : OwnerScreen(
        route = "profile_edit"
    )
}


//For screens in the owner-flow that do use a nav bar
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