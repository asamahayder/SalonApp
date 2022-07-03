package com.example.salonapp.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.salonapp.R

@Composable
fun screenLogoAndTitle(currentScreenTitle: String){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            modifier = Modifier
                .size(70.dp),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            tint = Color.Unspecified
        )

        Text(
            text = currentScreenTitle,
            color = colorResource(id = R.color.black),
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
        )
    }

}