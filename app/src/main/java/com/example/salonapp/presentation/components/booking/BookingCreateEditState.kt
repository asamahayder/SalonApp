package com.example.salonapp.presentation.components.booking

import androidx.compose.ui.geometry.Size
import com.example.salonapp.domain.models.Salon
import com.example.salonapp.domain.models.Service
import com.example.salonapp.domain.models.User
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class BookingCreateEditState (

    val bookingId: Int? = null,

    val selectedEmployee: User? = null,

    val serviceSelectionExpanded: Boolean = false,
    val selectedService: Service? = null,

    val services: List<Service> = listOf(),

    val dateTimeSelectionExpanded: Boolean = false,
    val dateTime: LocalDateTime? = null,

    val date: LocalDate? = null,
    val dateError: String? = null,


    val time: LocalTime? = null,
    val timeError: String? = null,

    val note: String = "",
    val noteError: String? = null,

    val isLoading: Boolean = false,
    val error: String? = null,

    val startTimeOfDay: LocalTime? = null,
    val endTimeOfDay: LocalTime? = null,

    val serviceSelectionWidth: Size = Size.Zero,

    val salon: Salon? = null,
    val salonId:Int? = null,
    val employeeId: Int? = null,

    val fetchingOpeningHours: Boolean = true,

    val activeStep: Step = Step.one
)

enum class Step{
    one,
    two,
    three,
    four
}

fun Step.getStringValue():String{
    return when(this){
        Step.one -> 1
        Step.two -> 2
        Step.three -> 3
        Step.four -> 4
    }.toString()
}

fun Step.getIntValue():Int{
    return when(this){
        Step.one -> 1
        Step.two -> 2
        Step.three -> 3
        Step.four -> 4
    }
}