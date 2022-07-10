package com.example.salonapp.domain.use_cases.validations

import com.example.salonapp.domain.models.ValidationResult
import java.time.LocalTime
import javax.inject.Inject

class ValidateBookingTimeUseCase @Inject constructor(

) {
    fun execute(time: LocalTime?): ValidationResult {
        if (time == null){
            return ValidationResult(
                successful = false,
                errorMessage = "Must be specified"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}