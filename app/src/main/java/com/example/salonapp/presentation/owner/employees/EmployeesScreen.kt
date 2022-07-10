package com.example.salonapp.presentation.owner.employees


import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.salonapp.R
import com.example.salonapp.domain.models.RequestStatus
import com.example.salonapp.presentation.owner.schedule.noRippleClickable
import com.example.salonapp.presentation.owner.services.ServicesEvent
import com.example.salonapp.presentation.owner.services.ServicesViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EmployeesScreen(
    viewModel: EmployeesViewModel = hiltViewModel(),
    onCreateSalon: () -> Unit,
){
    val state = viewModel.state.value

    val context = LocalContext.current

    LaunchedEffect(key1 = LocalContext.current) {
        viewModel.events.collect { event ->
            when (event) {
                is EmployeesEvent.OnError-> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT)
                }
            }
        }
    }

    DisposableEffect(key1 = viewModel) {
        viewModel.onEvent(EmployeesEvent.OnInitialize)
        onDispose {  }
    }


    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()) {


        if (!state.isLoading){
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
                                Icon(
                                    Icons.Filled.ArrowDropDown, contentDescription = stringResource(
                                        R.string.dropdown_expand)
                                )
                            }
                        },
                        singleLine = true,
                        readOnly = true,
                        enabled = false,
                        modifier = Modifier
                            .noRippleClickable { viewModel.onEvent(EmployeesEvent.OnToggleSalonMenu) }
                            .onGloballyPositioned { coordinates ->
                                viewModel.onEvent(
                                    EmployeesEvent.OnSetSalonSelectionWidth(
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
                        expanded = state.salonSelectionExpanded,
                        onDismissRequest = {
                            viewModel.onEvent(EmployeesEvent.OnSalonSelectDismiss)
                        },
                        modifier = Modifier.width(with(LocalDensity.current){state.salonSelectionWidth.width.toDp()})
                    ) {
                        state.salons.forEach{salon ->
                            DropdownMenuItem(
                                text = {Text(text = salon.name)},
                                onClick = {
                                    viewModel.onEvent(EmployeesEvent.OnSetActiveSalon(salon))
                                }
                            )
                        }
                        MenuDefaults.Divider()
                        DropdownMenuItem(
                            text = { Text(text = "Create New Salon") },
                            onClick = {
                                onCreateSalon()
                            }
                        )

                    }
                }

                Spacer(modifier = Modifier.weight(.55f))
            }
        }

        Divider()

        if (state.isLoading){

            Box(modifier = Modifier.fillMaxSize()){
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.Center)
                )
            }

        }else{

            Column(Modifier.padding(20.dp, 20.dp, 20.dp, 0.dp)) {
                Text(text = stringResource(id = R.string.bottom_nav_employees), fontSize = 20.sp)
                if (!state.employees.isNullOrEmpty()){
                    LazyColumn (
                        modifier = Modifier
                            .fillMaxWidth()
                    ){
                        items(state.employees){employee ->

                            ListItem(
                                text = {
                                    Text(
                                        text = "${employee.firstName} ${employee.lastName}",
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                },
                                secondaryText = {
                                    Text(
                                        text = "${employee.phone} - ${employee.email}",
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        fontStyle = FontStyle.Italic,
                                        fontWeight = FontWeight.Light
                                    )
                                },
                                trailing = {
                                    IconButton(onClick = {
                                        viewModel.onEvent(EmployeesEvent.OnShowAlert)
                                    }) {
                                        Icon(Icons.Filled.Delete, stringResource(R.string.employee_remove_from_salon))
                                    }
                                }
                            )

                            if(state.showDeleteAlert){
                                AlertDialog(
                                    title = {
                                        Text(text = stringResource(R.string.employee_remove_from_salon))
                                    },
                                    text = {
                                        Text(text = stringResource(R.string.employee_remove_confirm_text))
                                    },
                                    confirmButton = {
                                        TextButton(onClick = { viewModel.onEvent(EmployeesEvent.OnRemoveEmployeeFromSalon(employeeId = employee.id)) }) {
                                            Text(text = stringResource(R.string.confirm))
                                        }
                                    },
                                    dismissButton = {
                                        TextButton(onClick = { viewModel.onEvent(EmployeesEvent.OnDismissAlert) }) {
                                            Text(text = stringResource(R.string.dismiss))
                                        }

                                    },
                                    onDismissRequest = {
                                        viewModel.onEvent(EmployeesEvent.OnDismissAlert)
                                    }
                                )
                            }
                        }
                    }
                }else{
                    Column(
                        modifier = Modifier
                            .padding(50.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = R.string.no_employees_to_show_message),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(30.dp))

                        IconButton(onClick = {
                            viewModel.onEvent(EmployeesEvent.OnReload)
                        }) {
                            Icon(
                                Icons.Filled.Refresh,
                                stringResource(R.string.try_again),
                                Modifier.size(50.dp)
                            )
                        }
                    }
                }


                Text(text = stringResource(R.string.requests), fontSize = 20.sp)
                if (!state.requests.isNullOrEmpty()){
                    LazyColumn (
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                    ){
                        items(state.requests){ request ->

                            ListItem(
                                modifier = Modifier.clickable {
                                    viewModel.onEvent(EmployeesEvent.OnShowRequestDialog)
                                },
                                text = {
                                    Text(
                                        text = "${request.employee.firstName} ${request.employee.lastName}",
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                },
                                secondaryText = {
                                    Text(
                                        text = "${request.employee.phone} - ${request.employee.email}",
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        fontStyle = FontStyle.Italic,
                                        fontWeight = FontWeight.Light
                                    )
                                }
                            )

                            if(state.showRequestDialog){
                                AlertDialog(
                                    title = {
                                        Text(text = stringResource(R.string.request_choice))
                                    },
                                    text = {
                                        Text(text = stringResource(R.string.request_choice_text))
                                    },
                                    confirmButton = {
                                        TextButton(onClick = { viewModel.onEvent(EmployeesEvent.OnAcceptRequest(requestId = request.id)) }) {
                                            Text(text = stringResource(R.string.accept))
                                        }
                                    },
                                    dismissButton = {
                                        TextButton(onClick = { viewModel.onEvent(EmployeesEvent.OnDenyRequest(requestId = request.id)) }) {
                                            Text(text = stringResource(R.string.deny))
                                        }

                                    },
                                    onDismissRequest = {
                                        viewModel.onEvent(EmployeesEvent.OnDismissRequestDialog)
                                    }
                                )
                            }
                        }
                    }
                }else{

                    Box(modifier = Modifier.fillMaxSize()){
                        Column(
                            modifier = Modifier
                                .padding(50.dp)
                                .align(Alignment.Center),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,

                            ) {
                            Text(
                                text = stringResource(R.string.no_requests),
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(30.dp))

                            IconButton(onClick = {
                                viewModel.onEvent(EmployeesEvent.OnReload)
                            }) {
                                Icon(
                                    Icons.Filled.Refresh,
                                    stringResource(R.string.try_again),
                                    Modifier.size(50.dp)
                                )
                            }
                        }
                    }
                }
            }





        }

    }
}













