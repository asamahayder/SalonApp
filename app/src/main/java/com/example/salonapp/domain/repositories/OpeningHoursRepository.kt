package com.example.salonapp.domain.repositories

import com.example.salonapp.domain.models.OpeningHours
import com.example.salonapp.domain.models.SpecialOpeningHours
import java.time.LocalDateTime

interface OpeningHoursRepository {

    suspend fun getOpeningHoursForEmployeeByWeek(employeeId: Int, week: LocalDateTime):OpeningHours

    suspend fun getNormalHours(employeeId: Int):OpeningHours

    suspend fun getSpecialHoursByWeek(employeeId: Int, week: LocalDateTime):SpecialOpeningHours

    suspend fun createSpecialOpeningHours(specialOpeningHours: SpecialOpeningHours):List<SpecialOpeningHours>

    suspend fun updateSpecialOpeningHours(specialOpeningHours: SpecialOpeningHours):List<SpecialOpeningHours>

    suspend fun updateOpeningHours(openingHours: OpeningHours):OpeningHours

    suspend fun deleteSpecialOpeningHoursByWeek(week:LocalDateTime):List<SpecialOpeningHours>

}