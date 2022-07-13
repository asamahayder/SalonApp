package com.example.salonapp.presentation.components.booking



import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.salonapp.R
import com.example.salonapp.common.SessionManager
import com.example.salonapp.common.Utils
import com.example.salonapp.domain.models.isOpen
import com.example.salonapp.presentation.components.screenLogoAndTitle
import com.example.salonapp.presentation.employee.hours.HoursEditEvent
import com.example.salonapp.presentation.employee.hours.noRippleClickable
import com.example.salonapp.presentation.employee.request.RequestScreenEvent
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime


inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier =
    composed {
        clickable(indication = null,
            interactionSource = remember { MutableInteractionSource() }) {
            onClick()
        }.wrapContentWidth()
    }

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BookingCreateEditScreen(
    viewModel: BookingCreateEditViewModel = hiltViewModel(),
    bookingId:Int? = null,
    onBookingCreatedOrUpdated: () -> Unit,
    onReturnToPreviousScreen: () -> Unit
){
    val state = viewModel.state.value


    LaunchedEffect(key1 = LocalContext.current) {
        viewModel.events.collect { event ->
            when (event) {
                is BookingCreateEditEvent.OnSubmitSuccess -> {
                    onBookingCreatedOrUpdated()
                }
                is BookingCreateEditEvent.OnReturnToPreviousScreen -> {
                    onReturnToPreviousScreen()
                }
            }
        }
    }

    DisposableEffect(key1 = viewModel) {
        viewModel.onEvent(BookingCreateEditEvent.OnInitialize(bookingId = bookingId))
        onDispose {  }
    }

    BackHandler {
        viewModel.onEvent(BookingCreateEditEvent.OnBack)
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 50.dp), horizontalAlignment = Alignment.CenterHorizontally) {

        val title = if(bookingId != null) "Update Booking" else "Create Booking"

        screenLogoAndTitle(currentScreenTitle = title)

        Spacer(modifier = Modifier.height(20.dp))

        Box(modifier = Modifier.fillMaxSize()) {

            if (state.isLoading) {

                CircularProgressIndicator(
                    Modifier
                        .height(100.dp)
                        .width(100.dp)
                        .align(Alignment.Center)
                )

            } else if (state.error != null) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                ) {

                    Text(text = stringResource(R.string.try_again_error_message))

                    Spacer(modifier = Modifier.height(10.dp))

                    IconButton(onClick = { viewModel.onEvent(BookingCreateEditEvent.OnInitialize(bookingId = bookingId)) }) {
                        Icon(
                            Icons.Filled.Refresh,
                            contentDescription = stringResource(id = R.string.try_again)
                        )
                    }

                }


            } else {

                Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                    when(state.activeStep){
                        1 -> {
                            Spacer(modifier = Modifier.height(20.dp))

                            Text(text = "Choose Salon", fontWeight = FontWeight.Bold, fontSize = 20.sp)

                            Spacer(modifier = Modifier.height(5.dp))

                            Divider()

                            Spacer(modifier = Modifier.height(10.dp))


                            if(state.salons.isEmpty()){
                                Text(text = "No salons to show", fontWeight = FontWeight.Light, fontSize = 15.sp)
                            }

                            LazyColumn{
                                items(state.salons){salon ->
                                    ListItem(
                                        modifier = Modifier.clickable {
                                            viewModel.onEvent(BookingCreateEditEvent.OnInitializeEmployees(salon))
                                        },
                                        text = {
                                            Text(
                                                text = salon.name,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        },
                                        secondaryText = {
                                            val address = "${salon.postCode} ${salon.city} - ${salon.streetName} ${salon.streetNumber}"
                                            Text(
                                                text = address,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                                fontStyle = FontStyle.Italic,
                                                fontWeight = FontWeight.Light
                                            )
                                        },
                                        singleLineSecondaryText = true
                                    )

                                }

                            }

                        }
                        2 -> {
                            Spacer(modifier = Modifier.height(20.dp))

                            Text(text = "Choose Employee", fontWeight = FontWeight.Bold, fontSize = 20.sp)

                            Spacer(modifier = Modifier.height(5.dp))

                            Divider()

                            Spacer(modifier = Modifier.height(10.dp))

                            if(state.employees.isEmpty()){
                                Text(text = "No employees to show", fontWeight = FontWeight.Light, fontSize = 15.sp)
                            }

                            LazyColumn{
                                items(state.employees){employee ->
                                    ListItem(
                                        modifier = Modifier.clickable {
                                            viewModel.onEvent(BookingCreateEditEvent.OnInitializeServices(employee = employee))
                                        },
                                        text = {
                                            Text(
                                                text = Utils.formatFullName(employee.firstName, employee.lastName),
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                    )

                                }

                            }

                        }
                        3 -> {
                            Spacer(modifier = Modifier.height(20.dp))

                            Text(text = "Choose Service", fontWeight = FontWeight.Bold, fontSize = 20.sp)

                            Spacer(modifier = Modifier.height(5.dp))

                            Divider()

                            Spacer(modifier = Modifier.height(10.dp))

                            if(state.services.isEmpty()){
                                Text(text = "No services to show", fontWeight = FontWeight.Light, fontSize = 15.sp)
                            }

                            LazyColumn{
                                items(state.services){service ->
                                    ListItem(
                                        modifier = Modifier.clickable {
                                            viewModel.onEvent(BookingCreateEditEvent.OnInitializeDates(service = service))
                                        },
                                        text = {
                                            Text(
                                                text = service.name,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        },
                                        secondaryText = {
                                            Text(
                                                text = service.price.toString() + "kr. - " + service.description,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                                fontStyle = FontStyle.Italic,
                                                fontWeight = FontWeight.Light
                                            )
                                        },
                                        singleLineSecondaryText = true
                                    )
                                }

                            }

                        }
                        4 -> {
                            val dateDialogState = rememberMaterialDialogState()

                            Spacer(modifier = Modifier.height(20.dp))

                            Text(text = "Choose Date", fontWeight = FontWeight.Bold, fontSize = 20.sp)

                            Spacer(modifier = Modifier.height(5.dp))

                            Divider()

                            Spacer(modifier = Modifier.height(10.dp))

                            OutlinedTextField(
                                value = if(state.chosenDate != null) state.chosenDate.toString() else "",
                                onValueChange = {},
                                label = {
                                    Text(text = stringResource(R.string.date))
                                },
                                singleLine = true,
                                readOnly = true,
                                enabled = false,
                                modifier = Modifier
                                    .noRippleClickable { dateDialogState.show() }
                                ,
                                colors = TextFieldDefaults.textFieldColors(
                                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    disabledIndicatorColor = MaterialTheme.colorScheme.outline,
                                    containerColor = Color.Transparent
                                )
                            )

                            MaterialDialog(
                                dialogState = dateDialogState,
                                buttons = {
                                    positiveButton("Ok")
                                    negativeButton("Cancel")
                                }
                            ) {

                                datepicker(
                                    allowedDateValidator = {
                                        (it.isAfter(LocalDate.now().minusDays(1))) && (state.openingHours?.isOpen(it) ?: false)
                                    }
                                ) { date ->
                                    viewModel.onEvent(BookingCreateEditEvent.OnInitializeTimes(date))
                                }

                            }

                        }
                        5 -> {
                            Spacer(modifier = Modifier.height(20.dp))

                            Text(text = "Choose Time", fontWeight = FontWeight.Bold, fontSize = 20.sp)

                            Spacer(modifier = Modifier.height(5.dp))

                            Divider()

                            Spacer(modifier = Modifier.height(10.dp))

                            if(state.availableTimes.isEmpty()){
                                Text(text = "No available times", fontWeight = FontWeight.Light, fontSize = 15.sp)
                            }

                            LazyColumn{
                                items(state.availableTimes){time ->
                                    ListItem(
                                        modifier = Modifier.clickable {
                                            viewModel.onEvent(BookingCreateEditEvent.OnInitializeSummary(time))
                                        },
                                        text = {
                                            Text(
                                                text = time.toString(),
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                    )
                                }

                            }

                        }
                        6 -> {

                            Spacer(modifier = Modifier.height(20.dp))

                            Text(text = "Summary", fontWeight = FontWeight.Bold, fontSize = 20.sp)

                            Spacer(modifier = Modifier.height(5.dp))

                            Divider()

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                                Column {
                                    Text(text = "Salon: ")
                                    Text(text = "Employee: ")
                                    Text(text = "Service: ")
                                    Text(text = "Date: ")
                                    Text(text = "Time: ")
                                }
                                Spacer(modifier = Modifier.width(20.dp))
                                Column {
                                    Text(text = state.chosenSalon?.name ?: "null", maxLines = 1, overflow = TextOverflow.Ellipsis)

                                    val chosenEmployee = state.chosenEmployee
                                    val employeeName =
                                        if(chosenEmployee != null) {
                                            Utils.formatFullName(chosenEmployee.firstName, chosenEmployee.lastName)
                                        }else{
                                            "null"
                                        }

                                    Text(text = employeeName)
                                    Text(text = state.chosenService?.name ?: "null", maxLines = 1, overflow = TextOverflow.Ellipsis)
                                    Text(text = state.chosenDate?.toString() ?: "null", maxLines = 1, overflow = TextOverflow.Ellipsis)
                                    Text(text = state.chosenTime?.toString() ?: "null", maxLines = 1, overflow = TextOverflow.Ellipsis)

                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            if (state.booking != null){
                                Text(text = "Hint: Press android back button to go to previous steps.", textAlign = TextAlign.Center)
                            }

                            Spacer(modifier = Modifier.height(40.dp))

                            //Button
                            FilledTonalButton(
                                onClick = {
                                    viewModel.onEvent(BookingCreateEditEvent.OnSubmit)
                                }
                            ) {
                                var buttonText = if(state.booking != null) "Save Changes" else "Create Booking"
                                Text(text = buttonText)
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            if (state.booking != null){
                                TextButton(onClick = { viewModel.onEvent(BookingCreateEditEvent.OnShowDeleteAlert) }) {
                                    Text(text = "Delete Booking", color = MaterialTheme.colorScheme.error)
                                }
                            }

                            if(state.showDeleteAlert){
                                AlertDialog(
                                    title = {
                                        Text(text = "Delete Booking")
                                    },
                                    text = {
                                        Text(text = "Are you sure you want to delete this booking?")
                                    },
                                    confirmButton = {
                                        TextButton(onClick = { viewModel.onEvent(BookingCreateEditEvent.OnDeleteBooking) }) {
                                            Text(text = stringResource(R.string.confirm))
                                        }
                                    },
                                    dismissButton = {
                                        TextButton(onClick = {  viewModel.onEvent(BookingCreateEditEvent.OnDismissDeleteAlert)  }) {
                                            Text(text = stringResource(R.string.dismiss))
                                        }

                                    },
                                    onDismissRequest = {
                                        viewModel.onEvent(BookingCreateEditEvent.OnDismissDeleteAlert)
                                    }
                                )
                            }


                        }



                    }

                }

            }
        }
    }

}



