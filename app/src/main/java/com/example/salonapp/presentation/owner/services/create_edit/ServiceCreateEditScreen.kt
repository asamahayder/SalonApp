package com.example.salonapp.presentation.owner.services.create_edit



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import com.example.salonapp.R
import com.example.salonapp.common.Utils
import com.example.salonapp.presentation.components.screenLogoAndTitle

@Composable
fun ServicesCreateEditScreen(
    viewModel: ServiceCreateEditViewModel = hiltViewModel(),
    serviceId: Int?,
    onServiceCreatedOrEdited: () -> Unit,
){

    val state = viewModel.state.value
    val focusManager = LocalFocusManager.current

    val context = LocalContext.current

    LaunchedEffect(key1 = LocalContext.current) {
        viewModel.events.collect { event ->
            when (event) {
                is ServiceCreateEditEvent.ServiceCreatedOrEditedSuccessfully -> {
                    onServiceCreatedOrEdited()
                }

            }
        }
    }

    DisposableEffect(key1 = viewModel) {
        viewModel.onEvent(ServiceCreateEditEvent.Initialize(serviceId))
        onDispose {  }
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

        val screenTitle = if(serviceId != null) stringResource(R.string.service_edit) else stringResource(R.string.create_service)
        screenLogoAndTitle(currentScreenTitle = screenTitle)

        if (state.isLoading) {
            Spacer(modifier = Modifier.height(100.dp))
            CircularProgressIndicator(
                Modifier
                    .height(100.dp)
                    .width(100.dp))
        } else {
            Spacer(modifier = Modifier.height(50.dp))

            OutlinedTextField(
                value = state.name,
                onValueChange = {
                    viewModel.onEvent(ServiceCreateEditEvent.NameChanged(it))
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
                    viewModel.onEvent(ServiceCreateEditEvent.DescriptionChanged(it))
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
                onValueChange = { viewModel.onEvent(ServiceCreateEditEvent.PriceChanged(it))
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
                onValueChange = {viewModel.onEvent(ServiceCreateEditEvent.DurationChanged(it))},
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
                onValueChange = {viewModel.onEvent(ServiceCreateEditEvent.PauseStartChanged(it))},
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

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.pauseEndInMinutes,
                onValueChange = {viewModel.onEvent(ServiceCreateEditEvent.PauseEndChanged(it))},
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


            Box{
                var enabled = true
                if (state.selectableEmployees.isEmpty()) enabled = false

                FilledTonalButton(enabled = enabled,
                    onClick = {
                        viewModel.onEvent(ServiceCreateEditEvent.OnToggleEmployeeMenu)
                    },
                    modifier = Modifier.align(Alignment.Center)
                ){
                    Text(stringResource(R.string.add_employee))
                }

                DropdownMenu(
                    expanded = state.employeeSelectionExpanded,
                    onDismissRequest = {
                        viewModel.onEvent(ServiceCreateEditEvent.OnEmployeeSelectDismiss)
                    }
                ) {
                    state.selectableEmployees.forEachIndexed(){ index, employee ->
                        DropdownMenuItem(
                            text = {Text(text = Utils.formatName(employee.firstName, employee.lastName))},
                            onClick = {
                                viewModel.onEvent(ServiceCreateEditEvent.AddEmployee(index))
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier.fillMaxWidth()) {

                state.selectedEmployees.forEachIndexed(){ index, employee ->

                    Row(
                        modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                    ){
                        Text(Utils.formatName(employee.firstName, employee.lastName))

                        IconButton(onClick = { viewModel.onEvent(ServiceCreateEditEvent.RemoveEmployee(index)) }) {
                            Icon(Icons.Filled.Clear, stringResource(R.string.employee_remove))
                        }
                    }

                }
            }





            Spacer(modifier = Modifier.height(50.dp))

            Button(
                onClick = { viewModel.onEvent(ServiceCreateEditEvent.Submit) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            {
                Text(
                    text =  stringResource(R.string.save_changes),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

        }

    }

}



