package com.example.salonapp.presentation.authentication.login


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
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
import com.example.salonapp.presentation.components.screenLogoAndTitle

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: (role:String) -> Unit,
    onRegisterClick: () -> Unit,
){
    val state = viewModel.state.value
    val focusManager = LocalFocusManager.current
    val arrangement = if (state.isLoading) Arrangement.Top else Arrangement.SpaceBetween

    LaunchedEffect(key1 = LocalContext.current) {
        viewModel.events.collect { event ->
            when (event) {
                is LoginEvent.LoginSuccess-> {
                    onLoginSuccess(event.role)
                }
            }
        }
    }

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
        screenLogoAndTitle(currentScreenTitle = stringResource(R.string.login))

        if (state.isLoading){
            Spacer(modifier = Modifier.height(100.dp))
            CircularProgressIndicator(Modifier.height(100.dp).width(100.dp))
        }else{
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.height(800.dp)

            ) {

                Spacer(modifier = Modifier.height(200.dp))

                OutlinedTextField(
                    value = state.emailLogin,
                    onValueChange = {
                        viewModel.onEvent(LoginEvent.EmailLoginChanged(it))
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
                        viewModel.onEvent(LoginEvent.PasswordLoginChanged(it))
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
                    onClick = { viewModel.onEvent(LoginEvent.SubmitLogin) },
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
                    onClick = { onRegisterClick() },
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
    }
}



