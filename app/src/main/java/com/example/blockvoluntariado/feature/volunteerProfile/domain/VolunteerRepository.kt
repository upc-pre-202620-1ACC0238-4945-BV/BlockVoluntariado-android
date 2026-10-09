package com.example.blockvoluntariado.feature.volunteerProfile.domain

import com.example.blockvoluntariado.feature.volunteerProfile.domain.Request.UpdateVolunteerPreferenceRequest
import com.example.blockvoluntariado.feature.volunteerProfile.domain.Request.UpdateVolunteerProfileRequest
import com.example.blockvoluntariado.feature.volunteerProfile.domain.valueObjects.PersonalName
import com.example.blockvoluntariado.feature.volunteerProfile.domain.valueObjects.VolunteerPreferences

interface VolunteerRepository {

    suspend fun getVoluntarioProfileById(id: Int): Result<Volunteer?>

    suspend fun updateVolunteerProfile(id: Int,profile: UpdateVolunteerProfileRequest
    ): Result<Volunteer?>

    suspend fun getVolunteerPreferencesById(
        id: Int
    ): Result<VolunteerPreferences?>

    suspend fun updateVolunteerPreferences(
        id: Int,
        preferences: UpdateVolunteerPreferenceRequest
    ): Result<VolunteerPreferences?>



}