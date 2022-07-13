package com.example.salonapp.presentation.employee.hours

import com.example.salonapp.domain.models.OpeningHours
import com.example.salonapp.domain.models.User
import java.time.LocalTime


data class HoursEditState (
    val openingHours: OpeningHours? = null,

    val isLoading: Boolean = false,
    val error: String? = null,
    val userId: Int? = null,
    val validationError:Boolean = false

    )