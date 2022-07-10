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
import com.example.salonapp.presentation.owner.employees.EmployeesScreen
import com.example.salonapp.presentation.owner.profile.ProfileScreen
import com.example.salonapp.presentation.owner.salon_create.SalonCreateScreen
import com.example.salonapp.presentation.owner.schedule.ScheduleScreen
import com.example.salonapp.presentation.owner.services.ServicesScreen
import com.example.salonapp.presentation.owner.services.create_edit.ServicesCreateEditScreen
import java.lang.NumberFormatException

@Composable
fun OwnerNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.OWNER,
        startDestination = BottomNavScreen.Schedule.route
    ) {
        composable(route = BottomNavScreen.Schedule.route) {
            ScheduleScreen(
                onCreateSalon = { firstSalon ->
                    if (firstSalon) navController.popBackStack()
                    navController.navigate(OwnerScreen.SalonCreate.route)},
                onCreateBooking = { navController.navigate(OwnerScreen.BookingCreate.route)}
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
                    navController.navigate(OwnerScreen.SalonCreate.route)
                }
            )
        }

        composable(route = BottomNavScreen.Employees.route) {
            EmployeesScreen(
                onCreateSalon = {
                navController.navigate(OwnerScreen.SalonCreate.route)
            })
        }

        composable(route = BottomNavScreen.Profile.route) {
            ProfileScreen(onEditSalon = {

            })
        }

        composable(route = OwnerScreen.SalonCreate.route) {
            SalonCreateScreen(onSalonCreated = {
                navController.popBackStack()
                navController.navigate(BottomNavScreen.Schedule.route)
            })

        }

        composable(route = OwnerScreen.BookingCreate.route) {
            BookingCreateEditScreen(
//                bookingId = null,
//                onBookingCreatedOrUpdated = {
//                    navController.popBackStack()
//                    navController.navigate(BottomNavScreen.Schedule.route)
//                }
            )
        }


        composable(route = OwnerScreen.ServiceCreate.route + "/{serviceId}") {backStackEntry ->

            var serviceID: Int?
            try {
                serviceID = backStackEntry.arguments?.getString("serviceId")?.toInt()
            }catch (e: NumberFormatException){
                serviceID = null
            }



            ServicesCreateEditScreen(
                serviceId = serviceID,
                onServiceCreatedOrEdited = {
                    navController.popBackStack()
                    navController.navigate(BottomNavScreen.Services.route)
                }
            )
        }

//        composable(route = OwnerScreen.ServiceCreate.route){
//            ServicesCreateScreen(
//                onServiceCreated = {
//                    navController.popBackStack()
//                    navController.navigate(BottomNavScreen.Services.route)
//                }
//            )
//        }

    }
}

//For screens in the owner-flow that do not use a nav bar
sealed class OwnerScreen(
    val route: String,
){
    object SalonCreate : OwnerScreen(
        route = "salon_create"
    )
    object BookingCreate : OwnerScreen(
        route = "booking_create"
    )
    object ServiceCreate : OwnerScreen(
        route = "service_create"
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