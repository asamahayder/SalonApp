package com.example.salonapp.presentation.authentication.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.salonapp.R
import com.example.salonapp.presentation.Screen
import com.example.salonapp.presentation.authentication.LoginAndRegisterEvent
import com.example.salonapp.presentation.authentication.LoginAndRegisterScreen
import com.example.salonapp.presentation.authentication.LoginAndRegisterViewModel
import com.example.salonapp.presentation.authentication.login.LoginScreen
import com.example.salonapp.presentation.authentication.register.RegisterDetailsScreen
import com.example.salonapp.presentation.authentication.register.RegisterRoleSelectionScreen

@Composable
fun LoginAndRegisterScreen(
    navController: NavController,
    viewModel: LoginAndRegisterViewModel = hiltViewModel()
){
//    var currentScreenTitleId = getScreenId(viewModel.state.value.currentScreen)
//    val state = viewModel.state
//
//    LaunchedEffect(key1 = LocalContext.current) {
//        viewModel.events.collect { event ->
//            when (event) {
//                is LoginAndRegisterEvent.LoginOrRegisterSuccess-> {
//                    navController.navigate(Screen.MainScreenOwner.route)
//                }
//            }
//        }
//    }
//
//    val arrangement = if (state.value.isLoading) Arrangement.Top else Arrangement.SpaceBetween
//
//    Column(
//        Modifier
//            .fillMaxHeight()
//            .fillMaxWidth()
//            .verticalScroll(rememberScrollState())
//            .padding(horizontal = 20.dp, vertical = 50.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = arrangement
//    )
//    {
//        screenLogoAndTitle(currentScreenTitle = stringResource(id = currentScreenTitleId))
//
//        if (state.value.isLoading){
//            Spacer(modifier = Modifier.height(100.dp))
//            CircularProgressIndicator(Modifier.height(100.dp).width(100.dp))
//        }else{
//            when(state.value.currentScreen){
//                LoginAndRegisterScreen.RegisterRoleSelection -> RegisterRoleSelectionScreen()
//                LoginAndRegisterScreen.RegisterDetails -> RegisterDetailsScreen()
//                LoginAndRegisterScreen.Login -> LoginScreen()
//            }
//        }
//
//    }
}

private fun getScreenId(loginAndRegisterScreen: LoginAndRegisterScreen): Int{
    return when(loginAndRegisterScreen){
        LoginAndRegisterScreen.RegisterRoleSelection -> R.string.register
        LoginAndRegisterScreen.RegisterDetails -> R.string.register
        LoginAndRegisterScreen.Login -> R.string.login
    }
}