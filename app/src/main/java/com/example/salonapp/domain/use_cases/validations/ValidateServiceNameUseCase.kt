package com.example.salonapp.domain.use_cases.validations

import com.example.salonapp.domain.models.ValidationResult
import javax.inject.Inject

class ValidateServiceNameUseCase @Inject constructor(

) {
    fun execute(name: String): ValidationResult {
        if(name.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Cannot be blank"
            )
        }
        if (name.length > 50){
            return ValidationResult(
                successful = false,
                errorMessage = "Too long. Max 50 characters."
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}