package com.example.salonapp.domain.use_cases.validations

import com.example.salonapp.domain.models.ValidationResult
import javax.inject.Inject

class ValidateSuitUseCase @Inject constructor(

) {
    fun execute(streetNumber: String): ValidationResult {
        if(streetNumber.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Cannot be blank"
            )
        }
        if (streetNumber.length > 10){
            return ValidationResult(
                successful = false,
                errorMessage = "Too long. Max 10 characters."
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}