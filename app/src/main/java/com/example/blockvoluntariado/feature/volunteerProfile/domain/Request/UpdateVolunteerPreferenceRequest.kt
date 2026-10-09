package com.example.blockvoluntariado.feature.volunteerProfile.domain.Request

import com.example.blockvoluntariado.feature.volunteerProfile.domain.valueObjects.PersonalName
import com.example.blockvoluntariado.feature.volunteerProfile.domain.valueObjects.VolunteerPreferences


data class UpdateVolunteerPreferenceRequest(


    val preferences: VolunteerPreferences

)
