package com.example.salonapp.domain.models

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)