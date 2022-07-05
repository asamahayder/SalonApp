package com.example.salonapp.domain.use_cases.validations

import com.example.salonapp.domain.models.ValidationResult
import java.lang.NumberFormatException
import javax.inject.Inject

class ValidateServicePauseUseCase @Inject constructor(

) {

    fun execute(pauseStartString: String, pauseEndString: String, durationInMinutes: Int): ValidationResult {

        if ((pauseStartString.isBlank() && pauseEndString.isNotBlank())
            ||
            (pauseStartString.isNotBlank() && pauseEndString.isBlank())
        ){
            return ValidationResult(
                successful = false,
                errorMessage = "Pause start/end must both be specified or empty."
            )
        }

        if (pauseStartString.isNotBlank() && pauseEndString.isNotBlank()){
            try {
                val pauseStart = pauseStartString.toInt()
                val pauseEnd = pauseEndString.toInt()

                if (pauseStart < 0 || pauseStart > 500){
                    return ValidationResult(
                        successful = false,
                        errorMessage = "Pause values must be between 0-500"
                    )
                }

                if (pauseEnd < 0 || pauseEnd > 500){
                    return ValidationResult(
                        successful = false,
                        errorMessage = "Pause values must be between 0-500"
                    )
                }

                if (pauseStart > durationInMinutes || pauseEnd > durationInMinutes){
                    return ValidationResult(
                        successful = false,
                        errorMessage = "Pause can't start/end after duration"
                    )
                }

                if (pauseStart == pauseEnd){
                    return ValidationResult(
                        successful = false,
                        errorMessage = "Pause start/end can't have same value"
                    )
                }

                if (pauseEnd < pauseStart){
                    return ValidationResult(
                        successful = false,
                        errorMessage = "Pause End can't be before Start"
                    )
                }

                if (pauseStart == 0){
                    return ValidationResult(
                        successful = false,
                        errorMessage = "Pause can't start at 0"
                    )
                }

                if (pauseEnd == durationInMinutes){
                    return ValidationResult(
                        successful = false,
                        errorMessage = "Pause can't end at duration"
                    )
                }

            }catch (e: NumberFormatException){
                return ValidationResult(
                    successful = false,
                    errorMessage = "Pause fields must contain valid Integers"
                )
            }
        }

        return ValidationResult(
            successful = true
        )

    }
}