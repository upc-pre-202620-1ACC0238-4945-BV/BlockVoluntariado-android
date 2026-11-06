package com.example.blockvoluntariado.feature.volunteerProfile.application

import com.example.blockvoluntariado.feature.volunteerProfile.domain.VolunteerRepository
import javax.inject.Inject

class GetVoluntarioProfileByIdUseCase @Inject constructor(val profile: VolunteerRepository ){

    suspend operator fun invoke(id: Int) = profile.getVoluntarioProfileById(id)

}