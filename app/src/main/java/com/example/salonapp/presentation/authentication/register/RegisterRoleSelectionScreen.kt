package com.example.salonapp.presentation.authentication.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.salonapp.R
import com.example.salonapp.common.Constants
import com.example.salonapp.presentation.components.screenLogoAndTitle

@Composable
fun RegisterRoleSelectionScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onRoleSelected: (role: String) -> Unit
){

    val state = viewModel.state.value
    val arrangement = if (state.isLoading) Arrangement.Top else Arrangement.SpaceBetween


    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = arrangement
    )
    {
        screenLogoAndTitle(currentScreenTitle = stringResource(R.string.register))

        if (state.isLoading){
            Spacer(modifier = Modifier.height(100.dp))
            CircularProgressIndicator(Modifier.height(100.dp).width(100.dp))
        }else{
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.height(300.dp)

            ) {

                Text(
                    text = stringResource(id = R.string.register_role_question),
                    color = colorResource(id = R.color.black),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                FilledTonalButton(
                    onClick = {
                        viewModel.onEvent(RegisterEvent.SetRole(Constants.ROLE_CUSTOMER))
                        onRoleSelected(viewModel.state.value.role)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                {
                    androidx.compose.material3.Text(
                        text = stringResource(id = R.string.role_customer),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }


                FilledTonalButton(
                    onClick = {
                        viewModel.onEvent(RegisterEvent.SetRole(Constants.ROLE_EMPLOYEE))
                        onRoleSelected(viewModel.state.value.role)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                {
                    androidx.compose.material3.Text(
                        text = stringResource(id = R.string.role_employee),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                FilledTonalButton(
                    onClick = {
                        viewModel.onEvent(RegisterEvent.SetRole(Constants.ROLE_OWNER))
                        onRoleSelected(viewModel.state.value.role)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                {
                    androidx.compose.material3.Text(
                        text = stringResource(id = R.string.role_owner),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

            }
        }

    }
}

