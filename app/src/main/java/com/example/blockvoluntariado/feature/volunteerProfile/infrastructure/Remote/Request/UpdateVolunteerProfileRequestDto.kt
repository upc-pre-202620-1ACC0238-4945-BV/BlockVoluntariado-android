package com.example.blockvoluntariado.feature.volunteerProfile.infrastructure.Remote.Request

import com.example.blockvoluntariado.feature.volunteerProfile.infrastructure.Remote.ValueObjects.PersonalNameDto

data class UpdateVolunteerProfileRequestDto(

    val personalName: PersonalNameDto

)
