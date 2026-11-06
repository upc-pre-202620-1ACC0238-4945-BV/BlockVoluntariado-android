package com.example.blockvoluntariado.feature.volunteerProfile.application

import com.example.blockvoluntariado.feature.volunteerProfile.domain.Request.UpdateVolunteerPreferenceRequest
import com.example.blockvoluntariado.feature.volunteerProfile.domain.VolunteerRepository
import com.example.blockvoluntariado.feature.volunteerProfile.domain.valueObjects.VolunteerPreferences
import javax.inject.Inject

class UpdateVolunteerPreferencesUseCase @Inject constructor(private val repository: VolunteerRepository){
    suspend operator fun invoke(id: Int, preferences: UpdateVolunteerPreferenceRequest) = repository.updateVolunteerPreferences(id, preferences)
}