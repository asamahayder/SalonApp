package com.example.salonapp.presentation.login_and_register.components


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.salonapp.R
import com.example.salonapp.presentation.login_and_register.LoginAndRegisterViewModel
import com.example.salonapp.presentation.login_and_register.LoginAndRegisterEvent

@Composable
fun LoginForm(
    viewModel: LoginAndRegisterViewModel = hiltViewModel()
){
    val state = viewModel.state.value
    val focusManager = LocalFocusManager.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.height(800.dp)

    ) {

        Spacer(modifier = Modifier.height(200.dp))

        OutlinedTextField(
            value = state.emailLogin,
            onValueChange = {
                viewModel.onEvent(LoginAndRegisterEvent.EmailLoginChanged(it))
            },
            isError = state.emailLoginError != null,
            modifier = Modifier
                .fillMaxWidth()
            ,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            singleLine = true,
            label = { Text(text = stringResource(id = R.string.form_email)) }
        )

        if (state.emailLoginError != null) {
            Text(
                text = state.emailLoginError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.End)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.passwordLogin,
            onValueChange = {
                viewModel.onEvent(LoginAndRegisterEvent.PasswordLoginChanged(it))
            },
            isError = state.passwordLoginError != null,
            modifier = Modifier
                .fillMaxWidth()
            ,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            singleLine = true,
            label = { Text(text = stringResource(id = R.string.form_password)) },
            visualTransformation = PasswordVisualTransformation()
        )

        if (state.passwordLoginError != null) {
            Text(
                text = state.passwordLoginError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.End)
            )
        }

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = { viewModel.onEvent(LoginAndRegisterEvent.SubmitLogin) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )
        {
            Text(
                text = stringResource(id = R.string.login),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = { viewModel.onEvent(LoginAndRegisterEvent.GoToRegister) },
        )
        {
            Text(
                text = stringResource(id = R.string.register_new_account),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }


    }

}



