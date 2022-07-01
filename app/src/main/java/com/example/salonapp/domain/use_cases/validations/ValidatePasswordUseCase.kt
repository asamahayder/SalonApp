package com.example.salonapp.domain.use_cases.validations

import android.content.res.Resources
import com.example.salonapp.R
import com.example.salonapp.domain.models.ValidationResult
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor(

){
    fun execute(password: String): ValidationResult {
        if(password.length < 14) {
            return ValidationResult(
                successful = false,
                errorMessage = "Too short, must be at least 14 characters"
            )
        }
        /*val containsLettersAndDigits = password.any { it.isDigit() } &&
                password.any { it.isLetter() }
        if(!containsLettersAndDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = "Not valid password"
            )
        }*/
        return ValidationResult(
            successful = true
        )
    }
}