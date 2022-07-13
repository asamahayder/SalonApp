package com.example.salonapp.presentation.owner.salon

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.salonapp.R
import com.example.salonapp.presentation.components.profile.profile_card.ProfileCardEvent
import com.example.salonapp.presentation.components.screenLogoAndTitle
import com.example.salonapp.presentation.employee.request.RequestScreenEvent


@Composable
fun SalonCreateEditScreen(
    viewModel: SalonCreateEditViewModel = hiltViewModel(),
    salonId:Int? = null,
    firstSalon:Boolean = false,
    onSalonCreated: () -> Unit
){
    val state = viewModel.state.value
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        viewModel.events.collect { event ->
            when (event) {
                is SalonCreateEditEvent.OnFinishedAction -> {
                    onSalonCreated()
                }
            }
        }
    }

    DisposableEffect(key1 = viewModel) {
        viewModel.onEvent(SalonCreateEditEvent.OnInitialize(salonId))
        onDispose {  }
    }


    val toastMessage = stringResource(R.string.must_create_first_salon)
    if (firstSalon){
        BackHandler {
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
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

        val title = if(salonId != null) stringResource(R.string.salon_edit_title) else stringResource(R.string.create_salon)

        screenLogoAndTitle(currentScreenTitle = title)

        if (state.isLoading){
            Spacer(modifier = Modifier.height(100.dp))
            CircularProgressIndicator(
                Modifier
                    .height(100.dp)
                    .width(100.dp))
        }else{

            OutlinedTextField(
                value = state.name,
                onValueChange = {
                    viewModel.onEvent(SalonCreateEditEvent.NameChanged(it))
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
                label = { Text(text = stringResource(R.string.salon_name)) }
            )

            if (state.nameError != null) {
                Text(
                    text = state.nameError,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.email ?: "",
                onValueChange = {
                    viewModel.onEvent(SalonCreateEditEvent.EmailChanged(it))
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
                label = { Text(text = stringResource(R.string.salon_email)) }
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
                value = state.phone,
                onValueChange = {
                    viewModel.onEvent(SalonCreateEditEvent.PhoneChanged(it))
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
                label = { Text(text = stringResource(R.string.salon_phone)) }
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
                value = state.city,
                onValueChange = {
                    viewModel.onEvent(SalonCreateEditEvent.CityChanged(it))
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
                label = { Text(text = stringResource(R.string.city)) }
            )

            if (state.cityError != null) {
                Text(
                    text = state.cityError,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.postCode,
                onValueChange = {
                    viewModel.onEvent(SalonCreateEditEvent.PostCodeChanged(it))
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
                label = { Text(text = stringResource(R.string.postcode)) }
            )

            if (state.postCodeError != null) {
                Text(
                    text = state.postCodeError,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.streetName,
                onValueChange = {
                    viewModel.onEvent(SalonCreateEditEvent.StreetNameChanged(it))
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
                label = { Text(text = stringResource(R.string.streetname)) }
            )

            if (state.streetNameError != null) {
                Text(
                    text = state.streetNameError,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.streetNumber,
                onValueChange = {
                    viewModel.onEvent(SalonCreateEditEvent.StreetNumberChanged(it))
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
                label = { Text(text = stringResource(R.string.streetnumber)) }
            )

            if (state.streetNumberError != null) {
                Text(
                    text = state.streetNumberError,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.suit ?: "",
                onValueChange = {
                    viewModel.onEvent(SalonCreateEditEvent.SuitChanged(it))
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
                label = { Text(text = "Suit") }
            )

            if (state.suitError != null) {
                Text(
                    text = state.suitError,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.door ?: "",
                onValueChange = {
                    viewModel.onEvent(SalonCreateEditEvent.DoorChanged(it))
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
                label = { Text(text = stringResource(R.string.door)) }
            )

            if (state.doorError != null) {
                Text(
                    text = state.doorError,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }

            Spacer(modifier = Modifier.height(50.dp))

            Button(
                onClick = { viewModel.onEvent(SalonCreateEditEvent.Submit) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            if (salonId != null){
                TextButton(onClick = { viewModel.onEvent(SalonCreateEditEvent.OnShowDeleteAlert) }) {
                    Text(
                        text = stringResource(R.string.salon_delete),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )
                }
            }

            if(state.showAlert){
                AlertDialog(
                    title = {
                        Text(text = stringResource(R.string.salon_delete))
                    },
                    text = {
                        Text(text = stringResource(R.string.sure_delete_salon_text))
                    },
                    confirmButton = {
                        TextButton(onClick = { viewModel.onEvent(SalonCreateEditEvent.OnDeleteSalon) }) {
                            Text(text = stringResource(R.string.confirm))
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { viewModel.onEvent(SalonCreateEditEvent.OnDismissDeleteAlert) }) {
                            Text(text = stringResource(R.string.dismiss))
                        }
                    },
                    onDismissRequest = {
                        viewModel.onEvent(SalonCreateEditEvent.OnDismissDeleteAlert)
                    }
                )
            }

        }

    }
}

