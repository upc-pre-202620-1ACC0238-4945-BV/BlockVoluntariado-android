package com.example.blockvoluntariado.feature.volunteerProfile.infrastructure.Remote


import com.example.blockvoluntariado.feature.volunteerProfile.infrastructure.Remote.Request.UpdateVolunteerPreferencesRequestDto
import com.example.blockvoluntariado.feature.volunteerProfile.infrastructure.Remote.Request.UpdateVolunteerProfileRequestDto
import com.example.blockvoluntariado.feature.volunteerProfile.infrastructure.Remote.ValueObjects.VolunteerPreferencesDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface VolunteerService {


    @GET("v1/volunteers/{volunteerId}/profile")
    suspend fun getVolunteerProfileById(
        @Path("volunteerId") volunteerId: Int
    ): Response<VolunteerResponseDto>


    @PUT("v1/volunteers/{volunteerId}/profile")
    suspend fun updateVolunteerProfile(
        @Path("volunteerId") volunteerId: Int,
        @Body request: UpdateVolunteerProfileRequestDto
    ): Response<VolunteerResponseDto>

    // GET /api/v1/volunteers/{volunteerId}/preferences
    @GET("v1/volunteers/{volunteerId}/preferences")
    suspend fun getVolunteerPreferencesById(
        @Path("volunteerId") volunteerId: Int
    ): Response<VolunteerPreferencesDto>

    // PUT /api/v1/volunteers/{volunteerId}/preferences
    @PUT("v1/volunteers/{volunteerId}/preferences")
    suspend fun updateVolunteerPreferences(
        @Path("volunteerId") volunteerId: Int,
        @Body request: UpdateVolunteerPreferencesRequestDto
    ): Response<VolunteerPreferencesDto>


}