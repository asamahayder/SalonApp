package com.example.salonapp.presentation.login_and_register.components


import androidx.activity.compose.BackHandler
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
fun RegisterDetails(
    viewModel: LoginAndRegisterViewModel = hiltViewModel(),
){
    val state = viewModel.state.value
    val focusManager = LocalFocusManager.current

    BackHandler(true) { viewModel.onEvent(LoginAndRegisterEvent.BackCalledFromRegisterDetails) }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp),

    ) {

        OutlinedTextField(
            value = state.emailRegister,
            onValueChange = {
                viewModel.onEvent(LoginAndRegisterEvent.EmailRegisterChanged(it))
            },
            isError = state.emailRegisterError != null,
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
            label = { Text(text = stringResource(id = R.string.form_email))}
        )

        if (state.emailRegisterError != null) {
            Text(
                text = state.emailRegisterError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.End)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = state.firstName,
            onValueChange = {
                viewModel.onEvent(LoginAndRegisterEvent.FirstnameChanged(it))
            },
            isError = state.firstNameError != null,
            modifier = Modifier
                .fillMaxWidth()
            ,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            singleLine = true,
            label = { Text(text = stringResource(id = R.string.form_firstname))}
        )

        if (state.firstNameError != null) {
            Text(
                text = state.firstNameError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.End)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.lastName,
            onValueChange = {
                viewModel.onEvent(LoginAndRegisterEvent.LastnameChanged(it))
            },
            isError = state.lastNameError != null,
            modifier = Modifier
                .fillMaxWidth()
            ,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            singleLine = true,
            label = { Text(text = stringResource(id = R.string.form_lastname))}
        )

        if (state.lastNameError != null) {
            Text(
                text = state.lastNameError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.End)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.phone,
            onValueChange = {
                viewModel.onEvent(LoginAndRegisterEvent.PhoneChanged(it))
            },
            isError = state.phoneError != null,
            modifier = Modifier
                .fillMaxWidth()
            ,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            singleLine = true,
            label = { Text(text = stringResource(id = R.string.form_phone))}
        )

        if (state.phoneError != null) {
            Text(
                text = state.phoneError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.End)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.passwordRegister,
            onValueChange = {
                viewModel.onEvent(LoginAndRegisterEvent.PasswordRegisterChanged(it))
            },
            isError = state.passwordRegisterError != null,
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
            label = { Text(text = stringResource(id = R.string.form_password))},
            visualTransformation = PasswordVisualTransformation()
        )

        if (state.passwordRegisterError != null) {
            Text(
                text = state.passwordRegisterError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.End)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.passwordConfirm,
            onValueChange = {
                viewModel.onEvent(LoginAndRegisterEvent.ConfirmPasswordChanged(it))
            },
            isError = state.passwordConfirmError != null,
            modifier = Modifier
                .fillMaxWidth()
            ,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            singleLine = true,
            label = { Text(text = stringResource(id = R.string.form_password_confirm))},
            visualTransformation = PasswordVisualTransformation()
        )

        if (state.passwordConfirmError != null) {
            Text(
                text = state.passwordConfirmError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.End)
            )
        }

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = { viewModel.onEvent(LoginAndRegisterEvent.SubmitRegister) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )
        {
            Text(
                text = stringResource(id = R.string.register),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

    }

}



