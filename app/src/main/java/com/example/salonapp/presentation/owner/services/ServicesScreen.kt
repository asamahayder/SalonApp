package com.example.salonapp.presentation.owner.services



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
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.salonapp.R
import com.example.salonapp.presentation.owner.schedule.noRippleClickable

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ServicesScreen(
    viewModel: ServicesViewModel = hiltViewModel(),
    onCreateService: () -> Unit,
    onCreateSalon: () -> Unit,
    onEditService: (id:Int) -> Unit
){
    val state = viewModel.state.value

    val context = LocalContext.current

    LaunchedEffect(key1 = LocalContext.current) {
        viewModel.events.collect { event ->
            when (event) {
                is ServicesEvent.OnError-> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT)
                }
            }
        }
    }

    DisposableEffect(key1 = viewModel) {
        viewModel.onEvent(ServicesEvent.OnInitialize)
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
                                Icon(Icons.Filled.ArrowDropDown, contentDescription = stringResource(R.string.dropdown_expand))
                            }
                        },
                        singleLine = true,
                        readOnly = true,
                        enabled = false,
                        modifier = Modifier
                            .noRippleClickable { viewModel.onEvent(ServicesEvent.OnToggleSalonMenu) }
                            .onGloballyPositioned { coordinates ->
                                viewModel.onEvent(ServicesEvent.OnSetSalonSelectionWidth(coordinates.size.toSize()))
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
                            viewModel.onEvent(ServicesEvent.OnSalonSelectDismiss)
                        },
                        modifier = Modifier.width(with(LocalDensity.current){state.salonSelectionWidth.width.toDp()})
                    ) {
                        state.salons.forEach{salon ->
                            DropdownMenuItem(
                                text = {Text(text = salon.name)},
                                onClick = {
                                    viewModel.onEvent(ServicesEvent.OnSetActiveSalon(salon))
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

        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight())
        {


            if (state.isLoading){
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.Center)
                )
            }else{

                if (!state.services.isNullOrEmpty()){
                    LazyColumn (
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .align(Alignment.Center)
                    ){
                        items(state.services){service ->

                            ListItem(
                                modifier = Modifier.clickable {
                                    onEditService(service.id)
                                },
                                text = {
                                    Text(
                                        text = service.name,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                },
                                secondaryText = {
                                    val description = service.description ?: ""
                                    Text(
                                        text = service.price.toString() + "kr. - " + " " + description,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        fontStyle = FontStyle.Italic,
                                        fontWeight = FontWeight.Light
                                    )
                                },
                                singleLineSecondaryText = true,
                                trailing = {
                                    IconButton(onClick = {
                                        viewModel.onEvent(ServicesEvent.OnShowAlert)
                                    }) {
                                        Icon(Icons.Filled.Delete, stringResource(R.string.delete_service))
                                    }
                                }
                            )

                            if(state.showDeleteAlert){
                                AlertDialog(
                                    title = {
                                        Text(text = stringResource(R.string.confirm_service_delete_title))
                                    },
                                    text = {
                                        Text(text = stringResource(R.string.confirm_delete_service_text))
                                    },
                                    confirmButton = {
                                        TextButton(onClick = { viewModel.onEvent(ServicesEvent.OnDeleteService(serviceId = service.id)) }) {
                                            Text(text = stringResource(R.string.confirm))
                                        }

                                    },
                                    dismissButton = {
                                        TextButton(onClick = { viewModel.onEvent(ServicesEvent.OnDismissAlert) }) {
                                            Text(text = stringResource(R.string.dismiss))
                                        }

                                    },
                                    onDismissRequest = {
                                        viewModel.onEvent(ServicesEvent.OnDismissAlert)
                                    }
                                )
                            }
                        }
                    }
                }else{
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(50.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.no_services),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(30.dp))

                        IconButton(onClick = {
                            viewModel.onEvent(ServicesEvent.OnReload)
                        }) {
                            Icon(
                                Icons.Filled.Refresh,
                                stringResource(R.string.try_again),
                                Modifier.size(50.dp)
                            )
                        }
                    }
                }


                if(state.activeSalon != null){
                    ExtendedFloatingActionButton(
                        onClick = {
                            viewModel.onEvent(ServicesEvent.OnCreateService)
                            onCreateService()
                                  },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(12.dp)
                    ) {
                        Icon(Icons.Filled.Create, stringResource(R.string.create_service))
                        androidx.compose.material.Text(text = stringResource(R.string.create_service))
                    }
                }


            }






        }

    }





    



}



