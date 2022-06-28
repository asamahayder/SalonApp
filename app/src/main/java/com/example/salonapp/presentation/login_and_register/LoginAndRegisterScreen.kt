package com.example.salonapp.presentation.login_and_register.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.salonapp.R
import com.example.salonapp.domain.models.ValidationEvent
import com.example.salonapp.presentation.Screen
import com.example.salonapp.presentation.login_and_register.LoginAndRegisterViewModel
import com.example.salonapp.presentation.login_and_register.LoginAndRegisterScreen

@Composable
fun LoginAndRegisterScreen(
    navController: NavController,
    viewModel: LoginAndRegisterViewModel = hiltViewModel()
){
    var currentScreenTitleId = getScreenId(viewModel.state.value.currentScreen)
    val state = viewModel.state

    LaunchedEffect(key1 = LocalContext.current) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    navController.navigate(Screen.MainScreenOwner.route)
                }
            }
        }
    }

    Column(
        Modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    )
    {
        screenLogoAndTitle(currentScreenTitle = stringResource(id = currentScreenTitleId))

        when(state.value.currentScreen){
            LoginAndRegisterScreen.RegisterRoleSelection -> RegisterRoleSelection()
            LoginAndRegisterScreen.RegisterDetails -> RegisterDetails()
            LoginAndRegisterScreen.Login -> LoginForm()

        }


    }
}

private fun getScreenId(loginAndRegisterScreen: LoginAndRegisterScreen): Int{
    return when(loginAndRegisterScreen){
        LoginAndRegisterScreen.RegisterRoleSelection -> R.string.register
        LoginAndRegisterScreen.RegisterDetails -> R.string.register
        LoginAndRegisterScreen.Login -> R.string.login
    }
}

@Preview
@Composable
fun test()
{
    LoginAndRegisterScreen(navController = rememberNavController())
}