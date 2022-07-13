package com.example.salonapp.presentation.employee.hours

import java.time.LocalTime

sealed class HoursEditEvent {


    object OnInitialize : HoursEditEvent()

    data class OnSetMondayStart(val mondayStart:LocalTime): HoursEditEvent()
    data class OnSetMondayEnd(val mondayEnd:LocalTime): HoursEditEvent()
    data class OnSetMondayOpen(val mondayOpen:Boolean): HoursEditEvent()

    data class OnSetTuesdayStart(val tuesdayStart:LocalTime): HoursEditEvent()
    data class OnSetTuesdayEnd(val tuesdayEnd:LocalTime): HoursEditEvent()
    data class OnSetTuesdayOpen(val tuesdayOpen:Boolean): HoursEditEvent()

    data class OnSetWednesdayStart(val wednesdayStart:LocalTime): HoursEditEvent()
    data class OnSetWednesdayEnd(val wednesdayEnd:LocalTime): HoursEditEvent()
    data class OnSetWednesdayOpen(val wednesdayOpen:Boolean): HoursEditEvent()

    data class OnSetThursdayStart(val thursdayStart:LocalTime): HoursEditEvent()
    data class OnSetThursdayEnd(val thursdayEnd:LocalTime): HoursEditEvent()
    data class OnSetThursdayOpen(val thursdayOpen:Boolean): HoursEditEvent()

    data class OnSetFridayStart(val fridayStart:LocalTime): HoursEditEvent()
    data class OnSetFridayEnd(val fridayEnd:LocalTime): HoursEditEvent()
    data class OnSetFridayOpen(val fridayOpen:Boolean): HoursEditEvent()

    data class OnSetSaturdayStart(val saturdayStart:LocalTime): HoursEditEvent()
    data class OnSetSaturdayEnd(val saturdayEnd:LocalTime): HoursEditEvent()
    data class OnSetSaturdayOpen(val saturdayOpen:Boolean): HoursEditEvent()

    data class OnSetSundayStart(val sundayStart:LocalTime): HoursEditEvent()
    data class OnSetSundayEnd(val sundayEnd:LocalTime): HoursEditEvent()
    data class OnSetSundayOpen(val sundayOpen:Boolean): HoursEditEvent()


    object OnSubmit: HoursEditEvent()
    object OnUpdateSuccess: HoursEditEvent()


}