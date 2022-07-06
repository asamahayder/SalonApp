package com.example.salonapp.domain.use_cases.validations

import com.example.salonapp.domain.models.ValidationResult
import javax.inject.Inject

class ValidateBookingNoteUseCase @Inject constructor(

) {
    fun execute(note: String): ValidationResult {
        if (note.length > 300){
            return ValidationResult(
                successful = false,
                errorMessage = "Too long. Max 300 characters."
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}