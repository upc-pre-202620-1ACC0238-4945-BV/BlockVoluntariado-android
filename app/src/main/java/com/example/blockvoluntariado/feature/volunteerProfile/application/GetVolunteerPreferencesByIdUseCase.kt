package com.example.blockvoluntariado.feature.volunteerProfile.application

import com.example.blockvoluntariado.feature.volunteerProfile.domain.VolunteerRepository

import javax.inject.Inject

class GetVolunteerPreferencesByIdUseCase @Inject constructor(val preferences: VolunteerRepository) {

    suspend operator fun invoke(id: Int) = preferences.getVolunteerPreferencesById(id)
}