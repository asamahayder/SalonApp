package com.example.salonapp.presentation.owner.schedule


import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.salonapp.R
import com.example.salonapp.presentation.components.Schedule.Schedule
import java.time.LocalDateTime

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier =
    composed {
        clickable(indication = null,
            interactionSource = remember { MutableInteractionSource() }) {
            onClick()
        }
    }

@Composable
fun ScheduleScreen(
    viewModel: ScheduleViewModel = hiltViewModel(),
    onCreateSalon: (firstSalon: Boolean) -> Unit,
    onCreateBooking: () -> Unit
){
    val state = viewModel.state.value

    LaunchedEffect(key1 = LocalContext.current) {
        viewModel.events.collect { event ->
            when (event) {
                is ScheduleEvent.OnCreateSalon-> {
                    onCreateSalon(true)
                }
            }
        }
    }

    if (state.isLoading){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxHeight().fillMaxWidth().padding(50.dp)
        ) {
            CircularProgressIndicator(
                Modifier
                    .height(100.dp)
                    .width(100.dp))
        }

    }
    else if (!state.error.isNullOrEmpty()){
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight().padding(50.dp)
        ) {
            Text(text = state.error)
        }
    }
    else{
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(10.dp)
            ){

                Box(modifier = Modifier.weight(.45f)){
                    OutlinedTextField(
                        value = state.activeSalon?.name ?: "",
                        onValueChange = {},
                        label = {
                            Text(text = stringResource(R.string.salon))
                        },
                        trailingIcon = {
                            if (state.salonSelectionExpanded){
                                Icon(Icons.Filled.ArrowDropUp, contentDescription = stringResource(R.string.dropdown_collapse))
                            }else{
                                Icon(Icons.Filled.ArrowDropDown, contentDescription = stringResource(R.string.dropdown_expand))
                            }
                        },
                        singleLine = true,
                        readOnly = true,
                        enabled = false,
                        modifier = Modifier
                            .noRippleClickable { viewModel.onEvent(ScheduleEvent.OnToggleSalonMenu) }
                            .onGloballyPositioned { coordinates ->
                                viewModel.onEvent(ScheduleEvent.OnSetSalonSelectionWidth(coordinates.size.toSize()))
                            }
                        ,
                        colors = TextFieldDefaults.textFieldColors(
                            disabledTextColor = MaterialTheme.colorScheme.onSurface,
                            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            disabledIndicatorColor = MaterialTheme.colorScheme.outline,
                            containerColor = Color.Transparent
                        )
                    )

                    DropdownMenu(
                        expanded = state.salonSelectionExpanded,
                        onDismissRequest = {
                            viewModel.onEvent(ScheduleEvent.OnSalonSelectDismiss)
                        },
                        modifier = Modifier.width(with(LocalDensity.current){state.salonSelectionWidth.width.toDp()})
                    ) {
                        state.salons.forEach{salon ->
                            DropdownMenuItem(
                                text = {Text(text = salon.name)},
                                onClick = {
                                    viewModel.onEvent(ScheduleEvent.OnSetActiveSalon(salon))
                                }
                            )
                        }
                        MenuDefaults.Divider()
                        DropdownMenuItem(
                            text = { Text(text = "Create New Salon") },
                            onClick = {
                                onCreateSalon(false)
                            }
                        )

                    }
                }

                Spacer(modifier = Modifier.weight(.1f))

                Box(modifier = Modifier.weight(.45f)){
                    if (!state.activeSalon?.employees.isNullOrEmpty()){
                        OutlinedTextField(
                            value = createEmployeeName(state.activeEmployee?.firstName, state.activeEmployee?.lastName),
                            onValueChange = {},
                            label = {
                                Text(text = stringResource(R.string.employee))
                            },
                            trailingIcon = {
                                if (state.employeeSelectionExpanded){
                                    Icon(Icons.Filled.ArrowDropUp, contentDescription = stringResource(R.string.dropdown_collapse))
                                }else{
                                    Icon(Icons.Filled.ArrowDropDown, contentDescription = stringResource(R.string.dropdown_expand))
                                }
                            },
                            singleLine = true,
                            readOnly = true,
                            enabled = false,
                            modifier = Modifier
                                .noRippleClickable { viewModel.onEvent(ScheduleEvent.OnToggleEmployeeMenu) }
                                .onGloballyPositioned { coordinates ->
                                    viewModel.onEvent(ScheduleEvent.OnSetEmployeeSelectionWidth(coordinates.size.toSize()))
                                }
                            ,
                            colors = TextFieldDefaults.textFieldColors(
                                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledIndicatorColor = MaterialTheme.colorScheme.outline,
                                containerColor = Color.Transparent
                            )
                        )

                        DropdownMenu(
                            expanded = state.employeeSelectionExpanded,
                            onDismissRequest = {
                                viewModel.onEvent(ScheduleEvent.OnEmployeeSelectDismiss)
                            },
                            modifier = Modifier.width(with(LocalDensity.current){state.employeeSelectionWidth.width.toDp()})
                        ) {
                            state.activeSalon?.employees?.forEach{ employee ->
                                DropdownMenuItem(
                                    text = {Text(text = createEmployeeName(employee.firstName, employee.lastName))},
                                    onClick = {
                                        viewModel.onEvent(ScheduleEvent.OnSetActiveEmployee(employee))
                                    }
                                )
                            }
                        }

                    }
                }

            }

            Divider()

            if (state.salons.isNullOrEmpty()){
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxHeight().padding(50.dp)
                ) {
                    Text(text = stringResource(R.string.please_create_a_salon))
                }
            }else if (state.employees.isNullOrEmpty()){
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxHeight().padding(50.dp)
                ) {
                    Text(text = stringResource(R.string.no_employees_to_show_message))
                }
            }else{
                Schedule(
                    listOf(),
                    onWeekChanged = {
                        viewModel.onEvent(ScheduleEvent.OnWeekChanged(it))
                    },
                    onCreateBook = {

                    }
                )
            }

        }
    }
}


private fun createEmployeeName(firstName: String?, lastName: String?): String{
    if (firstName == null || lastName == null) return ""

    val firstLetterOfLastName = lastName[0].uppercaseChar()

    return "$firstName $firstLetterOfLastName."
}


