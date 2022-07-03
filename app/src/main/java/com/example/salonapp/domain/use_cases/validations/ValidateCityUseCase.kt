package com.example.salonapp.domain.use_cases.validations

import com.example.salonapp.domain.models.ValidationResult
import javax.inject.Inject

class ValidateCityUseCase @Inject constructor(

) {
    fun execute(city: String): ValidationResult {
        if(city.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Cannot be blank"
            )
        }
        if (city.length > 100){
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