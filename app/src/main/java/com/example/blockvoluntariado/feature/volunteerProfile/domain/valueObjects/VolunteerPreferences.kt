package com.example.blockvoluntariado.feature.volunteerProfile.domain.valueObjects

data class VolunteerPreferences(

    val causes: List<String>,
    val availability: String,
    val preferredModality: String

)
