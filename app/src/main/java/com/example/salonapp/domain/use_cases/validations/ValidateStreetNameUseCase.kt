package com.example.salonapp.domain.use_cases.validations

import com.example.salonapp.domain.models.ValidationResult
import javax.inject.Inject

class ValidateStreetNameUseCase @Inject constructor(

) {
    fun execute(streetName: String): ValidationResult {
        if(streetName.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Cannot be blank"
            )
        }
        if (streetName.length > 100){
            return ValidationResult(
                successful = false,
                errorMessage = "Too long. Max 100 characters."
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}