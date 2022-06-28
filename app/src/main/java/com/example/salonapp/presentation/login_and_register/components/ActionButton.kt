package com.example.salonapp.presentation.login_and_register.components

import android.service.autofill.OnClickAction
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ActionButton(
    backgroundColor: Color,
    textColor: Color,
    text: String,
    onClick: () -> Unit
){
    Box(
        Modifier
            .background(color = backgroundColor, shape = RoundedCornerShape(50.dp))
            .fillMaxWidth()
            .padding(10.dp)
            .clickable(onClick = onClick),
        Alignment.Center
        ){

        Text(
            fontSize = 20.sp,
            text = text,
            color = textColor,
            fontWeight = FontWeight.Bold
        )


    }
}