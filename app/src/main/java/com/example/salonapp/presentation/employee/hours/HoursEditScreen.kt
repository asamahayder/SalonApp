package com.example.salonapp.presentation.employee.hours


import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
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
import com.example.salonapp.common.Constants
import com.example.salonapp.presentation.components.profile.profile_card.ProfileCardEvent
import com.example.salonapp.presentation.components.screenLogoAndTitle
import com.example.salonapp.presentation.owner.salon.SalonCreateEditEvent
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalTime

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier =
    composed {
        clickable(indication = null,
            interactionSource = remember { MutableInteractionSource() }) {
            onClick()
        }.wrapContentWidth()
    }

@Composable
fun HoursEditScreen(
    viewModel: HoursEditViewModel = hiltViewModel(),
    onUpdateSuccess: () -> Unit
){

    val state = viewModel.state.value

    val mondayStartTimeDialogState = rememberMaterialDialogState()
    MaterialDialog(
        dialogState = mondayStartTimeDialogState,
        buttons = {
            positiveButton("Ok")
            negativeButton("Cancel")
        }
    ) {

        timepicker(is24HourClock = true, initialTime = state.openingHours?.mondayStart ?: LocalTime.now()) { time ->
            viewModel.onEvent(HoursEditEvent.OnSetMondayStart(time))
        }

    }

    val mondayEndTimeDialogState = rememberMaterialDialogState()
    MaterialDialog(
        dialogState = mondayEndTimeDialogState,
        buttons = {
            positiveButton("Ok")
            negativeButton("Cancel")
        }
    ) {

        timepicker(is24HourClock = true, initialTime = state.openingHours?.mondayEnd ?: LocalTime.now()) { time ->
            viewModel.onEvent(HoursEditEvent.OnSetMondayEnd(time))
        }

    }

    val tuesdayStartTimeDialogState = rememberMaterialDialogState()
    MaterialDialog(
        dialogState = tuesdayStartTimeDialogState,
        buttons = {
            positiveButton("Ok")
            negativeButton("Cancel")
        }
    ) {

        timepicker(is24HourClock = true, initialTime = state.openingHours?.tuesdayStart ?: LocalTime.now()) { time ->
            viewModel.onEvent(HoursEditEvent.OnSetTuesdayStart(time))
        }

    }

    val tuesdayEndTimeDialogState = rememberMaterialDialogState()
    MaterialDialog(
        dialogState = tuesdayEndTimeDialogState,
        buttons = {
            positiveButton("Ok")
            negativeButton("Cancel")
        }
    ) {

        timepicker(is24HourClock = true, initialTime = state.openingHours?.tuesdayEnd ?: LocalTime.now()) { time ->
            viewModel.onEvent(HoursEditEvent.OnSetTuesdayEnd(time))
        }

    }

    val wednesdayStartTimeDialogState = rememberMaterialDialogState()
    MaterialDialog(
        dialogState = wednesdayStartTimeDialogState,
        buttons = {
            positiveButton("Ok")
            negativeButton("Cancel")
        }
    ) {

        timepicker(is24HourClock = true, initialTime = state.openingHours?.wednessdayStart ?: LocalTime.now()) { time ->
            viewModel.onEvent(HoursEditEvent.OnSetWednesdayStart(time))
        }

    }

    val wednesdayEndTimeDialogState = rememberMaterialDialogState()
    MaterialDialog(
        dialogState = wednesdayEndTimeDialogState,
        buttons = {
            positiveButton("Ok")
            negativeButton("Cancel")
        }
    ) {

        timepicker(is24HourClock = true, initialTime = state.openingHours?.wednessdayEnd ?: LocalTime.now()) { time ->
            viewModel.onEvent(HoursEditEvent.OnSetWednesdayEnd(time))
        }

    }

    val thursdayStartTimeDialogState = rememberMaterialDialogState()
    MaterialDialog(
        dialogState = thursdayStartTimeDialogState,
        buttons = {
            positiveButton("Ok")
            negativeButton("Cancel")
        }
    ) {

        timepicker(is24HourClock = true, initialTime = state.openingHours?.thursdayStart ?: LocalTime.now()) { time ->
            viewModel.onEvent(HoursEditEvent.OnSetThursdayStart(time))
        }

    }

    val thursdayEndTimeDialogState = rememberMaterialDialogState()
    MaterialDialog(
        dialogState = thursdayEndTimeDialogState,
        buttons = {
            positiveButton("Ok")
            negativeButton("Cancel")
        }
    ) {

        timepicker(is24HourClock = true, initialTime = state.openingHours?.thursdayEnd ?: LocalTime.now()) { time ->
            viewModel.onEvent(HoursEditEvent.OnSetThursdayEnd(time))
        }

    }

    val fridayStartTimeDialogState = rememberMaterialDialogState()
    MaterialDialog(
        dialogState = fridayStartTimeDialogState,
        buttons = {
            positiveButton("Ok")
            negativeButton("Cancel")
        }
    ) {

        timepicker(is24HourClock = true, initialTime = state.openingHours?.fridayStart ?: LocalTime.now()) { time ->
            viewModel.onEvent(HoursEditEvent.OnSetFridayStart(time))
        }

    }

    val fridayEndTimeDialogState = rememberMaterialDialogState()
    MaterialDialog(
        dialogState = fridayEndTimeDialogState,
        buttons = {
            positiveButton("Ok")
            negativeButton("Cancel")
        }
    ) {

        timepicker(is24HourClock = true, initialTime = state.openingHours?.fridayEnd ?: LocalTime.now()) { time ->
            viewModel.onEvent(HoursEditEvent.OnSetFridayEnd(time))
        }

    }

    val saturdayStartTimeDialogState = rememberMaterialDialogState()
    MaterialDialog(
        dialogState = saturdayStartTimeDialogState,
        buttons = {
            positiveButton("Ok")
            negativeButton("Cancel")
        }
    ) {

        timepicker(is24HourClock = true, initialTime = state.openingHours?.saturdayStart ?: LocalTime.now()) { time ->
            viewModel.onEvent(HoursEditEvent.OnSetSaturdayStart(time))
        }

    }

    val saturdayEndTimeDialogState = rememberMaterialDialogState()
    MaterialDialog(
        dialogState = saturdayEndTimeDialogState,
        buttons = {
            positiveButton("Ok")
            negativeButton("Cancel")
        }
    ) {

        timepicker(is24HourClock = true, initialTime = state.openingHours?.saturdayEnd ?: LocalTime.now()) { time ->
            viewModel.onEvent(HoursEditEvent.OnSetSaturdayEnd(time))
        }

    }

    val sundayStartTimeDialogState = rememberMaterialDialogState()
    MaterialDialog(
        dialogState = sundayStartTimeDialogState,
        buttons = {
            positiveButton("Ok")
            negativeButton("Cancel")
        }
    ) {

        timepicker(is24HourClock = true, initialTime = state.openingHours?.sundayStart ?: LocalTime.now()) { time ->
            viewModel.onEvent(HoursEditEvent.OnSetSundayStart(time))
        }

    }

    val sundayEndTimeDialogState = rememberMaterialDialogState()
    MaterialDialog(
        dialogState = sundayEndTimeDialogState,
        buttons = {
            positiveButton("Ok")
            negativeButton("Cancel")
        }
    ) {

        timepicker(is24HourClock = true, initialTime = state.openingHours?.sundayEnd ?: LocalTime.now()) { time ->
            viewModel.onEvent(HoursEditEvent.OnSetSundayEnd(time))
        }

    }




    LaunchedEffect(key1 = LocalContext.current) {
        viewModel.events.collect { event ->
            when (event) {
                is HoursEditEvent.OnUpdateSuccess-> {
                    onUpdateSuccess()
                }

            }
        }
    }

    DisposableEffect(key1 = viewModel) {
        viewModel.onEvent(HoursEditEvent.OnInitialize)
        onDispose {  }
    }


    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp, 50.dp, 20.dp, 0.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        screenLogoAndTitle(currentScreenTitle = stringResource(R.string.hours_edit))

        Spacer(modifier = Modifier.height(20.dp))

        Divider()

        Box(modifier = Modifier.fillMaxSize()){

            if (state.isLoading){

                CircularProgressIndicator(
                    Modifier
                        .height(100.dp)
                        .width(100.dp)
                        .align(Alignment.Center)
                )

            }else if (state.error != null){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                ) {

                    Text(text = stringResource(R.string.try_again_error_message))


                    Spacer(modifier = Modifier.height(10.dp))

                    IconButton(onClick = { viewModel.onEvent(HoursEditEvent.OnInitialize) }) {
                        Icon(Icons.Filled.Refresh, contentDescription = stringResource(id = R.string.try_again))
                    }

                }


            }else if (state.openingHours != null){

                Column(
                    Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {

                    Column {
                        Text(text = "Monday: ")
                        Switch(checked = state.openingHours.mondayOpen,
                            onCheckedChange = {
                                viewModel.onEvent(HoursEditEvent.OnSetMondayOpen(it))
                            }
                        )
                        OutlinedTextField(
                            value = state.openingHours.mondayStart.toString(),
                            onValueChange = {},
                            label = {
                                Text(text = stringResource(R.string.start))
                            },
                            singleLine = true,
                            readOnly = true,
                            enabled = false,
                            modifier = Modifier
                                .noRippleClickable { mondayStartTimeDialogState.show() }
                            ,
                            colors = TextFieldDefaults.textFieldColors(
                                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledIndicatorColor = MaterialTheme.colorScheme.outline,
                                containerColor = Color.Transparent
                            )
                        )
                        OutlinedTextField(
                            value = state.openingHours.mondayEnd.toString(),
                            onValueChange = {},
                            label = {
                                Text(text = stringResource(R.string.end))
                            },
                            singleLine = true,
                            readOnly = true,
                            enabled = false,
                            modifier = Modifier
                                .noRippleClickable { mondayEndTimeDialogState.show() }
                            ,
                            colors = TextFieldDefaults.textFieldColors(
                                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledIndicatorColor = MaterialTheme.colorScheme.outline,
                                containerColor = Color.Transparent
                            )
                        )


                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Column {
                        Text(text = "Tuesday: ")
                        Switch(checked = state.openingHours.tuesdayOpen,
                            onCheckedChange = {
                                viewModel.onEvent(HoursEditEvent.OnSetTuesdayOpen(it))
                            }
                        )
                        OutlinedTextField(
                            value = state.openingHours.tuesdayStart.toString(),
                            onValueChange = {},
                            label = {
                                Text(text = stringResource(R.string.start))
                            },
                            singleLine = true,
                            readOnly = true,
                            enabled = false,
                            modifier = Modifier
                                .noRippleClickable { tuesdayStartTimeDialogState.show() }
                            ,
                            colors = TextFieldDefaults.textFieldColors(
                                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledIndicatorColor = MaterialTheme.colorScheme.outline,
                                containerColor = Color.Transparent
                            )
                        )

                        OutlinedTextField(
                            value = state.openingHours.tuesdayEnd.toString(),
                            onValueChange = {},
                            label = {
                                Text(text = stringResource(R.string.end))
                            },
                            singleLine = true,
                            readOnly = true,
                            enabled = false,
                            modifier = Modifier
                                .noRippleClickable { tuesdayEndTimeDialogState.show() }
                            ,
                            colors = TextFieldDefaults.textFieldColors(
                                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledIndicatorColor = MaterialTheme.colorScheme.outline,
                                containerColor = Color.Transparent
                            )
                        )


                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Column {
                        Text(text = "Wednesday: ")
                        Switch(checked = state.openingHours.wednessdayOpen,
                            onCheckedChange = {
                                viewModel.onEvent(HoursEditEvent.OnSetWednesdayOpen(it))
                            }
                        )
                        OutlinedTextField(
                            value = state.openingHours.wednessdayStart.toString(),
                            onValueChange = {},
                            label = {
                                Text(text = stringResource(R.string.start))
                            },
                            singleLine = true,
                            readOnly = true,
                            enabled = false,
                            modifier = Modifier
                                .noRippleClickable { wednesdayStartTimeDialogState.show() }
                            ,
                            colors = TextFieldDefaults.textFieldColors(
                                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledIndicatorColor = MaterialTheme.colorScheme.outline,
                                containerColor = Color.Transparent
                            )
                        )
                        OutlinedTextField(
                            value = state.openingHours.wednessdayEnd.toString(),
                            onValueChange = {},
                            label = {
                                Text(text = stringResource(R.string.end))
                            },
                            singleLine = true,
                            readOnly = true,
                            enabled = false,
                            modifier = Modifier
                                .noRippleClickable { wednesdayEndTimeDialogState.show() }
                            ,
                            colors = TextFieldDefaults.textFieldColors(
                                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledIndicatorColor = MaterialTheme.colorScheme.outline,
                                containerColor = Color.Transparent
                            )
                        )


                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Column {
                        Text(text = "Thursday: ")
                        Switch(checked = state.openingHours.thursdayOpen,
                            onCheckedChange = {
                                viewModel.onEvent(HoursEditEvent.OnSetThursdayOpen(it))
                            }
                        )
                        OutlinedTextField(
                            value = state.openingHours.thursdayStart.toString(),
                            onValueChange = {},
                            label = {
                                Text(text = stringResource(R.string.start))
                            },
                            singleLine = true,
                            readOnly = true,
                            enabled = false,
                            modifier = Modifier
                                .noRippleClickable { thursdayStartTimeDialogState.show() }
                            ,
                            colors = TextFieldDefaults.textFieldColors(
                                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledIndicatorColor = MaterialTheme.colorScheme.outline,
                                containerColor = Color.Transparent
                            )
                        )
                        OutlinedTextField(
                            value = state.openingHours.thursdayEnd.toString(),
                            onValueChange = {},
                            label = {
                                Text(text = stringResource(R.string.end))
                            },
                            singleLine = true,
                            readOnly = true,
                            enabled = false,
                            modifier = Modifier
                                .noRippleClickable { thursdayEndTimeDialogState.show() }
                            ,
                            colors = TextFieldDefaults.textFieldColors(
                                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledIndicatorColor = MaterialTheme.colorScheme.outline,
                                containerColor = Color.Transparent
                            )
                        )


                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Column {
                        Text(text = "Friday: ")
                        Switch(checked = state.openingHours.fridayOpen,
                            onCheckedChange = {
                                viewModel.onEvent(HoursEditEvent.OnSetFridayOpen(it))
                            }
                        )
                        OutlinedTextField(
                            value = state.openingHours.fridayStart.toString(),
                            onValueChange = {},
                            label = {
                                Text(text = stringResource(R.string.start))
                            },
                            singleLine = true,
                            readOnly = true,
                            enabled = false,
                            modifier = Modifier
                                .noRippleClickable { fridayStartTimeDialogState.show() }
                            ,
                            colors = TextFieldDefaults.textFieldColors(
                                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledIndicatorColor = MaterialTheme.colorScheme.outline,
                                containerColor = Color.Transparent
                            )
                        )
                        OutlinedTextField(
                            value = state.openingHours.fridayEnd.toString(),
                            onValueChange = {},
                            label = {
                                Text(text = stringResource(R.string.end))
                            },
                            singleLine = true,
                            readOnly = true,
                            enabled = false,
                            modifier = Modifier
                                .noRippleClickable { fridayEndTimeDialogState.show() }
                            ,
                            colors = TextFieldDefaults.textFieldColors(
                                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledIndicatorColor = MaterialTheme.colorScheme.outline,
                                containerColor = Color.Transparent
                            )
                        )


                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Column {
                        Text(text = "Saturday: ")
                        Switch(checked = state.openingHours.saturdayOpen,
                            onCheckedChange = {
                                viewModel.onEvent(HoursEditEvent.OnSetSaturdayOpen(it))
                            }
                        )
                        OutlinedTextField(
                            value = state.openingHours.saturdayStart.toString(),
                            onValueChange = {},
                            label = {
                                Text(text = stringResource(R.string.start))
                            },
                            singleLine = true,
                            readOnly = true,
                            enabled = false,
                            modifier = Modifier
                                .noRippleClickable { saturdayStartTimeDialogState.show() }
                            ,
                            colors = TextFieldDefaults.textFieldColors(
                                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledIndicatorColor = MaterialTheme.colorScheme.outline,
                                containerColor = Color.Transparent
                            )
                        )
                        OutlinedTextField(
                            value = state.openingHours.saturdayEnd.toString(),
                            onValueChange = {},
                            label = {
                                Text(text = stringResource(R.string.end))
                            },
                            singleLine = true,
                            readOnly = true,
                            enabled = false,
                            modifier = Modifier
                                .noRippleClickable { saturdayEndTimeDialogState.show() }
                            ,
                            colors = TextFieldDefaults.textFieldColors(
                                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledIndicatorColor = MaterialTheme.colorScheme.outline,
                                containerColor = Color.Transparent
                            )
                        )


                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Column {
                        Text(text = "Sunday: ")
                        Switch(checked = state.openingHours.sundayOpen,
                            onCheckedChange = {
                                viewModel.onEvent(HoursEditEvent.OnSetSundayOpen(it))
                            }
                        )
                        OutlinedTextField(
                            value = state.openingHours.sundayStart.toString(),
                            onValueChange = {},
                            label = {
                                Text(text = stringResource(R.string.start))
                            },
                            singleLine = true,
                            readOnly = true,
                            enabled = false,
                            modifier = Modifier
                                .noRippleClickable { sundayStartTimeDialogState.show() }
                            ,
                            colors = TextFieldDefaults.textFieldColors(
                                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledIndicatorColor = MaterialTheme.colorScheme.outline,
                                containerColor = Color.Transparent
                            )
                        )
                        OutlinedTextField(
                            value = state.openingHours.sundayEnd.toString(),
                            onValueChange = {},
                            label = {
                                Text(text = stringResource(R.string.end))
                            },
                            singleLine = true,
                            readOnly = true,
                            enabled = false,
                            modifier = Modifier
                                .noRippleClickable { sundayEndTimeDialogState.show() }
                            ,
                            colors = TextFieldDefaults.textFieldColors(
                                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledIndicatorColor = MaterialTheme.colorScheme.outline,
                                containerColor = Color.Transparent
                            )
                        )


                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    if (state.validationError){
                        Text(text = "Start cannot be after End", color = MaterialTheme.colorScheme.error)
                    }

                    Button(
                        onClick = { viewModel.onEvent(HoursEditEvent.OnSubmit) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    )
                    {
                        Text(
                            text = stringResource(id = R.string.hours_edit),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(30.dp))




                }

            }


        }

    }










}






