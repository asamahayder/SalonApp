package com.example.salonapp.presentation.components.profile.profile_card

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.salonapp.R
import com.example.salonapp.common.Utils
import com.example.salonapp.presentation.components.profile.salon_card.SalonCardEvent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCard(
    onEditProfile: () -> Unit,
    viewModel: ProfileCardViewModel = hiltViewModel(),
){

    val state = viewModel.state.value

    DisposableEffect(key1 = viewModel) {
        viewModel.onEvent(ProfileCardEvent.OnInitialize)
        onDispose {  }
    }

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)){

        Card(
            Modifier
                .align(Alignment.Center)
                .fillMaxSize()
                ) {


            if (state.isLoading){
                Box{
                    CircularProgressIndicator(
                        Modifier
                            .height(100.dp)
                            .width(100.dp)
                            .align(Alignment.Center))
                }
            }
            else if (state.error != null){

                Column {
                    Text(text = "An error happened, please refresh", textAlign = TextAlign.Center, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(10.dp))
                    IconButton(onClick = { viewModel.onEvent(ProfileCardEvent.OnInitialize) }) {
                        Icon(Icons.Filled.Refresh, contentDescription = stringResource(id = R.string.try_again))
                    }
                }
            }else{
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding(10.dp).fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {

                    Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxHeight()) {
                        Text(text = Utils.formatFullName(state.user!!.firstName, state.user.lastName))
                        Text(text = state.user.email)
                        Text(text = state.user.phone)
                        Text(text = state.user.role)
                    }

                    Column(horizontalAlignment = Alignment.End, modifier = Modifier.fillMaxHeight()) {
                        IconButton(onClick = { onEditProfile() }) {
                            Icon(Icons.Filled.Edit, contentDescription = stringResource(R.string.edit))
                        }

                    }

                }
            }



        }


    }

}