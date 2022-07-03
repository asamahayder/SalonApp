package com.example.salonapp.domain.use_cases.validations

import com.example.salonapp.domain.models.ValidationResult
import javax.inject.Inject

class ValidatePostCodeUseCase @Inject constructor(

) {
    fun execute(postCode: String): ValidationResult {
        if(postCode.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Cannot be blank"
            )
        }
        if (postCode.length > 50){
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