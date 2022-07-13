package com.example.salonapp.presentation.customer


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ListItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.salonapp.R
import com.example.salonapp.common.Utils
import com.example.salonapp.presentation.components.booking.BookingCreateEditEvent
import com.example.salonapp.presentation.components.profile.profile_card.ProfileCard
import com.example.salonapp.presentation.employee.schedule.EmployeeScheduleEvent
import com.example.salonapp.presentation.employee.schedule.EmployeeScheduleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenCustomer(
    navController: NavHostController = rememberNavController(),
    onLogOut: () -> Unit,
    onProfileEdit: () -> Unit,
    onGoToBooking: (bookingId:Int?) -> Unit,
    viewModel: MainCustomerScreenViewModel = hiltViewModel(),
) {

    val state = viewModel.state.value

    DisposableEffect(key1 = viewModel) {
        viewModel.onEvent(MainCustomerScreenEvent.OnInitialize)
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
                IconButton(onClick = { viewModel.onEvent(MainCustomerScreenEvent.OnInitialize) }) {
                    Icon(Icons.Filled.Refresh, contentDescription = stringResource(id = R.string.try_again), modifier = Modifier.size(50.dp))
                }
            }
        }
        else {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(20.dp, 20.dp, 20.dp, 0.dp)
                    .align(Alignment.Center)
            ) {

                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                    TextButton(onClick = { onLogOut() }) {
                        Text(text = stringResource(id = R.string.logout))
                    }
                }
                
                Spacer(modifier = Modifier.height(10.dp))

                FilledTonalButton(
                    onClick = {
                        onGoToBooking(null)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.booking_create), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }

                Spacer(modifier = Modifier.height(20.dp))

                ProfileCard(onEditProfile = { onProfileEdit() })

                Spacer(modifier = Modifier.height(20.dp))

                Text(text = "My bookings", fontWeight = FontWeight.Bold, fontSize = 20.sp)

                Spacer(modifier = Modifier.height(5.dp))

                Divider()

                Spacer(modifier = Modifier.height(5.dp))

                LazyColumn(Modifier.fillMaxWidth()){
                    items(state.myBookings.sortedBy { it.startTime }){booking ->
                        Card(Modifier.fillMaxWidth()){

                            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(), verticalAlignment = Alignment.Top) {

                                Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxHeight()) {

                                    Row {
                                        Column {
                                            Text("Service: ")
                                            Text("Salon: ")
                                            Text("Employee: ")
                                            Text("Date: ")
                                            Text("Time: ")
                                        }
                                        Column {
                                            Text(text = booking.service.name)
                                            Text(text = booking.service.salon.name)
                                            Text(text = Utils.formatFullName(booking.employee.firstName, booking.employee.lastName))
                                            Text(text = booking.startTime.toLocalDate().toString())
                                            Text(text = booking.startTime.toLocalTime().toString())
                                        }
                                    }

                                }

                                Spacer(modifier = Modifier.width(10.dp))

                                Column(
                                    horizontalAlignment = Alignment.End,
                                    verticalArrangement = Arrangement.Top,
                                    modifier = Modifier.fillMaxHeight()) {
                                    IconButton(onClick = { onGoToBooking(booking.id) }) {
                                        Icon(Icons.Filled.Edit, contentDescription = stringResource(R.string.edit))
                                    }
                                }

                            }

                        }

                        Spacer(modifier = Modifier.height(10.dp))

                    }

                }

            }

        }

    }


}



