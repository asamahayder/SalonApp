package com.example.salonapp.presentation.components.booking



import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.salonapp.R
import com.example.salonapp.presentation.components.screenLogoAndTitle
import com.example.salonapp.presentation.owner.schedule.ScheduleEvent
import com.example.salonapp.presentation.owner.schedule.noRippleClickable

@Composable
fun ServicesCreateEditScreen(
    viewModel: BookingCreateEditViewModel = hiltViewModel(),
    bookingId: Int?,
    onBookingCreatedOrUpdated: () -> Unit,
){




    val state = viewModel.state.value
    val focusManager = LocalFocusManager.current

    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        viewModel.events.collect { event ->
            when (event) {
                is BookingCreateEditEvent.OnBookingCreatedOrUpdated -> {
                    onBookingCreatedOrUpdated()
                }
            }
        }
    }

    DisposableEffect(key1 = viewModel) {
        viewModel.onEvent(BookingCreateEditEvent.Initialize(bookingId))
        onDispose {  }
    }


    val arrangement = if (state.isLoading || state.services.isEmpty()) Arrangement.Top else Arrangement.SpaceBetween

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

        val screenTitle = if(bookingId != null) stringResource(R.string.service_edit) else stringResource(R.string.create_service)
        screenLogoAndTitle(currentScreenTitle = screenTitle)

        if (state.isLoading) {
            Spacer(modifier = Modifier.height(100.dp))
            CircularProgressIndicator(
                Modifier
                    .height(100.dp)
                    .width(100.dp))
        }else if(state.services.isEmpty()) {
            Spacer(modifier = Modifier.height(100.dp))
            Text(text = stringResource(R.string.employee_need_service))
        }
        else {
            Spacer(modifier = Modifier.height(50.dp))

            Box{
                OutlinedTextField(
                    value = state.selectedService?.name ?: "",
                    onValueChange = {},
                    label = {
                        Text(text = stringResource(R.string.service))
                    },
                    trailingIcon = {
                        if (state.serviceSelectionExpanded){
                            Icon(Icons.Filled.ArrowDropUp, contentDescription = stringResource(R.string.dropdown_collapse))
                        }else{
                            Icon(Icons.Filled.ArrowDropDown, contentDescription = stringResource(R.string.dropdown_expand))
                        }
                    },
                    singleLine = true,
                    readOnly = true,
                    enabled = false,
                    modifier = Modifier
                        .noRippleClickable { viewModel.onEvent(BookingCreateEditEvent.OnToggleServiceMenu) }
                        .onGloballyPositioned { coordinates ->
                            viewModel.onEvent(BookingCreateEditEvent.OnSetServiceSelectionWidth(coordinates.size.toSize()))
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
                    expanded = state.serviceSelectionExpanded,
                    onDismissRequest = {
                        viewModel.onEvent(BookingCreateEditEvent.OnServiceSelectDismiss)
                    },
                    modifier = Modifier.width(with(LocalDensity.current){state.serviceSelectionWidth.width.toDp()})
                ) {
                    state.services.forEach{service ->
                        DropdownMenuItem(
                            text = {Text(text = service.name)},
                            onClick = {
                                viewModel.onEvent(BookingCreateEditEvent.OnSetActiveService(service))
                            }
                        )
                    }
                }
            }



            val datePickerDialog = DatePickerDialog(
                context,
                { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                    viewModel.onEvent(BookingCreateEditEvent.OnSetDateTimeService("$mDayOfMonth/${mMonth+1}/$mYear"))
                }, mYear, mMonth, mDay
            )





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



