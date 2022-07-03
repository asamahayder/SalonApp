package com.example.salonapp.data.dtos

data class OpeningHoursDTO(
    val employeeId: Int,
    val fridayEnd: String,
    val fridayOpen: Boolean,
    val fridayStart: String,
    val mondayEnd: String,
    val mondayOpen: Boolean,
    val mondayStart: String,
    val saturdayEnd: String,
    val saturdayOpen: Boolean,
    val saturdayStart: String,
    val sundayEnd: String,
    val sundayOpen: Boolean,
    val sundayStart: String,
    val thursdayEnd: String,
    val thursdayOpen: Boolean,
    val thursdayStart: String,
    val tuesdayEnd: String,
    val tuesdayOpen: Boolean,
    val tuesdayStart: String,
    val wednessdayEnd: String,
    val wednessdayOpen: Boolean,
    val wednessdayStart: String
)