package com.example.salonapp.domain.use_cases.validations

import android.content.res.Resources
import android.util.Patterns
import com.example.salonapp.R
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