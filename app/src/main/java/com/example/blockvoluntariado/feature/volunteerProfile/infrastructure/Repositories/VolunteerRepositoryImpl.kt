package com.example.blockvoluntariado.feature.volunteerProfile.infrastructure.Repositories


import com.example.blockvoluntariado.feature.volunteerProfile.domain.Request.UpdateVolunteerPreferenceRequest
import com.example.blockvoluntariado.feature.volunteerProfile.domain.Request.UpdateVolunteerProfileRequest
import com.example.blockvoluntariado.feature.volunteerProfile.domain.Volunteer
import com.example.blockvoluntariado.feature.volunteerProfile.domain.VolunteerRepository
import com.example.blockvoluntariado.feature.volunteerProfile.domain.valueObjects.VolunteerPreferences
import com.example.blockvoluntariado.feature.volunteerProfile.infrastructure.Remote.Request.UpdateVolunteerPreferencesRequestDto
import com.example.blockvoluntariado.feature.volunteerProfile.infrastructure.Remote.VolunteerService
import javax.inject.Inject

class VolunteerRepositoryImpl @Inject constructor(private val service: VolunteerService
): VolunteerRepository {

    override suspend fun getVoluntarioProfileById(id: Int): Result<Volunteer?> {

        return try {

            val response = service.getVolunteerProfileById(id)

            if(response.isSuccessful){
                val responseDto = response.body()
                val volunteer = responseDto?.volunteer?.firstOrNull()?.toDomain()
                Result.success(volunteer)

            }else{

                Result.failure(Exception("Error HTTP: ${response.code()}"))

            }

        }catch(e: Exception){
            Result.failure(e)
        }
    }


    override suspend fun getVolunteerPreferencesById(id: Int): Result<VolunteerPreferences?> {

        val response = service.getVolunteerPreferencesById(id)

        return try{

            if(response.isSuccessful){

                val responseDto = response.body()
                val preference = responseDto?.toDomain()
                Result.success(preference)

            }else{
                Result.failure(Exception("Error HTTP: ${response.code()}"))
            }

        }catch(e: Exception){
            Result.failure(e)
        }




    }

    override suspend fun updateVolunteerPreferences(
        id: Int,
        preferences: UpdateVolunteerPreferenceRequest
    ): Result<VolunteerPreferences?> {

        return try {
            val requestDto = preferences.toDto()

            val response = service.updateVolunteerPreferences(
                id,
                requestDto
            )

            if (response.isSuccessful) {
                Result.success(response.body()?.toDomain())
            } else {
                Result.failure(
                    Exception("Error HTTP: ${response.code()}")
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

    override suspend fun updateVolunteerProfile(
        id: Int,
        profile: UpdateVolunteerProfileRequest
    ): Result<Volunteer?> {
        return try {
            val requestDto = profile.toDto()

            val response = service.updateVolunteerProfile(
                id,
                requestDto
            )

            if (response.isSuccessful) {
                val volunteer = response.body()
                    ?.volunteer
                    ?.firstOrNull()
                    ?.toDomain()

                Result.success(volunteer)
            } else {
                Result.failure(
                    Exception("Error HTTP: ${response.code()}")
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}