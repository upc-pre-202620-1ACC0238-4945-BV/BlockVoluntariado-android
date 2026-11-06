package com.example.blockvoluntariado.feature.volunteerProfile.infrastructure.Repositories

import com.example.blockvoluntariado.feature.volunteerProfile.domain.Request.UpdateVolunteerPreferenceRequest
import com.example.blockvoluntariado.feature.volunteerProfile.domain.Request.UpdateVolunteerProfileRequest
import com.example.blockvoluntariado.feature.volunteerProfile.domain.Volunteer
import com.example.blockvoluntariado.feature.volunteerProfile.domain.valueObjects.PersonalName
import com.example.blockvoluntariado.feature.volunteerProfile.domain.valueObjects.VolunteerPreferences
import com.example.blockvoluntariado.feature.volunteerProfile.infrastructure.Remote.Request.UpdateVolunteerPreferencesRequestDto
import com.example.blockvoluntariado.feature.volunteerProfile.infrastructure.Remote.Request.UpdateVolunteerProfileRequestDto
import com.example.blockvoluntariado.feature.volunteerProfile.infrastructure.Remote.ValueObjects.PersonalNameDto
import com.example.blockvoluntariado.feature.volunteerProfile.infrastructure.Remote.ValueObjects.VolunteerPreferencesDto
import com.example.blockvoluntariado.feature.volunteerProfile.infrastructure.Remote.VolunteerDto


fun VolunteerDto.toDomain(): Volunteer {

    return Volunteer(
        id = this.id,
        userId = this.userId,
        name = this.name.toDomain(),
        dniDocument = this.dniDocument,
        universityName = this.universityName,
        studentCode = this.studentCode,
        phoneNumber = this.phoneNumber,
        accumulatedHours = this.accumulatedHours,
        preferences = this.preferences.toDomain()
    )
}

fun PersonalNameDto.toDomain(): PersonalName {
    return PersonalName(
        firstName = this.firstName,
        lastName = this.lastName
    )
}

fun VolunteerPreferencesDto.toDomain(): VolunteerPreferences {
    return VolunteerPreferences(
        causes = this.causes,
        availability = this.availability,
        preferredModality = this.preferredModality
    )
}


fun PersonalName.toDto(): PersonalNameDto {
    return PersonalNameDto(
        firstName = firstName,
        lastName = lastName
    )
}

fun VolunteerPreferences.toDto(): VolunteerPreferencesDto {
    return VolunteerPreferencesDto(
        causes = causes,
        availability = availability,
        preferredModality = preferredModality
    )
}

fun UpdateVolunteerPreferenceRequest.toDto(): UpdateVolunteerPreferencesRequestDto {
    return UpdateVolunteerPreferencesRequestDto(
        preferences = preferences.toDto()
    )
}

fun UpdateVolunteerProfileRequest.toDto(): UpdateVolunteerProfileRequestDto {
    return UpdateVolunteerProfileRequestDto(
        personalName = personalName.toDto()
    )
}