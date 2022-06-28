package com.example.salonapp.domain.use_cases.validations

import android.content.res.Resources
import android.util.Patterns
import androidx.compose.ui.res.stringResource
import com.example.salonapp.R
import com.example.salonapp.domain.models.ValidationResult
import javax.inject.Inject

class ValidatePhoneUseCase @Inject constructor(

){
    fun execute(phone: String): ValidationResult {
        if(phone.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Cannot be blank"
            )
        }
        if(!Patterns.PHONE.matcher(phone).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Invalid phone number"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}