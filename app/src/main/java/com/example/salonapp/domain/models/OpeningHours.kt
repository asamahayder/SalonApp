package com.example.salonapp.domain.models

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class OpeningHours(
    val employee: User,
    val fridayEnd: LocalTime,
    val fridayOpen: Boolean,
    val fridayStart: LocalTime,
    val mondayEnd: LocalTime,
    val mondayOpen: Boolean,
    val mondayStart: LocalTime,
    val saturdayEnd: LocalTime,
    val saturdayOpen: Boolean,
    val saturdayStart: LocalTime,
    val sundayEnd: LocalTime,
    val sundayOpen: Boolean,
    val sundayStart: LocalTime,
    val thursdayEnd: LocalTime,
    val thursdayOpen: Boolean,
    val thursdayStart: LocalTime,
    val tuesdayEnd: LocalTime,
    val tuesdayOpen: Boolean,
    val tuesdayStart: LocalTime,
    val wednessdayEnd: LocalTime,
    val wednessdayOpen: Boolean,
    val wednessdayStart: LocalTime
)

fun OpeningHours.getStartOfDay(date: LocalDate): LocalTime{
    return when(date.dayOfWeek){
        DayOfWeek.MONDAY -> mondayStart
        DayOfWeek.TUESDAY -> tuesdayStart
        DayOfWeek.WEDNESDAY -> wednessdayStart
        DayOfWeek.THURSDAY -> thursdayStart
        DayOfWeek.FRIDAY -> fridayStart
        DayOfWeek.SATURDAY -> saturdayStart
        DayOfWeek.SUNDAY -> sundayStart
    }
}

fun OpeningHours.getEndOfDay(date:LocalDate): LocalTime{
    return when(date.dayOfWeek){
        DayOfWeek.MONDAY -> mondayEnd
        DayOfWeek.TUESDAY -> tuesdayEnd
        DayOfWeek.WEDNESDAY -> wednessdayEnd
        DayOfWeek.THURSDAY -> thursdayEnd
        DayOfWeek.FRIDAY -> fridayEnd
        DayOfWeek.SATURDAY -> saturdayEnd
        DayOfWeek.SUNDAY -> sundayEnd
    }
}

fun OpeningHours.isOpen(date:LocalDate): Boolean{
    return when(date.dayOfWeek){
        DayOfWeek.MONDAY -> mondayOpen
        DayOfWeek.TUESDAY -> tuesdayOpen
        DayOfWeek.WEDNESDAY -> wednessdayOpen
        DayOfWeek.THURSDAY -> thursdayOpen
        DayOfWeek.FRIDAY -> fridayOpen
        DayOfWeek.SATURDAY -> saturdayOpen
        DayOfWeek.SUNDAY -> sundayOpen
    }
}