package com.example.blockvoluntariado.feature.volunteerProfile.infrastructure.Remote

import com.example.blockvoluntariado.feature.volunteerProfile.infrastructure.Remote.ValueObjects.PersonalNameDto
import com.example.blockvoluntariado.feature.volunteerProfile.infrastructure.Remote.ValueObjects.VolunteerPreferencesDto

data class VolunteerDto(

    val id: Long,
    val userId: Long,
    val name: PersonalNameDto,
    val dniDocument: String,
    val universityName: String,
    val studentCode: String,
    val phoneNumber: String,
    val accumulatedHours: Int,
    val preferences: VolunteerPreferencesDto


)
