package com.example.salonapp.presentation.salon_create

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.salonapp.R
import com.example.salonapp.presentation.Screen
import com.example.salonapp.presentation.login_and_register.components.screenLogoAndTitle


@Composable
fun SalonCreateScreen(
    navController: NavController,
    viewModel: SalonCreateViewModel = hiltViewModel()
){
    val state = viewModel.state.value
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = LocalContext.current) {
        viewModel.events.collect { event ->
            when (event) {
                is SalonCreateEvent.SalonCreatedSuccessfully-> {
                    navController.navigate(Screen.HomeScreen.route)
                }
            }
        }
    }

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
        screenLogoAndTitle(currentScreenTitle = stringResource(R.string.create_salon))

        if (state.isLoading){
            Spacer(modifier = Modifier.height(100.dp))
            CircularProgressIndicator(Modifier.height(100.dp).width(100.dp))
        }else{

            OutlinedTextField(
                value = state.name,
                onValueChange = {
                    viewModel.onEvent(SalonCreateEvent.NameChanged(it))
                },
                isError = state.nameError != null,
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
                label = { androidx.compose.material3.Text(text = stringResource(R.string.salon_name)) }
            )

            if (state.nameError != null) {
                androidx.compose.material3.Text(
                    text = state.nameError,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.email ?: "",
                onValueChange = {
                    viewModel.onEvent(SalonCreateEvent.EmailChanged(it))
                },
                isError = state.emailError != null,
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
                label = { androidx.compose.material3.Text(text = stringResource(R.string.salon_email)) }
            )

            if (state.emailError != null) {
                androidx.compose.material3.Text(
                    text = state.emailError,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.phone,
                onValueChange = {
                    viewModel.onEvent(SalonCreateEvent.PhoneChanged(it))
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
                label = { androidx.compose.material3.Text(text = stringResource(R.string.salon_phone)) }
            )

            if (state.phoneError != null) {
                androidx.compose.material3.Text(
                    text = state.phoneError,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.city,
                onValueChange = {
                    viewModel.onEvent(SalonCreateEvent.CityChanged(it))
                },
                isError = state.cityError != null,
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
                label = { androidx.compose.material3.Text(text = stringResource(R.string.city)) }
            )

            if (state.cityError != null) {
                androidx.compose.material3.Text(
                    text = state.cityError,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.postCode,
                onValueChange = {
                    viewModel.onEvent(SalonCreateEvent.PostCodeChanged(it))
                },
                isError = state.postCodeError != null,
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
                label = { androidx.compose.material3.Text(text = stringResource(R.string.postcode)) }
            )

            if (state.postCodeError != null) {
                androidx.compose.material3.Text(
                    text = state.postCodeError,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.streetName,
                onValueChange = {
                    viewModel.onEvent(SalonCreateEvent.StreetNameChanged(it))
                },
                isError = state.streetNameError != null,
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
                label = { androidx.compose.material3.Text(text = stringResource(R.string.streetname)) }
            )

            if (state.streetNameError != null) {
                androidx.compose.material3.Text(
                    text = state.streetNameError,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.streetNumber,
                onValueChange = {
                    viewModel.onEvent(SalonCreateEvent.StreetNumberChanged(it))
                },
                isError = state.streetNumberError != null,
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
                label = { androidx.compose.material3.Text(text = stringResource(R.string.streetnumber)) }
            )

            if (state.streetNumberError != null) {
                androidx.compose.material3.Text(
                    text = state.streetNumberError,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.suit ?: "",
                onValueChange = {
                    viewModel.onEvent(SalonCreateEvent.SuitChanged(it))
                },
                isError = state.suitError != null,
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
                label = { androidx.compose.material3.Text(text = "Suit") }
            )

            if (state.suitError != null) {
                androidx.compose.material3.Text(
                    text = state.suitError,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.door ?: "",
                onValueChange = {
                    viewModel.onEvent(SalonCreateEvent.DoorChanged(it))
                },
                isError = state.doorError != null,
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
                label = { androidx.compose.material3.Text(text = stringResource(R.string.door)) }
            )

            if (state.doorError != null) {
                androidx.compose.material3.Text(
                    text = state.doorError,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }

            Spacer(modifier = Modifier.height(50.dp))

            Button(
                onClick = { viewModel.onEvent(SalonCreateEvent.Submit) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            {
                androidx.compose.material3.Text(
                    text = stringResource(id = R.string.create_salon),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

        }

    }
}

