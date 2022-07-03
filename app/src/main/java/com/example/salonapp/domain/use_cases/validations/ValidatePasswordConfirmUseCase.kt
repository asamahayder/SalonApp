package com.example.salonapp.domain.use_cases.validations

import com.example.salonapp.domain.models.ValidationResult
import javax.inject.Inject

class ValidatePasswordConfirmUseCase @Inject constructor(

){
    fun execute(passwordConfirm: String, password: String): ValidationResult {
        if(passwordConfirm != password) {
            return ValidationResult(
                successful = false,
                errorMessage = "Not matching"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}