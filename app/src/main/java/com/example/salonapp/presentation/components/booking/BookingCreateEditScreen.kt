package com.example.salonapp.presentation.components.booking



import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.salonapp.R
import com.example.salonapp.presentation.components.screenLogoAndTitle
import com.example.salonapp.presentation.owner.salon_create.SalonCreateEvent
import com.example.salonapp.presentation.owner.schedule.noRippleClickable
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter


//@Composable
//fun StepIcon(
//    step: Step,
//    viewModel: BookingCreateEditViewModel = hiltViewModel()
//){
//    //TODO: undersøg om det virker, eller om det giver problemer at give den sin egen viewmodel
//
//    Box{
//        val color1 = MaterialTheme.colorScheme.onSurface
//        var color2 = MaterialTheme.colorScheme.onPrimary
//
//        if (step.getIntValue() == viewModel.state.value.activeStep.getIntValue()){
//            color2 = MaterialTheme.colorScheme.onSurface
//            Canvas(modifier = Modifier
//                .size(100.dp)
//                .align(Alignment.Center),
//                onDraw = {
//                    drawCircle(color = color1)
//                }
//            )
//
//        }
//
//        Text(text = step.getStringValue(),
//            modifier = Modifier.align(Alignment.Center),
//            textAlign = TextAlign.Center,
//            color = color2
//        )
//    }
//
//}

@Composable
fun BookingCreateEditScreen(){

}


/*@Composable
fun BookingCreateEditScreen(
    viewModel: BookingCreateEditViewModel = hiltViewModel(),
    bookingId: Int?,
    onBookingCreatedOrUpdated: () -> Unit,
){

    val state = viewModel.state.value
    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton("Ok")
            negativeButton("Cancel")
        }
    ) {

        datepicker(
            allowedDateValidator = {date ->
            !date.isBefore(LocalDate.now())
        }) { date ->
            viewModel.onEvent(BookingCreateEditEvent.OnSetDate(date))
        }
    }



    MaterialDialog(
        dialogState = timeDialogState,
        buttons = {
            positiveButton("Ok")
            negativeButton("Cancel")
        }
    ) {

        if (state.date != null && state.startTimeOfDay != null && state.endTimeOfDay != null){
            var startTime: LocalTime
            var timeRange: ClosedRange<LocalTime>

            if (state.date.isEqual(LocalDate.now())){
                if (LocalTime.now().isAfter(state.startTimeOfDay)){
                    startTime = LocalTime.now()
                }else{
                    startTime = state.startTimeOfDay
                }
            }else if (state.date.isAfter(LocalDate.now())){
                startTime = state.startTimeOfDay
            }else{
                startTime = LocalTime.MIDNIGHT
            }

            if(startTime == LocalTime.MIDNIGHT){
                //Just getting empty range
                timeRange = LocalTime.now().rangeTo(LocalTime.now().minusSeconds(5))
            }else{

                var endTime:LocalTime

                if (state.selectedService != null){
                    endTime = state.endTimeOfDay.minusMinutes(state.selectedService.durationInMinutes.toLong())
                }else{
                    endTime = state.endTimeOfDay
                }

                timeRange = startTime.rangeTo(endTime)
            }

            timepicker(is24HourClock = true, timeRange = timeRange.) { time ->
                viewModel.onEvent(BookingCreateEditEvent.OnSetTime(time))
            }

        }

    }

    val focusManager = LocalFocusManager.current

    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        viewModel.events.collect { event ->
            when (event) {
                is BookingCreateEditEvent.OnBookingCreatedOrUpdated -> {
                    onBookingCreatedOrUpdated()
                }
                is BookingCreateEditEvent.OnFetchedHours -> {
                    timeDialogState.show()
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

        val screenTitle = if(bookingId != null) stringResource(R.string.save_changes) else stringResource(R.string.booking_create)
        screenLogoAndTitle(currentScreenTitle = screenTitle)
        Spacer(modifier = Modifier.height(75.dp))


        if (state.isLoading) {
            CircularProgressIndicator(
                Modifier
                    .height(100.dp)
                    .width(100.dp))

        }
        else if (state.error != null){
            Text(text = state.error,
                textAlign = TextAlign.Center
            )
        }
        else if(state.services.isEmpty()) {
            Text(text = stringResource(R.string.employee_need_service),
                textAlign = TextAlign.Center
            )
        }
        else {

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                StepIcon(step = Step.one)
                Icon(Icons.Filled.ArrowRightAlt, contentDescription = stringResource(R.string.next_step_icon))
                StepIcon(step = Step.two)
                Icon(Icons.Filled.ArrowRightAlt, contentDescription = stringResource(R.string.next_step_icon))
                StepIcon(step = Step.three)
                Icon(Icons.Filled.ArrowRightAlt, contentDescription = stringResource(R.string.next_step_icon))
                StepIcon(step = Step.four)
            }

            when(state.activeStep.getIntValue()){
                1 -> {
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
                                    viewModel.onEvent(
                                        BookingCreateEditEvent.OnSetServiceSelectionWidth(
                                            coordinates.size.toSize()
                                        )
                                    )
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



                }
                2 -> {
                    OutlinedTextField(
                        value = if(state.date != null) state.date.format(DateTimeFormatter.ofPattern("dd/MM-y")).toString() else "",
                        onValueChange = {},
                        label = {
                            Text(text = stringResource(R.string.date))
                        },
                        singleLine = true,
                        readOnly = true,
                        enabled = false,
                        modifier = Modifier
                            .noRippleClickable {
                                dateDialogState.show()
                            }
                        ,
                        colors = TextFieldDefaults.textFieldColors(
                            disabledTextColor = MaterialTheme.colorScheme.onSurface,
                            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            disabledIndicatorColor = if (state.dateError == null) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.error,
                            containerColor = Color.Transparent
                        ),
                        isError = state.dateError != null
                    )

                    if (state.dateError != null) {
                        Text(
                            text = state.dateError,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }
                }
                3 -> {
                    var timeValue:String =
                        if (state.fetchingOpeningHours){
                            stringResource(R.string.please_wait)
                        }else{
                            state.time?.format(DateTimeFormatter.ofPattern("HH:mm")).toString()
                        }

                    if (timeValue == "null") timeValue = ""

                    OutlinedTextField(
                        value = timeValue,
                        onValueChange = {},
                        label = {
                            Text(text = stringResource(R.string.time))
                        },
                        singleLine = true,
                        readOnly = true,
                        enabled = false,
                        modifier = Modifier
                            .noRippleClickable {
                                if(!state.fetchingOpeningHours){
                                    timeDialogState.show()
                                }
                            }
                        ,
                        colors = TextFieldDefaults.textFieldColors(
                            disabledTextColor = MaterialTheme.colorScheme.onSurface,
                            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            disabledIndicatorColor = if (state.timeError == null) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.error,
                            containerColor = Color.Transparent
                        )
                    )

                    if (state.timeError != null) {
                        Text(
                            text = state.timeError,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }
                }
                4 -> {


                }
            }









            Spacer(modifier = Modifier.height(50.dp))

            Button(
                onClick = {
                    if (state.activeStep.getIntValue() == 4){
                        viewModel.onEvent(BookingCreateEditEvent.Submit)
                    }else{
                        viewModel.onEvent(BookingCreateEditEvent.OnNextStep)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            {

                val text:String = if (state.activeStep.getIntValue() == 4){
                    stringResource(id = R.string.booking_create)
                }else{
                    stringResource(R.string.next_step)
                }

                Text(
                    text =  text,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

        }

    }

}*/



