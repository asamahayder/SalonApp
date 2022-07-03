package com.example.salonapp.domain.models

import java.time.LocalDateTime

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