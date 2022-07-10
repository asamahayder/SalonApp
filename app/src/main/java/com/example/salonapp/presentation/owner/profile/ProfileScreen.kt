package com.example.salonapp.presentation.owner.profile


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ListItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.salonapp.R
import com.example.salonapp.common.Constants
import com.example.salonapp.domain.models.User
import com.example.salonapp.presentation.components.profile.profile_card.ProfileCard
import com.example.salonapp.presentation.components.profile.profile_card.ProfileCardEvent
import com.example.salonapp.presentation.components.profile.salon_card.SalonCard
import com.example.salonapp.presentation.owner.services.ServicesEvent

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onEditSalon: (salonId:Int) -> Unit
){

    val state = viewModel.state.value

    DisposableEffect(key1 = viewModel) {
        viewModel.onEvent(ProfileEvent.OnInitialize)
        onDispose {  }
    }


    Box(modifier = Modifier.fillMaxSize()){

        if (state.isLoading){
            CircularProgressIndicator(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.Center)
            )
        }

        else if (state.error != null){
            Column(Modifier.align(Alignment.Center)) {
                Text(text = "An error happened, please refresh", textAlign = TextAlign.Center, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(10.dp))
                IconButton(onClick = { viewModel.onEvent(ProfileEvent.OnInitialize) }) {
                    Icon(Icons.Filled.Refresh, contentDescription = stringResource(id = R.string.try_again))
                }
            }
        }else{
            Column(
                Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .padding(20.dp,20.dp,20.dp,0.dp)) {

                Text(text = stringResource(R.string.user), fontWeight = FontWeight.Bold, fontSize = 30.sp)

                Divider()

                Spacer(Modifier.height(10.dp))

                ProfileCard(onEditProfile = {

                })

                Spacer(Modifier.height(10.dp))

                Text(text = stringResource(R.string.salons), fontWeight = FontWeight.Bold, fontSize = 30.sp)

                Divider()



                LazyColumn (
                    modifier = Modifier
                        .fillMaxWidth()

                ){


                    itemsIndexed(state.salons){index, salon ->
                        if (index == 0){
                            Spacer(Modifier.height(10.dp))
                        }

                        val isOwner = state.user!!.role == Constants.ROLE_OWNER

                        SalonCard(salon = salon, isOwner = isOwner, onEditSalon = { onEditSalon(salon.id)})
                        Spacer(Modifier.height(5.dp))
                    }
                }



            }


        }


    }



}



