package com.example.salonapp.presentation.employee


import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreenEmployee(
    navController: NavHostController = rememberNavController(),
) {

    Text(text = "Inside employee view")

}



