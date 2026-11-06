package com.example.blockvoluntariado.feature.participationTracking.infrastructure.remote

import com.example.blockvoluntariado.feature.participationTracking.infrastructure.remote.dto.ActividadDto
import com.example.blockvoluntariado.feature.participationTracking.infrastructure.remote.dto.AttendanceRecordDto
import com.example.blockvoluntariado.feature.participationTracking.infrastructure.remote.dto.RecordBulkAttendanceRequestDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ParticipationService {

    @GET("v1/actividades/{id}")
    suspend fun getActividadById(
        @Path("id") id: Long
    ): ActividadDto

    @GET("v1/actividades/convocatoria/{convocatoriaId}")
    suspend fun getActividadByConvocatoria(
        @Path("convocatoriaId") convocatoriaId: Long
    ): ActividadDto

    @GET("v1/actividades/{id}/participantes")
    suspend fun getParticipantes(
        @Path("id") id: Long
    ): List<AttendanceRecordDto>

    @POST("v1/actividades/{id}/asistencias")
    suspend fun recordBulkAttendance(
        @Path("id") id: Long,
        @Body request: RecordBulkAttendanceRequestDto
    ): ActividadDto

    @PATCH("v1/actividades/{id}/iniciar")
    suspend fun startActividad(
        @Path("id") id: Long
    ): ActividadDto

    @PATCH("v1/actividades/{id}/finalizar")
    suspend fun completeActividad(
        @Path("id") id: Long
    ): ActividadDto
}
