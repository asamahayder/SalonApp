package com.example.salonapp.presentation.salon_create.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.salonapp.domain.models.Salon

@Composable
fun SalonRow(salon: Salon){
    Row {
        Text(text = salon.name)
    }
}