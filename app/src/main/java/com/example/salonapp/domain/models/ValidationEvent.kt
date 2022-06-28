package com.example.salonapp.domain.models

sealed class ValidationEvent {
    object Success: ValidationEvent()
}