package com.example.salonapp.presentation.owner.employees


import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun EmployeesScreen(
    viewModel: EmployeesViewModel = hiltViewModel()
){
    val state = viewModel.state.value

    Text(text = state.sampleText)


}



