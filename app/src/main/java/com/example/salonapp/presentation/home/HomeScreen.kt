package com.example.salonapp.presentation.home


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.salonapp.R
import com.example.salonapp.presentation.Screen
import com.example.salonapp.presentation.salon_create.components.SalonRow


@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
){
    val state = viewModel.state.value

    LaunchedEffect(key1 = LocalContext.current) {
        viewModel.events.collect { event ->
            when (event) {
                is HomeEvent.GoToCreateSalon-> {

                }
            }
        }

        viewModel.onEvent(HomeEvent.FetchSalons)
    }

    Column(
        Modifier
            .padding(horizontal = 20.dp, vertical = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    )
    {

        if (state.isLoading){
            Spacer(modifier = Modifier.height(100.dp))
            CircularProgressIndicator(
                Modifier
                    .height(100.dp)
                    .width(100.dp))
        }else if(state.fetchedSalons && state.salons.isNullOrEmpty()){
            androidx.compose.material3.Button(
                onClick = {navController.navigate(Screen.CreateSalonScreen.route)}
            ){
                Text(text = "Create First Salon")
            }
        }
        else if(!state.error.isNullOrEmpty()){
            Text(text = "Something went wrong")
            Text(text = state.error)
        }
        else{
            Text(
                text = stringResource(R.string.my_salons),
                color = colorResource(id = R.color.black),
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
            )
            
            Text(text = state.fetchedSalons.toString())

            Text(text = state.salons.count().toString())

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn{
                items(state.salons) { salon -> SalonRow(salon = salon)}
            }
        }


    }
}

