package com.example.blockvoluntariado.feature.volunteerProfile.infrastructure.Remote.ValueObjects

data class VolunteerPreferencesDto(
    val causes: List<String>,
    val availability: String,
    val preferredModality: String
)