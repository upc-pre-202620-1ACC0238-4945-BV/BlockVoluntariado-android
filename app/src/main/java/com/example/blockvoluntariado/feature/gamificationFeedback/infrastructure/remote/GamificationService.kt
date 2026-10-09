package com.example.blockvoluntariado.feature.gamificationFeedback.infrastructure.remote

import com.example.blockvoluntariado.feature.gamificationFeedback.infrastructure.remote.dto.CreateEvaluacionRequestDto
import com.example.blockvoluntariado.feature.gamificationFeedback.infrastructure.remote.dto.DigitalCertificateDto
import com.example.blockvoluntariado.feature.gamificationFeedback.infrastructure.remote.dto.EvaluacionDto
import com.example.blockvoluntariado.feature.gamificationFeedback.infrastructure.remote.dto.GamificationProfileDto
import com.example.blockvoluntariado.feature.gamificationFeedback.infrastructure.remote.dto.VolunteerHistoryItemDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GamificationService {

    @GET("v1/volunteers/{volunteerId}/certificados")
    suspend fun getCertificatesByVolunteer(
        @Path("volunteerId") volunteerId: Long
    ): List<DigitalCertificateDto>

    @GET("v1/certificados/verificar/{verificationHash}")
    suspend fun verifyCertificate(
        @Path("verificationHash") verificationHash: String
    ): DigitalCertificateDto

    @GET("v1/volunteers/{volunteerId}/logros")
    suspend fun getGamificationProfile(
        @Path("volunteerId") volunteerId: Long
    ): GamificationProfileDto

    @GET("v1/volunteers/{volunteerId}/historial")
    suspend fun getVolunteerHistory(
        @Path("volunteerId") volunteerId: Long
    ): List<VolunteerHistoryItemDto>

    @POST("v1/evaluaciones/voluntarios/{volunteerId}")
    suspend fun reviewVolunteer(
        @Path("volunteerId") volunteerId: Long,
        @Body request: CreateEvaluacionRequestDto
    ): EvaluacionDto

    @POST("v1/evaluaciones/ong/{ongId}")
    suspend fun reviewOrganization(
        @Path("ongId") ongId: Long,
        @Body request: CreateEvaluacionRequestDto
    ): EvaluacionDto

    @GET("v1/evaluaciones/target/{targetId}")
    suspend fun getReviewsByTarget(
        @Path("targetId") targetId: Long
    ): List<EvaluacionDto>
}
