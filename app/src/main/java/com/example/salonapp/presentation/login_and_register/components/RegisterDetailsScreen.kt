package com.example.salonapp.presentation.login_and_register.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.salonapp.R

@Composable
fun RegisterDetailsScreen(

){
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 50.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.1f),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            tint = Color.Unspecified
        )



        Text(
            text = stringResource(id = R.string.register),
            color = colorResource(id = R.color.black),
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier
            .fillMaxHeight(.3f)
            .fillMaxWidth())

        Text(
            text = stringResource(id = R.string.register_role_question),
            color = colorResource(id = R.color.black),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )



        ActionButton(
            backgroundColor = colorResource(id = R.color.blue_primary),
            textColor = colorResource(id = R.color.white),
            text = stringResource(id = R.string.role_customer)
        )

        ActionButton(
            backgroundColor = colorResource(id = R.color.yellow_secondary),
            textColor = colorResource(id = R.color.black),
            text = stringResource(id = R.string.role_employee)
        )

        ActionButton(
            backgroundColor = colorResource(id = R.color.yellow_primary),
            textColor = colorResource(id = R.color.black),
            text = stringResource(id = R.string.role_owner)
        )
    }

}