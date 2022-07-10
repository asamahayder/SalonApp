package com.example.salonapp.presentation.employee.request


import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import com.example.salonapp.presentation.components.Schedule.Schedule
import com.example.salonapp.presentation.components.screenLogoAndTitle
import com.example.salonapp.presentation.employee.schedule.EmployeeScheduleEvent
import com.example.salonapp.presentation.owner.services.ServicesEvent

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RequestScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: RequestScreenViewModel = hiltViewModel(),
    ) {

    val state = viewModel.state.value

    val context = LocalContext.current
    LaunchedEffect(key1 = context) {
        viewModel.events.collect { event ->
            when (event) {
                is RequestScreenEvent.OnRequestDeleted-> {
                    Toast.makeText(context, "Request Deleted", Toast.LENGTH_SHORT).show()
                }
                is RequestScreenEvent.OnRequestCreated-> {
                    Toast.makeText(context, "Request Created", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    DisposableEffect(key1 = viewModel) {
        viewModel.onEvent(RequestScreenEvent.OnInitialize)
        onDispose {  }
    }


    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp, 20.dp, 20.dp, 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        screenLogoAndTitle(currentScreenTitle = stringResource(id = R.string.requests))

        Spacer(modifier = Modifier.height(75.dp))

        Box(modifier = Modifier.fillMaxSize()){
            if (state.isLoading){
                CircularProgressIndicator(
                    Modifier
                        .height(100.dp)
                        .width(100.dp)
                        .align(Alignment.Center))

            }
            else if (state.error != null){
                Column(Modifier.align(Alignment.Center)) {
                    Text(text = stringResource(R.string.error_text),
                        textAlign = TextAlign.Center)

                    IconButton(onClick = { viewModel.onEvent(RequestScreenEvent.OnInitialize) }) {
                        Icon(Icons.Filled.Refresh, contentDescription = stringResource(id = R.string.try_again))
                    }
                }
            }



            else if (state.fetchedSalons && state.salons.isNotEmpty() && state.pendingRequest == null){

                Column(Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Select which salon to send a request to.", textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(10.dp))
                    Divider()
                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(state.salons){salon ->
                            ListItem(
                                modifier = Modifier.clickable {
                                    viewModel.onEvent(RequestScreenEvent.OnShowCreateRequestDialog(salon))
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

                            if(state.showCreateRequestDialog && state.selectedSalon != null){
                                AlertDialog(
                                    title = {
                                        Text(text = stringResource(R.string.request_create))
                                    },
                                    text = {
                                        Text(text = stringResource(R.string.sure_want_to_create_request) + " " +  state.selectedSalon.name  + "?")
                                    },
                                    confirmButton = {
                                        TextButton(onClick = { viewModel.onEvent(RequestScreenEvent.OnCreateRequest(state.selectedSalon.id)) }) {
                                            Text(text = stringResource(R.string.confirm))
                                        }
                                    },
                                    dismissButton = {
                                        TextButton(onClick = { viewModel.onEvent(RequestScreenEvent.OnDismissCreateRequestDialog) }) {
                                            Text(text = stringResource(R.string.dismiss))
                                        }

                                    },
                                    onDismissRequest = {
                                        viewModel.onEvent(RequestScreenEvent.OnDismissCreateRequestDialog)
                                    }
                                )
                            }
                        }
                    }
                }



            }
            else if (state.pendingRequest != null){

                Column(
                    Modifier
                        .align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "You have a pending request for the following salon: ", textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(100.dp))
                    Text(text = state.pendingRequest.salon.name,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp)

                    Spacer(modifier = Modifier.height(100.dp))

                    Button(
                        onClick = {
                            viewModel.onEvent(RequestScreenEvent.OnShowDeleteRequestDialog)
                        }
                    ) {
                        Text(text = stringResource(R.string.request_delete), textAlign = TextAlign.Center)
                    }

                    if(state.showDeleteRequestDialog){
                        AlertDialog(
                            title = {
                                Text(text = stringResource(R.string.request_delete))
                            },
                            text = {
                                Text(text = stringResource(R.string.sure_want_to_delete_request))
                            },
                            confirmButton = {
                                TextButton(onClick = { viewModel.onEvent(RequestScreenEvent.OnDeleteRequest) }) {
                                    Text(text = stringResource(R.string.confirm))
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { viewModel.onEvent(RequestScreenEvent.OnDismissDeleteRequestDialog) }) {
                                    Text(text = stringResource(R.string.dismiss))
                                }

                            },
                            onDismissRequest = {
                                viewModel.onEvent(RequestScreenEvent.OnDismissDeleteRequestDialog)
                            }
                        )
                    }

                }
            }
        }

    }

}



