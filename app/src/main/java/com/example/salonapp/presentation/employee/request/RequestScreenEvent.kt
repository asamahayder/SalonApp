package com.example.salonapp.presentation.employee.request

import com.example.salonapp.domain.models.Salon
import java.time.LocalDateTime

sealed class RequestScreenEvent {

    object OnRequestCreated: RequestScreenEvent()

    object OnRequestDeleted: RequestScreenEvent()

    data class OnCreateRequest(val salonId:Int): RequestScreenEvent()

    object OnDeleteRequest: RequestScreenEvent()

    object  OnDismissCreateRequestDialog: RequestScreenEvent()

    object OnDismissDeleteRequestDialog: RequestScreenEvent()

    object OnShowDeleteRequestDialog: RequestScreenEvent()

    data class OnShowCreateRequestDialog(val salon:Salon): RequestScreenEvent()

    object OnInitialize: RequestScreenEvent()

}