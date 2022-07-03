package com.example.salonapp.domain.use_cases.validations

import com.example.salonapp.domain.models.ValidationResult
import javax.inject.Inject

class ValidateSalonNameUseCase @Inject constructor(

) {
    fun execute(name: String): ValidationResult {
        if(name.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Cannot be blank"
            )
        }
        if (name.length > 100){
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