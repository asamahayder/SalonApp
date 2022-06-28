package com.example.salonapp.presentation.login_and_register.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
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
import com.example.salonapp.presentation.login_and_register.LoginAndRegisterViewModel
import com.example.salonapp.presentation.login_and_register.RegistrationFormEvent

@Composable
fun RegisterRoleSelection(
    viewModel: LoginAndRegisterViewModel = hiltViewModel()
){
    val state = viewModel.state.value


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
            onClick = { viewModel.onEvent(RegistrationFormEvent.SetRole(Constants.ROLE_CUSTOMER)) },
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
            onClick = { viewModel.onEvent(RegistrationFormEvent.SetRole(Constants.ROLE_EMPLOYEE)) },
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
            onClick = { viewModel.onEvent(RegistrationFormEvent.SetRole(Constants.ROLE_OWNER)) },
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

//        ActionButton(
//            backgroundColor = colorResource(id = R.color.blue_primary),
//            textColor = colorResource(id = R.color.white),
//            text = stringResource(id = R.string.role_owner),
//            onClick = {
//                viewModel.onEvent(RegistrationFormEvent.SetRole(Constants.ROLE_CUSTOMER))
//            }
//        )
//
//        ActionButton(
//            backgroundColor = colorResource(id = R.color.yellow_secondary),
//            textColor = colorResource(id = R.color.black),
//            text = stringResource(id = R.string.role_employee),
//            onClick = {
//                viewModel.onEvent(RegistrationFormEvent.SetRole(Constants.ROLE_EMPLOYEE))
//            }
//        )
//
//        ActionButton(
//            backgroundColor = colorResource(id = R.color.yellow_primary),
//            textColor = colorResource(id = R.color.black),
//            text = stringResource(id = R.string.role_owner),
//            onClick = {
//                viewModel.onEvent(RegistrationFormEvent.SetRole(Constants.ROLE_OWNER))
//            }
//        )

    }

}

