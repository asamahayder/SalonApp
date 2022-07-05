package com.example.salonapp.domain.use_cases.validations

import com.example.salonapp.domain.models.ValidationResult
import javax.inject.Inject

class ValidateServiceDescriptionUseCase @Inject constructor(

) {
    fun execute(description: String): ValidationResult {

        if (description.isBlank()){
            return ValidationResult(successful = true)
        }

        if (description.length > 150){
            return ValidationResult(
                successful = false,
                errorMessage = "Too long. Max 150 characters."
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}