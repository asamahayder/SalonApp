package com.example.salonapp.presentation.customer


import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreenCustomer(
    navController: NavHostController = rememberNavController(),
) {

    Text(text = "Inside customer view")

}



