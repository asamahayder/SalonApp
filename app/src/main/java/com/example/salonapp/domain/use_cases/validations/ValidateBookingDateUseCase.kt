package com.example.salonapp.domain.use_cases.validations

import com.example.salonapp.domain.models.ValidationResult
import java.time.LocalDate
import javax.inject.Inject

class ValidateBookingDateUseCase @Inject constructor(

) {
    fun execute(date: LocalDate?): ValidationResult {
        if (date == null){
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