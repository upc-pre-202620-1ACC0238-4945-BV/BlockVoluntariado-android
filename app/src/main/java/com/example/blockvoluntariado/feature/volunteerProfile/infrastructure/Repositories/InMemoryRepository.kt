package com.example.blockvoluntariado.feature.volunteerProfile.infrastructure.Repositories

import com.example.blockvoluntariado.feature.volunteerProfile.domain.Request.UpdateVolunteerPreferenceRequest
import com.example.blockvoluntariado.feature.volunteerProfile.domain.Request.UpdateVolunteerProfileRequest
import com.example.blockvoluntariado.feature.volunteerProfile.domain.Volunteer
import com.example.blockvoluntariado.feature.volunteerProfile.domain.VolunteerRepository
import com.example.blockvoluntariado.feature.volunteerProfile.domain.valueObjects.PersonalName
import com.example.blockvoluntariado.feature.volunteerProfile.domain.valueObjects.VolunteerPreferences
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

class InMemoryRepository @Inject constructor() : VolunteerRepository {

    private val _volunteers = mutableListOf(
        Volunteer(
            id = 1L,
            userId = 101L,
            name = PersonalName(
                firstName = "Carlos",
                lastName = "Ramírez"
            ),
            dniDocument = "12345678",
            universityName = "Universidad Nacional Mayor de San Marcos",
            studentCode = "20210001",
            phoneNumber = "987654321",
            accumulatedHours = 45,
            preferences = VolunteerPreferences(
                causes = listOf("Educación", "Medio Ambiente"),
                availability = "Fines de semana",
                preferredModality = "Presencial"
            )
        ),
        Volunteer(
            id = 2L,
            userId = 102L,
            name = PersonalName(
                firstName = "María",
                lastName = "Fernández"
            ),
            dniDocument = "87654321",
            universityName = "Pontificia Universidad Católica del Perú",
            studentCode = "20220015",
            phoneNumber = "912345678",
            accumulatedHours = 72,
            preferences = VolunteerPreferences(
                causes = listOf("Protección Animal", "Salud"),
                availability = "Tardes",
                preferredModality = "Virtual"
            )
        )
    )

    override suspend fun getVoluntarioProfileById(id: Int): Result<Volunteer?> {
        delay(300.milliseconds)
        val volunteer = _volunteers.find { it.id.toInt() == id }
        return Result.success(volunteer)
    }

    override suspend fun updateVolunteerProfile(
        id: Int,
        profile: UpdateVolunteerProfileRequest
    ): Result<Volunteer?> {
        delay(300.milliseconds)
        val index = _volunteers.indexOfFirst { it.id.toInt() == id }
        return if (index != -1) {
            val updated = _volunteers[index].copy(name = profile.personalName)
            _volunteers[index] = updated
            Result.success(updated)
        } else {
            Result.failure(Exception("Voluntario no encontrado"))
        }
    }

    override suspend fun getVolunteerPreferencesById(id: Int): Result<VolunteerPreferences?> {
        delay(300.milliseconds)
        val volunteer = _volunteers.find { it.id.toInt() == id }
        return Result.success(volunteer?.preferences)
    }

    override suspend fun updateVolunteerPreferences(
        id: Int,
        preferences: UpdateVolunteerPreferenceRequest
    ): Result<VolunteerPreferences?> {
        delay(300.milliseconds)
        val index = _volunteers.indexOfFirst { it.id.toInt() == id }
        return if (index != -1) {
            val updated = _volunteers[index].copy(preferences = preferences.preferences)
            _volunteers[index] = updated
            Result.success(updated.preferences)
        } else {
            Result.failure(Exception("Voluntario no encontrado"))
        }
    }
}