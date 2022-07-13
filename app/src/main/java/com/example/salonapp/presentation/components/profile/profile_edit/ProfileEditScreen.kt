package com.example.salonapp.presentation.components.profile.profile_edit


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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@Composable
fun ProfileEditScreen(
    viewModel: ProfileEditViewModel = hiltViewModel(),
    onUpdateSuccess: () -> Unit,
    onDeleteSuccess: () -> Unit
){
    val state = viewModel.state.value
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = LocalContext.current) {
        viewModel.events.collect { event ->
            when (event) {
                is ProfileEditEvent.OnUpdateSuccess-> {
                    onUpdateSuccess()
                }
                is ProfileEditEvent.OnDeleteSuccess-> {
                    onDeleteSuccess()
                }
            }
        }
    }

    DisposableEffect(key1 = viewModel) {
        viewModel.onEvent(ProfileEditEvent.OnInitialize)
        onDispose {  }
    }


    Column(Modifier.fillMaxSize().padding(horizontal = 20.dp, vertical = 50.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        screenLogoAndTitle(currentScreenTitle = stringResource(R.string.user_update))

        Spacer(modifier = Modifier.height(20.dp))

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

                    if (state.user != null && state.user.role == Constants.ROLE_OWNER){
                        Text(text = stringResource(R.string.try_again_error_message) + stringResource(
                                                    R.string.make_sure_no_employees_working_for_you)
                                                )
                    }else{
                        Text(text = stringResource(R.string.try_again_error_message))
                    }



                    Spacer(modifier = Modifier.height(10.dp))

                    IconButton(onClick = { viewModel.onEvent(ProfileEditEvent.OnInitialize) }) {
                        Icon(Icons.Filled.Refresh, contentDescription = stringResource(id = R.string.try_again))
                    }

                }


            }else{

                Column(
                    Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {

                    OutlinedTextField(
                        value = state.firstName,
                        onValueChange = {
                            viewModel.onEvent(ProfileEditEvent.FirstnameChanged(it))
                        },
                        isError = state.firstNameError != null,
                        modifier = Modifier
                            .fillMaxWidth()
                        ,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        ),
                        singleLine = true,
                        label = { Text(text = stringResource(id = R.string.form_firstname))}
                    )

                    if (state.firstNameError != null) {
                        Text(
                            text = state.firstNameError,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = state.lastName,
                        onValueChange = {
                            viewModel.onEvent(ProfileEditEvent.LastnameChanged(it))
                        },
                        isError = state.lastNameError != null,
                        modifier = Modifier
                            .fillMaxWidth()
                        ,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        ),
                        singleLine = true,
                        label = { Text(text = stringResource(id = R.string.form_lastname))}
                    )

                    if (state.lastNameError != null) {
                        Text(
                            text = state.lastNameError,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = state.phone,
                        onValueChange = {
                            viewModel.onEvent(ProfileEditEvent.PhoneChanged(it))
                        },
                        isError = state.phoneError != null,
                        modifier = Modifier
                            .fillMaxWidth()
                        ,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        ),
                        singleLine = true,
                        label = { Text(text = stringResource(id = R.string.form_phone))}
                    )

                    if (state.phoneError != null) {
                        Text(
                            text = state.phoneError,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { viewModel.onEvent(ProfileEditEvent.OnSubmit) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    )
                    {
                        Text(
                            text = stringResource(id = R.string.user_update),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    TextButton(onClick = { viewModel.onEvent(ProfileEditEvent.OnShowAlert) }) {
                        Text(
                            text = stringResource(R.string.user_delete),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                    }


                    if(state.showAlert){
                        AlertDialog(
                            title = {
                                Text(text = stringResource(R.string.user_delete))
                            },
                            text = {
                                Text(text = stringResource(R.string.sure_delete_user_message))
                            },
                            confirmButton = {
                                TextButton(onClick = { viewModel.onEvent(ProfileEditEvent.OnDeleteUser) }) {
                                    Text(text = stringResource(R.string.confirm))
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { viewModel.onEvent(ProfileEditEvent.OnDismissAlert) }) {
                                    Text(text = stringResource(R.string.dismiss))
                                }
                            },
                            onDismissRequest = {
                                viewModel.onEvent(ProfileEditEvent.OnDismissAlert)
                            }
                        )
                    }




                }

            }


        }

    }











}



