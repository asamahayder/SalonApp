package com.example.salonapp.presentation.owner.profile


import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
){
    val state = viewModel.state.value

    Text(text = state.sampleText)


}



