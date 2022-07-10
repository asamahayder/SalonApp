package com.example.salonapp.presentation.components.profile.salon_card

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
import com.example.salonapp.domain.models.Salon
import com.example.salonapp.presentation.components.profile.profile_card.SalonCardViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalonCard(
    salon: Salon,
    isOwner:Boolean,
    onEditSalon: () -> Unit
){

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)){

        Card(
            Modifier
                .align(Alignment.Center)
                .fillMaxSize()
                ) {



            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding(10.dp).fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {

                Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxHeight()) {
                    Text(text = salon.name)
                    Text(text = salon.email)
                    Text(text = salon.phone)
                    Text(text = salon.postCode + " " + salon.city)
                    Text(text = salon.streetName + " " + salon.streetNumber)
                }

                Column(horizontalAlignment = Alignment.End, modifier = Modifier.fillMaxHeight()) {

                    if (isOwner){
                        IconButton(onClick = { onEditSalon() }) {
                            Icon(Icons.Filled.Edit, contentDescription = stringResource(R.string.edit))
                        }
                    }

                }

            }




        }


    }

}