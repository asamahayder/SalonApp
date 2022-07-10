package com.example.salonapp.presentation.owner.employees

import androidx.compose.ui.geometry.Size
import com.example.salonapp.domain.models.Salon
import com.example.salonapp.presentation.owner.profile.ProfileEvent

sealed class EmployeesEvent {
    data class OnRemoveEmployeeFromSalon(val employeeId:Int): EmployeesEvent()
    data class OnAcceptRequest(val requestId:Int): EmployeesEvent()
    data class OnDenyRequest(val requestId:Int): EmployeesEvent()
    object OnShowRequestDialog: EmployeesEvent()
    object OnDismissRequestDialog: EmployeesEvent()

    object OnShowAlert: EmployeesEvent()
    object OnDismissAlert: EmployeesEvent()

    object OnInitialize: EmployeesEvent()
    object OnReload: EmployeesEvent()
    data class OnError(val message: String): EmployeesEvent()

    object OnToggleSalonMenu: EmployeesEvent()
    object OnSalonSelectDismiss: EmployeesEvent()
    data class OnSetSalonSelectionWidth(val width: Size): EmployeesEvent()
    data class OnSetActiveSalon(val salon: Salon): EmployeesEvent()

}