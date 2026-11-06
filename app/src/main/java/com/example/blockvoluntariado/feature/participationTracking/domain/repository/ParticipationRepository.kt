package com.example.blockvoluntariado.feature.participationTracking.domain.repository

import com.example.blockvoluntariado.feature.participationTracking.domain.model.Actividad
import com.example.blockvoluntariado.feature.participationTracking.domain.model.AsistenciaParticipante

interface ParticipationRepository {
    suspend fun getActividadById(id: Long): Result<Actividad>
    suspend fun getActividadByConvocatoria(convocatoriaId: Long): Result<Actividad>
    suspend fun getMyScheduledActivities(volunteerId: Long): Result<List<Actividad>>
    suspend fun getParticipantesByActividad(actividadId: Long): Result<List<AsistenciaParticipante>>
    suspend fun recordBulkAttendance(actividadId: Long, items: List<AsistenciaParticipante>): Result<Actividad>
    suspend fun startActividad(actividadId: Long): Result<Actividad>
    suspend fun completeActividad(actividadId: Long): Result<Actividad>
}
