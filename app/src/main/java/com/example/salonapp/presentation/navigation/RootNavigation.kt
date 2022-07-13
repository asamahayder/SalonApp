package com.example.salonapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.salonapp.presentation.customer.MainScreenCustomer
import com.example.salonapp.presentation.employee.schedule.EmployeeScheduleScreen
import com.example.salonapp.presentation.owner.MainScreenOwner


//Based on code from https://github.com/stevdza-san/NestedNavigationBottomBarDemo

@Composable
fun RootNavigationGraph(navController: NavHostController, onLogOut:()->Unit) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.AUTHENTICATION
    ) {
        authNavGraph(navController = navController)
        employeeNavGraph(navController = navController, onLogOut = {onLogOut()})
        customerNavGraph(navController = navController, onLogOut = {onLogOut()})
        composable(route = Graph.OWNER) {
            MainScreenOwner(onLogOut = {
                onLogOut()
            })
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val OWNER = "owner_graph"
    const val EMPLOYEE = "employee_graph"
    const val CUSTOMER = "customer_graph"
}