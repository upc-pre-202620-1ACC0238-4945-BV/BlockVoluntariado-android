package com.example.blockvoluntariado.feature.volunteerProfile.application

import com.example.blockvoluntariado.feature.volunteerProfile.domain.Request.UpdateVolunteerProfileRequest
import com.example.blockvoluntariado.feature.volunteerProfile.domain.VolunteerRepository
import javax.inject.Inject

class UpdateVolunteerProfileUseCase @Inject constructor(private val repository: VolunteerRepository) {
    suspend operator fun invoke(id: Int, profile: UpdateVolunteerProfileRequest) = repository.updateVolunteerProfile(id, profile)
}