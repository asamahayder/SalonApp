package com.example.salonapp.presentation.employee.schedule


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.salonapp.R
import com.example.salonapp.presentation.components.Schedule.Schedule
import com.example.salonapp.presentation.employee.request.RequestScreenEvent

@Composable
fun EmployeeScheduleScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: EmployeeScheduleViewModel = hiltViewModel(),
    onGoToRequestCreation: () -> Unit,
    onGoToProfile: () -> Unit,
    onCreateBooking: () -> Unit,
    ) {

    val state = viewModel.state.value

    LaunchedEffect(key1 = LocalContext.current) {
        viewModel.events.collect { event ->
            when (event) {
                is EmployeeScheduleEvent.OnGoToRequestCreation-> {
                    onGoToRequestCreation()
                }
            }
        }
    }

    DisposableEffect(key1 = viewModel) {
        viewModel.onEvent(EmployeeScheduleEvent.OnInitialize)
        onDispose {  }
    }


    Box(modifier = Modifier.fillMaxSize()){

        if (state.isLoading){
            CircularProgressIndicator(
                Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .align(Alignment.Center))

        }
        else if (state.error != null){
            Column(Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = stringResource(R.string.error_text),
                    textAlign = TextAlign.Center)
                IconButton(onClick = { viewModel.onEvent(EmployeeScheduleEvent.OnInitialize) }) {
                    Icon(Icons.Filled.Refresh, contentDescription = stringResource(id = R.string.try_again), modifier = Modifier.size(50.dp))
                }
            }

        }
        else if (state.fetchedSalon && state.salon == null){
            Column(
                Modifier
                    .align(Alignment.Center)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(R.string.not_assigned_to_salon), textAlign = TextAlign.Center)

                Spacer(modifier = Modifier.height(50.dp))

                Button(onClick = { onGoToRequestCreation() }) {
                    Text(text = stringResource(R.string.request_page), textAlign = TextAlign.Center)
                }
            }

        }
        else if (state.salon != null){
            Column(
                Modifier
                    .align(Alignment.Center)
                    .fillMaxSize()) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = state.salon.name, fontWeight = FontWeight.Bold, fontSize = 35.sp)

                    IconButton(onClick = {onGoToProfile()}) {
                        Icon(Icons.Filled.Person, contentDescription = stringResource(id = R.string.bottom_nav_profile), modifier = Modifier.size(40.dp))
                    }

                }

                Divider()

                Schedule(bookings = state.bookings, onCreateBooking = { onCreateBooking }, onWeekChanged = {})

            }
        }


    }





}



