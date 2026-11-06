package com.example.blockvoluntariado.feature.volunteerProfile.domain

import com.example.blockvoluntariado.feature.volunteerProfile.domain.valueObjects.PersonalName
import com.example.blockvoluntariado.feature.volunteerProfile.domain.valueObjects.VolunteerPreferences


data class Volunteer(

    val id: Long,
    val userId: Long,
    val name: PersonalName,
    val dniDocument: String,
    val universityName: String,
    val studentCode: String,
    val phoneNumber: String,
    val accumulatedHours: Int,
    val preferences: VolunteerPreferences
)
