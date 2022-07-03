package com.example.salonapp.domain.use_cases.validations

import com.example.salonapp.domain.models.ValidationResult
import javax.inject.Inject

class ValidateLastNameUseCase @Inject constructor(

) {
    fun execute(lastName: String): ValidationResult {
        if(lastName.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Cannot be blank"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}