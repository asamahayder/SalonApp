package com.example.salonapp.domain.use_cases.validations

import com.example.salonapp.domain.models.ValidationResult
import java.lang.NullPointerException
import java.lang.NumberFormatException
import javax.inject.Inject

class ValidateServicePriceUseCase @Inject constructor(

) {
    fun execute(price: String): ValidationResult {

        if (price.isBlank()){
            return ValidationResult(
                successful = false,
                errorMessage = "Must be specified"
            )
        }

        try {
            val price = price.toInt()

            if (price < 0){
                return ValidationResult(
                    successful = false,
                    errorMessage = "Price can't be negative"
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