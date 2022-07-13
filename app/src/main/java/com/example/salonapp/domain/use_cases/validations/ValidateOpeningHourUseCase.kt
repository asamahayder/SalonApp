package com.example.salonapp.domain.use_cases.validations

import com.example.salonapp.domain.models.ValidationResult
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

class ValidateOpeningHourUseCase @Inject constructor(

) {
    fun execute(startTime:LocalTime, endTime:LocalTime): ValidationResult {


        if (startTime.isAfter(endTime)){
            return ValidationResult(
                successful = false,
                errorMessage = "Start can't be after End"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}