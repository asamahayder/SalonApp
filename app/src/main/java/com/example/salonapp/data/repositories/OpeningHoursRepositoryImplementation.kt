package com.example.salonapp.data.repositories

import com.example.salonapp.data.dtos.OpeningHoursDTO
import com.example.salonapp.data.dtos.SpecialOpeningHoursDTO
import com.example.salonapp.data.remote.OpeningHoursAPI
import com.example.salonapp.domain.models.OpeningHours
import com.example.salonapp.domain.models.SpecialOpeningHours
import com.example.salonapp.domain.repositories.OpeningHoursRepository
import com.example.salonapp.domain.repositories.UserRepository
import java.time.LocalDateTime
import javax.inject.Inject

class OpeningHoursRepositoryImplementation @Inject constructor(
    private val api: OpeningHoursAPI,
    private val userRepository: UserRepository
): OpeningHoursRepository
{

    override suspend fun getOpeningHoursForEmployeeByWeek(
        employeeId: Int,
        week: LocalDateTime
    ): OpeningHours {
        return toModel(api.getOpeningHoursForEmployeeByWeek(employeeId, week.toString()))
    }

    override suspend fun getNormalHours(employeeId: Int): OpeningHours {
        return toModel(api.getNormalHours(employeeId))
    }

    override suspend fun getSpecialHoursByWeek(
        employeeId: Int,
        week: LocalDateTime
    ): SpecialOpeningHours {
        return toModel(api.getSpecialHoursByWeek(employeeId, week.toString()))
    }

    override suspend fun createSpecialOpeningHours(specialOpeningHours: SpecialOpeningHours): List<SpecialOpeningHours> {
        return api.createSpecialOpeningHours(toDTO(specialOpeningHours)).map { toModel(it) }
    }

    override suspend fun updateSpecialOpeningHours(specialOpeningHours: SpecialOpeningHours): List<SpecialOpeningHours> {
        return api.updateSpecialOpeningHours(toDTO(specialOpeningHours)).map { toModel(it) }
    }

    override suspend fun updateOpeningHours(openingHours: OpeningHours): OpeningHours {
        return toModel(api.updateOpeningHours(toDTO(openingHours)))
    }

    override suspend fun deleteSpecialOpeningHoursByWeek(week: LocalDateTime): List<SpecialOpeningHours> {
        return api.deleteSpecialOpeningHours(week.toString()).map { toModel(it) }
    }

    private fun toDTO(openingHours: OpeningHours): OpeningHoursDTO{
        return OpeningHoursDTO(
            employeeId = openingHours.employee.id,
            mondayStart = openingHours.mondayStart.toString(),
            mondayEnd = openingHours.mondayEnd.toString(),
            mondayOpen = openingHours.mondayOpen,
            tuesdayStart = openingHours.tuesdayStart.toString(),
            tuesdayEnd = openingHours.tuesdayEnd.toString(),
            tuesdayOpen = openingHours.tuesdayOpen,
            wednessdayStart = openingHours.wednessdayStart.toString(),
            wednessdayEnd = openingHours.wednessdayEnd.toString(),
            wednessdayOpen = openingHours.wednessdayOpen,
            thursdayStart = openingHours.thursdayStart.toString(),
            thursdayEnd = openingHours.thursdayEnd.toString(),
            thursdayOpen = openingHours.thursdayOpen,
            fridayStart = openingHours.fridayStart.toString(),
            fridayEnd = openingHours.fridayEnd.toString(),
            fridayOpen = openingHours.fridayOpen,
            saturdayStart = openingHours.saturdayStart.toString(),
            saturdayEnd = openingHours.saturdayEnd.toString(),
            saturdayOpen = openingHours.saturdayOpen,
            sundayStart = openingHours.sundayStart.toString(),
            sundayEnd = openingHours.sundayEnd.toString(),
            sundayOpen = openingHours.sundayOpen,
        )
    }

    private fun toDTO(specialOpeningHours: SpecialOpeningHours): SpecialOpeningHoursDTO{
        return SpecialOpeningHoursDTO(
            week = specialOpeningHours.week.toString(),
            employeeId = specialOpeningHours.employee.id,
            mondayStart = specialOpeningHours.mondayStart.toString(),
            mondayEnd = specialOpeningHours.mondayEnd.toString(),
            mondayOpen = specialOpeningHours.mondayOpen,
            tuesdayStart = specialOpeningHours.tuesdayStart.toString(),
            tuesdayEnd = specialOpeningHours.tuesdayEnd.toString(),
            tuesdayOpen = specialOpeningHours.tuesdayOpen,
            wednessdayStart = specialOpeningHours.wednessdayStart.toString(),
            wednessdayEnd = specialOpeningHours.wednessdayEnd.toString(),
            wednessdayOpen = specialOpeningHours.wednessdayOpen,
            thursdayStart = specialOpeningHours.thursdayStart.toString(),
            thursdayEnd = specialOpeningHours.thursdayEnd.toString(),
            thursdayOpen = specialOpeningHours.thursdayOpen,
            fridayStart = specialOpeningHours.fridayStart.toString(),
            fridayEnd = specialOpeningHours.fridayEnd.toString(),
            fridayOpen = specialOpeningHours.fridayOpen,
            saturdayStart = specialOpeningHours.saturdayStart.toString(),
            saturdayEnd = specialOpeningHours.saturdayEnd.toString(),
            saturdayOpen = specialOpeningHours.saturdayOpen,
            sundayStart = specialOpeningHours.sundayStart.toString(),
            sundayEnd = specialOpeningHours.sundayEnd.toString(),
            sundayOpen = specialOpeningHours.sundayOpen,
        )
    }

    private suspend fun toModel(openingHoursDTO: OpeningHoursDTO): OpeningHours{
        
        val employee = userRepository.getUserById(openingHoursDTO.employeeId)
        
        return OpeningHours(
            employee = employee,
            mondayStart = LocalDateTime.parse(openingHoursDTO.mondayStart),
            mondayEnd = LocalDateTime.parse(openingHoursDTO.mondayEnd),
            mondayOpen = openingHoursDTO.mondayOpen,
            tuesdayStart = LocalDateTime.parse(openingHoursDTO.tuesdayStart),
            tuesdayEnd = LocalDateTime.parse(openingHoursDTO.tuesdayEnd),
            tuesdayOpen = openingHoursDTO.tuesdayOpen,
            wednessdayStart = LocalDateTime.parse(openingHoursDTO.wednessdayStart),
            wednessdayEnd = LocalDateTime.parse(openingHoursDTO.wednessdayEnd),
            wednessdayOpen = openingHoursDTO.wednessdayOpen,
            thursdayStart = LocalDateTime.parse(openingHoursDTO.thursdayStart),
            thursdayEnd = LocalDateTime.parse(openingHoursDTO.thursdayEnd),
            thursdayOpen = openingHoursDTO.thursdayOpen,
            fridayStart = LocalDateTime.parse(openingHoursDTO.fridayStart),
            fridayEnd = LocalDateTime.parse(openingHoursDTO.fridayEnd),
            fridayOpen = openingHoursDTO.fridayOpen,
            saturdayStart = LocalDateTime.parse(openingHoursDTO.saturdayStart),
            saturdayEnd = LocalDateTime.parse(openingHoursDTO.saturdayEnd),
            saturdayOpen = openingHoursDTO.saturdayOpen,
            sundayStart = LocalDateTime.parse(openingHoursDTO.sundayStart),
            sundayEnd = LocalDateTime.parse(openingHoursDTO.sundayEnd),
            sundayOpen = openingHoursDTO.sundayOpen,
        )
    }

    private suspend fun toModel(specialOpeningHoursDTO: SpecialOpeningHoursDTO): SpecialOpeningHours{
        val employee = userRepository.getUserById(specialOpeningHoursDTO.employeeId)

        return SpecialOpeningHours(
            employee = employee,
            week = LocalDateTime.parse(specialOpeningHoursDTO.week),
            mondayStart = LocalDateTime.parse(specialOpeningHoursDTO.mondayStart),
            mondayEnd = LocalDateTime.parse(specialOpeningHoursDTO.mondayEnd),
            mondayOpen = specialOpeningHoursDTO.mondayOpen,
            tuesdayStart = LocalDateTime.parse(specialOpeningHoursDTO.tuesdayStart),
            tuesdayEnd = LocalDateTime.parse(specialOpeningHoursDTO.tuesdayEnd),
            tuesdayOpen = specialOpeningHoursDTO.tuesdayOpen,
            wednessdayStart = LocalDateTime.parse(specialOpeningHoursDTO.wednessdayStart),
            wednessdayEnd = LocalDateTime.parse(specialOpeningHoursDTO.wednessdayEnd),
            wednessdayOpen = specialOpeningHoursDTO.wednessdayOpen,
            thursdayStart = LocalDateTime.parse(specialOpeningHoursDTO.thursdayStart),
            thursdayEnd = LocalDateTime.parse(specialOpeningHoursDTO.thursdayEnd),
            thursdayOpen = specialOpeningHoursDTO.thursdayOpen,
            fridayStart = LocalDateTime.parse(specialOpeningHoursDTO.fridayStart),
            fridayEnd = LocalDateTime.parse(specialOpeningHoursDTO.fridayEnd),
            fridayOpen = specialOpeningHoursDTO.fridayOpen,
            saturdayStart = LocalDateTime.parse(specialOpeningHoursDTO.saturdayStart),
            saturdayEnd = LocalDateTime.parse(specialOpeningHoursDTO.saturdayEnd),
            saturdayOpen = specialOpeningHoursDTO.saturdayOpen,
            sundayStart = LocalDateTime.parse(specialOpeningHoursDTO.sundayStart),
            sundayEnd = LocalDateTime.parse(specialOpeningHoursDTO.sundayEnd),
            sundayOpen = specialOpeningHoursDTO.sundayOpen,
        )
    }

    


}