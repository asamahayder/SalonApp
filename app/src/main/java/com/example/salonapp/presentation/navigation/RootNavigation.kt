package com.example.salonapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.salonapp.presentation.customer.MainScreenCustomer
import com.example.salonapp.presentation.employee.MainScreenEmployee
import com.example.salonapp.presentation.owner.MainScreenOwner


//Based on code from https://github.com/stevdza-san/NestedNavigationBottomBarDemo

@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.AUTHENTICATION
    ) {
        authNavGraph(navController = navController)
        composable(route = Graph.OWNER) {
            MainScreenOwner()
        }
        composable(route = Graph.EMPLOYEE) {
            MainScreenEmployee()
        }
        composable(route = Graph.CUSTOMER) {
            MainScreenCustomer()
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val OWNER = "owner_graph"
    const val EMPLOYEE = "employee_graph"
    const val CUSTOMER = "customer_graph"


    const val DETAILS = "details_graph"
}