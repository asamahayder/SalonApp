package com.example.salonapp.domain.use_cases.validations

import com.example.salonapp.domain.models.ValidationResult
import javax.inject.Inject

class ValidateFirstNameUseCase @Inject constructor(

) {
    fun execute(firstName: String): ValidationResult {
        if(firstName.isBlank()) {
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