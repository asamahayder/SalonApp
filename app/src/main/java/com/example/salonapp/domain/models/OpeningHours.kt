package com.example.salonapp.domain.models

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class OpeningHours(
    val employee: User,
    val fridayEnd: LocalDateTime,
    val fridayOpen: Boolean,
    val fridayStart: LocalDateTime,
    val mondayEnd: LocalDateTime,
    val mondayOpen: Boolean,
    val mondayStart: LocalDateTime,
    val saturdayEnd: LocalDateTime,
    val saturdayOpen: Boolean,
    val saturdayStart: LocalDateTime,
    val sundayEnd: LocalDateTime,
    val sundayOpen: Boolean,
    val sundayStart: LocalDateTime,
    val thursdayEnd: LocalDateTime,
    val thursdayOpen: Boolean,
    val thursdayStart: LocalDateTime,
    val tuesdayEnd: LocalDateTime,
    val tuesdayOpen: Boolean,
    val tuesdayStart: LocalDateTime,
    val wednessdayEnd: LocalDateTime,
    val wednessdayOpen: Boolean,
    val wednessdayStart: LocalDateTime
)

fun OpeningHours.getStartOfDay(date: LocalDate): LocalTime{
    return when(date.dayOfWeek){
        DayOfWeek.MONDAY -> mondayStart.toLocalTime()
        DayOfWeek.TUESDAY -> tuesdayStart.toLocalTime()
        DayOfWeek.WEDNESDAY -> wednessdayStart.toLocalTime()
        DayOfWeek.THURSDAY -> thursdayStart.toLocalTime()
        DayOfWeek.FRIDAY -> fridayStart.toLocalTime()
        DayOfWeek.SATURDAY -> saturdayStart.toLocalTime()
        DayOfWeek.SUNDAY -> sundayStart.toLocalTime()
    }
}

fun OpeningHours.getEndOfDay(date:LocalDate): LocalTime{
    return when(date.dayOfWeek){
        DayOfWeek.MONDAY -> mondayEnd.toLocalTime()
        DayOfWeek.TUESDAY -> tuesdayEnd.toLocalTime()
        DayOfWeek.WEDNESDAY -> wednessdayEnd.toLocalTime()
        DayOfWeek.THURSDAY -> thursdayEnd.toLocalTime()
        DayOfWeek.FRIDAY -> fridayEnd.toLocalTime()
        DayOfWeek.SATURDAY -> saturdayEnd.toLocalTime()
        DayOfWeek.SUNDAY -> sundayEnd.toLocalTime()
    }
}