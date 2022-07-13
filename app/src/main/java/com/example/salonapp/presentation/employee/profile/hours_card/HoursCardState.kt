package com.example.salonapp.presentation.employee.profile.hours_card

import androidx.compose.ui.geometry.Size
import com.example.salonapp.domain.models.OpeningHours
import com.example.salonapp.domain.models.Salon
import com.example.salonapp.domain.models.Service
import com.example.salonapp.domain.models.User
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class HoursCardState (


    val openingHours: OpeningHours? = null,
    val error:String? = null,
    val isLoading:Boolean = true,


)

