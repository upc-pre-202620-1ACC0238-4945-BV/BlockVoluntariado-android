package com.example.blockvoluntariado.feature.application.infrastructure.remote

import com.example.blockvoluntariado.feature.application.infrastructure.remote.dto.CreatePostulacionRequestDto
import com.example.blockvoluntariado.feature.application.infrastructure.remote.dto.PostulacionDto
import com.example.blockvoluntariado.feature.application.infrastructure.remote.dto.RejectPostulacionRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApplicationService {

    @POST("v1/convocatorias/{convocatoriaId}/postulaciones")
    suspend fun applyToConvocatoria(
        @Path("convocatoriaId") convocatoriaId: Long,
        @Body request: CreatePostulacionRequestDto
    ): Response<PostulacionDto>

    @GET("v1/volunteers/{volunteerId}/postulaciones")
    suspend fun getPostulacionesByVolunteer(
        @Path("volunteerId") volunteerId: Long
    ): Response<List<PostulacionDto>>

    @GET("v1/convocatorias/{convocatoriaId}/postulantes")
    suspend fun getPostulantesByConvocatoria(
        @Path("convocatoriaId") convocatoriaId: Long
    ): Response<List<PostulacionDto>>

    @PATCH("v1/postulaciones/{postulacionId}/aceptar")
    suspend fun acceptPostulacion(
        @Path("postulacionId") postulacionId: Long
    ): Response<PostulacionDto>

    @PATCH("v1/postulaciones/{postulacionId}/rechazar")
    suspend fun rejectPostulacion(
        @Path("postulacionId") postulacionId: Long,
        @Body request: RejectPostulacionRequestDto
    ): Response<PostulacionDto>

    @PATCH("v1/postulaciones/{postulacionId}/cancelar")
    suspend fun cancelPostulacion(
        @Path("postulacionId") postulacionId: Long
    ): Response<PostulacionDto>

    @GET("v1/postulaciones/{postulacionId}")
    suspend fun getPostulacionById(
        @Path("postulacionId") postulacionId: Long
    ): Response<PostulacionDto>
}
