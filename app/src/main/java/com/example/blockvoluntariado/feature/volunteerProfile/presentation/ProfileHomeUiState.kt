package com.example.blockvoluntariado.feature.volunteerProfile.presentation
import com.example.blockvoluntariado.feature.volunteerProfile.domain.Volunteer
import com.example.blockvoluntariado.feature.volunteerProfile.domain.valueObjects.VolunteerPreferences

data class ProfileHomeUiState(
    val volunteer: Volunteer? = null,
    val preferences: VolunteerPreferences? = null,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)
