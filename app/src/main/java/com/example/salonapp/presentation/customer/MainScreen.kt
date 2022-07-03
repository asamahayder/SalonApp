package com.example.salonapp.presentation.customer


import androidx.compose.material.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.salonapp.presentation.navigation.BottomNavScreen
import com.example.salonapp.presentation.navigation.OwnerNavGraph

@Composable
fun MainScreenCustomer(
    navController: NavHostController = rememberNavController(),
) {

    Text(text = "Inside customer view")

}



