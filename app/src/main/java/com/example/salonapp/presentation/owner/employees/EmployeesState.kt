package com.example.salonapp.presentation.owner.employees

import androidx.compose.ui.geometry.Size
import com.example.salonapp.domain.models.Request
import com.example.salonapp.domain.models.Salon
import com.example.salonapp.domain.models.User

data class EmployeesState (

    val salons: List<Salon> = listOf(),
    val employees: List<User> = listOf(),
    val requests: List<Request> = listOf(),
    val isLoading: Boolean = false,
    val error: String = "",
    val activeSalon: Salon? = null,
    val salonSelectionExpanded: Boolean = false,
    val salonSelectionWidth: Size = Size.Zero,
    val showDeleteAlert: Boolean = false,
    val showRequestDialog: Boolean = false
)