package com.example.salonapp.presentation.employee.profile.hours_card

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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HoursCard(
    onEditHours: () -> Unit,
    viewModel: HoursCardViewModel = hiltViewModel(),
){

    val state = viewModel.state.value

    DisposableEffect(key1 = viewModel) {
        viewModel.onEvent(HoursCardEvent.OnInitialize)
        onDispose {  }
    }

    Box(modifier = Modifier
        .fillMaxWidth()){

        Card(
            Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
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
                    IconButton(onClick = { viewModel.onEvent(HoursCardEvent.OnInitialize) }) {
                        Icon(Icons.Filled.Refresh, contentDescription = stringResource(id = R.string.try_again))
                    }
                }
            }else{
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding(10.dp).fillMaxWidth(), verticalAlignment = Alignment.Top) {

                    Column(verticalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Monday: ")
                        Text(text = "Tuesday: ")
                        Text(text = "Wednesday")
                        Text(text = "Thursday: ")
                        Text(text = "Friday: ")
                        Text(text = "Saturday: ")
                        Text(text = "Sunday: ")
                    }

                    Column(verticalArrangement = Arrangement.SpaceBetween) {
                        val openingHours = state.openingHours!!
                        val mondayText = if(openingHours.mondayOpen) "${openingHours.mondayStart} - ${openingHours.mondayEnd}" else "Closed"
                        val tuesdayText = if(openingHours.tuesdayOpen) "${openingHours.tuesdayStart} - ${openingHours.tuesdayEnd}" else "Closed"
                        val wednessdayText = if(openingHours.wednessdayOpen) "${openingHours.wednessdayStart} - ${openingHours.wednessdayEnd}" else "Closed"
                        val thursdayText = if(openingHours.thursdayOpen) "${openingHours.thursdayStart} - ${openingHours.thursdayEnd}" else "Closed"
                        val fridayText = if(openingHours.fridayOpen) "${openingHours.fridayStart} - ${openingHours.fridayEnd}" else "Closed"
                        val saturdayText = if(openingHours.saturdayOpen) "${openingHours.saturdayStart} - ${openingHours.saturdayEnd}" else "Closed"
                        val sundayText = if(openingHours.sundayOpen) "${openingHours.sundayStart} - ${openingHours.sundayEnd}" else "Closed"

                        Text(text = mondayText)
                        Text(text = tuesdayText)
                        Text(text = wednessdayText)
                        Text(text = thursdayText)
                        Text(text = fridayText)
                        Text(text = saturdayText)
                        Text(text = sundayText)
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        IconButton(onClick = { onEditHours() }) {
                            Icon(Icons.Filled.Edit, contentDescription = stringResource(R.string.edit))
                        }

                    }

                }
            }



        }


    }

}