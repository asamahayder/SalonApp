package com.example.salonapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.salonapp.common.Constants
import com.example.salonapp.presentation.authentication.login.LoginScreen
import com.example.salonapp.presentation.authentication.register.RegisterDetailsScreen
import com.example.salonapp.presentation.authentication.register.RegisterRoleSelectionScreen

//Based on code from https://github.com/stevdza-san/NestedNavigationBottomBarDemo

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.Login.route
    ) {
        composable(route = AuthScreen.Login.route) {
            LoginScreen(
                onLoginSuccess = {role->
                    when (role) {
                        Constants.ROLE_OWNER -> {
                            navController.popBackStack(AuthScreen.Login.route, true)
                            navController.navigate(Graph.OWNER)
                        }
                        Constants.ROLE_EMPLOYEE -> {
                            navController.popBackStack(AuthScreen.Login.route, true)
                            navController.navigate(Graph.EMPLOYEE)
                        }
                        Constants.ROLE_CUSTOMER -> {
                            navController.popBackStack(AuthScreen.Login.route, true)
                            navController.navigate(Graph.CUSTOMER)
                        }
                        else -> {
                            //TODO: handle wrong role specified
                        }
                    }
                },
                onRegisterClick = {
                    navController.navigate(AuthScreen.RegisterRoleSelection.route)
                }
            )
        }
        composable(route = AuthScreen.RegisterRoleSelection.route) {
            RegisterRoleSelectionScreen(
                onRoleSelected = {role ->
                    navController.navigate(AuthScreen.RegisterDetails.route + "/$role")
                }
            )
        }
        composable(route = AuthScreen.RegisterDetails.route + "/{role}") {backStackEntry ->

            val role = backStackEntry.arguments?.getString("role")

            RegisterDetailsScreen(
                role = role!!,
                onRegisterSuccess = {
                when (role) {
                    Constants.ROLE_OWNER -> {
                        navController.popBackStack(AuthScreen.Login.route, true)
                        navController.navigate(Graph.OWNER)
                    }
                    Constants.ROLE_EMPLOYEE -> {
                        navController.popBackStack(AuthScreen.Login.route, true)
                        navController.navigate(Graph.EMPLOYEE)
                    }
                    Constants.ROLE_CUSTOMER -> {
                        navController.popBackStack(AuthScreen.Login.route, true)
                        navController.navigate(Graph.CUSTOMER)
                    }
                    else -> {
                        //TODO: handle wrong role specified
                    }
                }
            })
        }
    }
}

sealed class AuthScreen(val route: String) {
    object Login : AuthScreen(route = "LOGIN")
    object RegisterRoleSelection : AuthScreen(route = "REGISTER_ROLE")
    object RegisterDetails : AuthScreen(route = "REGISTER_DETAILS")
}