package com.example.salonapp.presentation.login_and_register.components


import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.salonapp.presentation.login_and_register.LoginAndRegisterViewModel

@Composable
fun LoginForm(
    viewModel: LoginAndRegisterViewModel = hiltViewModel()
){
    val state = viewModel.state.value

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.height(800.dp)

    ) {

        /*TextField(
            value = state.userRegisterDTO.email,
            onValueChange = { newText:String -> state.userRegisterDTO.email = newText},
            label = "Email"
        )



        Spacer(modifier = Modifier.height(50.dp))

        ActionButton(
            backgroundColor = colorResource(id = R.color.blue_primary),
            textColor = colorResource(id = R.color.white),
            text = stringResource(id = R.string.register)
        )*/

    }

}



