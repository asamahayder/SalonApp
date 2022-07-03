package com.example.salonapp.presentation.owner.services


import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ServicesScreen(
    viewModel: ServicesViewModel = hiltViewModel()
){
    val state = viewModel.state.value

    Text(text = state.sampleText)


}



