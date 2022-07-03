package com.example.salonapp.domain.use_cases.validations

import android.util.Patterns
import com.example.salonapp.domain.models.ValidationResult
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor(

) {
    fun execute(email: String): ValidationResult {
        if(email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Email is blank"
            )
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Email not valid"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}