package com.example.salonapp.domain.use_cases.validations

import com.example.salonapp.domain.models.ValidationResult
import java.lang.NullPointerException
import java.lang.NumberFormatException
import javax.inject.Inject

class ValidateServiceDurationUseCase @Inject constructor(

) {

    fun execute(durationInMinutes: String): ValidationResult {

        if (durationInMinutes.isBlank()){
            return ValidationResult(
                successful = false,
                errorMessage = "Must be specified"
            )
        }

        try {
            val durationInMinutes = durationInMinutes.toInt()

            if (durationInMinutes < 1 || durationInMinutes > 500){
                return ValidationResult(
                    successful = false,
                    errorMessage = "Duration must be between 1 and 500 minutes"
                )
            }

            return ValidationResult(
                successful = true
            )


        }catch (e: NumberFormatException){
            return ValidationResult(
                successful = false,
                errorMessage = "Must be a valid Integer"
            )
        }
    }
}