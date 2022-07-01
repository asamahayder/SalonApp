package com.example.salonapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.SalonAppTheme
import com.example.salonapp.presentation.login_and_register.components.LoginAndRegisterScreen
import com.example.salonapp.presentation.home.HomeScreen
import com.example.salonapp.presentation.salon_create.SalonCreateScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SalonAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.LoginAndRegisterScreen.route
                    ){
                        composable(
                            route = Screen.LoginAndRegisterScreen.route
                        ) {
                            LoginAndRegisterScreen(navController)
                        }
                        composable(
                            route = Screen.HomeScreen.route
                        ) {
                            HomeScreen(navController)
                        }
                        composable(
                            route = Screen.CreateSalonScreen.route
                        ) {
                            SalonCreateScreen(navController)
                        }

                    }

                }
            }
        }
    }
}