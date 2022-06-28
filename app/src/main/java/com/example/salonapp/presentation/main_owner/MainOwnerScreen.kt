package com.example.salonapp.presentation.main_owner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.salonapp.presentation.login_and_register.LoginAndRegisterViewModel
import com.example.salonapp.presentation.login_and_register.components.RegisterRoleSelection
import com.example.salonapp.presentation.login_and_register.components.screenLogoAndTitle


@Composable
fun MainOwnerScreen(
    navController: NavController,
    //viewModel: LoginAndRegisterViewModel = hiltViewModel()
){
    Column(
        Modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    )
    {
        Text(text = "success")
    }
}

