package com.example.salonapp.presentation.login_and_register.components


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.salonapp.R
import com.example.salonapp.common.Constants
import com.example.salonapp.presentation.login_and_register.LoginAndRegisterViewModel
import com.example.salonapp.presentation.login_and_register.RegistrationFormEvent

@Composable
fun RegisterDetails(
    viewModel: LoginAndRegisterViewModel = hiltViewModel(),
){
    val state = viewModel.state.value

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp),

    ) {

        OutlinedTextField(
            value = state.email,
            onValueChange = {
                viewModel.onEvent(RegistrationFormEvent.EmailChanged(it))
            },
            isError = state.emailError != null,
            modifier = Modifier
                .fillMaxWidth()
            ,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            singleLine = true,
            label = { Text(text = stringResource(id = R.string.form_email))}
        )

        if (state.emailError != null) {
            Text(
                text = state.emailError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.End)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = state.firstName,
            onValueChange = {
                viewModel.onEvent(RegistrationFormEvent.FirstnameChanged(it))
            },
            isError = state.firstNameError != null,
            modifier = Modifier
                .fillMaxWidth()
            ,
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
                viewModel.onEvent(RegistrationFormEvent.LastnameChanged(it))
            },
            isError = state.lastNameError != null,
            modifier = Modifier
                .fillMaxWidth()
            ,
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
                viewModel.onEvent(RegistrationFormEvent.PhoneChanged(it))
            },
            isError = state.phoneError != null,
            modifier = Modifier
                .fillMaxWidth()
            ,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone
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
            value = state.password,
            onValueChange = {
                viewModel.onEvent(RegistrationFormEvent.PasswordChanged(it))
            },
            isError = state.passwordError != null,
            modifier = Modifier
                .fillMaxWidth()
            ,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            singleLine = true,
            label = { Text(text = stringResource(id = R.string.form_password))},
            visualTransformation = PasswordVisualTransformation()
        )

        if (state.passwordError != null) {
            Text(
                text = state.passwordError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.End)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.passwordConfirm,
            onValueChange = {
                viewModel.onEvent(RegistrationFormEvent.ConfirmPasswordChanged(it))
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
            onClick = { viewModel.onEvent(RegistrationFormEvent.Submit) },
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



