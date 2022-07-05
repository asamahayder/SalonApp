package com.example.salonapp.presentation.owner.services.create



import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.salonapp.R
import com.example.salonapp.presentation.components.screenLogoAndTitle
import com.example.salonapp.presentation.owner.salon_create.SalonCreateEvent
import com.example.salonapp.presentation.owner.schedule.noRippleClickable
import com.example.salonapp.presentation.owner.services.ServicesEvent

@Composable
fun ServicesCreateScreen(
    viewModel: ServiceCreateViewModel = hiltViewModel(),
    onCreateService: () -> Unit,
    onCreateSalon: () -> Unit,
    onEditService: (id:Int) -> Unit
){
    val state = viewModel.state.value
    val focusManager = LocalFocusManager.current

    val context = LocalContext.current

    LaunchedEffect(key1 = LocalContext.current) {
        viewModel.events.collect { event ->
            when (event) {

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

        if (state.isLoading) {
            Spacer(modifier = Modifier.height(100.dp))
            CircularProgressIndicator(
                Modifier
                    .height(100.dp)
                    .width(100.dp))
        } else {

            OutlinedTextField(
                value = state.name,
                onValueChange = {
                    viewModel.onEvent(ServiceCreateEvent.NameChanged(it))
                },
                isError = state.nameError != null,
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                singleLine = true,
                label = { Text(text = stringResource(R.string.service_name)) }
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
                value = state.description,
                onValueChange = {
                    viewModel.onEvent(ServiceCreateEvent.DescriptionChanged(it))
                },
                isError = state.descriptionError != null,
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                singleLine = false,
                maxLines = 2,
                label = { Text(text = stringResource(R.string.service_description)) }
            )

            if (state.descriptionError != null) {
                Text(
                    text = state.descriptionError,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.price,
                onValueChange = { viewModel.onEvent(ServiceCreateEvent.PriceChanged(it))
                },
                isError = state.priceError != null,
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                singleLine = true,
                label = { Text(text = stringResource(R.string.service_price)) }
            )

            if (state.priceError != null) {
                Text(
                    text = state.priceError,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.durationInMinutes,
                onValueChange = {viewModel.onEvent(ServiceCreateEvent.DurationChanged(it))},
                isError = state.durationError != null,
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                singleLine = true,
                label = { Text(text = stringResource(R.string.service_duration))}
            )

            if (state.durationError != null) {
                Text(
                    text = state.durationError,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.pauseStartInMinutes,
                onValueChange = {viewModel.onEvent(ServiceCreateEvent.PauseStartChanged(it))},
                isError = state.pauseError != null,
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                singleLine = true,
                label = { Text(text = stringResource(R.string.service_pause_start))}
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.pauseEndInMinutes,
                onValueChange = {viewModel.onEvent(ServiceCreateEvent.PauseEndChanged(it))},
                isError = state.pauseError != null,
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                singleLine = true,
                label = { Text(text = "Pause End")}
            )

            if (state.pauseError != null) {
                Text(
                    text = state.pauseError,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))


            Box(modifier = Modifier.fillMaxWidth()){
                var enabled = true
                if (state.selectableEmployees.isEmpty()) enabled = false

                FilledTonalButton(enabled = enabled,
                    onClick = {
                        viewModel.onEvent(ServiceCreateEvent.OnToggleEmployeeMenu)
                    }
                ){
                    Text(stringResource(R.string.add_employee))
                }

                DropdownMenu(
                    expanded = state.employeeSelectionExpanded,
                    onDismissRequest = {
                        viewModel.onEvent(ServiceCreateEvent.OnEmployeeSelectDismiss)
                    }
                ) {
                    state.selectableEmployees.forEachIndexed(){ index, employee ->
                        DropdownMenuItem(
                            text = {Text(text = employee.firstName)},
                            onClick = {
                                viewModel.onEvent(ServiceCreateEvent.AddEmployee(index))
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier.fillMaxWidth()) {

                state.selectedEmployees.forEachIndexed(){index, user ->

                    Row(modifier = Modifier.fillMaxWidth()){
                        Text(user.firstName + " " + user.lastName[0].uppercaseChar() + ".")

                        IconButton(onClick = { viewModel.onEvent(ServiceCreateEvent.RemoveEmployee(index)) }) {
                            Icon(Icons.Filled.Clear, stringResource(R.string.employee_remove))
                        }
                    }

                }
            }





            Spacer(modifier = Modifier.height(50.dp))

            Button(
                onClick = { viewModel.onEvent(ServiceCreateEvent.Submit) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            {
                androidx.compose.material3.Text(
                    text =  stringResource(R.string.service_create),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

        }

    }

}



