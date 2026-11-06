package com.example.blockvoluntariado.feature.participationTracking.infrastructure.repository

import com.example.blockvoluntariado.feature.participationTracking.domain.model.Actividad
import com.example.blockvoluntariado.feature.participationTracking.domain.model.AsistenciaParticipante
import com.example.blockvoluntariado.feature.participationTracking.domain.model.EstadoActividad
import com.example.blockvoluntariado.feature.participationTracking.domain.repository.ParticipationRepository
import com.example.blockvoluntariado.feature.participationTracking.infrastructure.remote.ParticipationService
import com.example.blockvoluntariado.feature.participationTracking.infrastructure.remote.dto.RecordAttendanceItemDto
import com.example.blockvoluntariado.feature.participationTracking.infrastructure.remote.dto.RecordBulkAttendanceRequestDto
import java.util.concurrent.CopyOnWriteArrayList
import javax.inject.Inject

class ParticipationRepositoryImpl @Inject constructor(
    private val service: ParticipationService
) : ParticipationRepository {

    private val memoryStore = CopyOnWriteArrayList<Actividad>().apply {
        addAll(
            listOf(
                Actividad(
                    id = 1L,
                    convocatoriaId = 1L,
                    titulo = "Jornada de Refuerzo Matemático - Primaria",
                    status = EstadoActividad.PLANIFICADA,
                    fechaActividad = "2026-10-15",
                    lugar = "Colegio Fe y Alegría 32, San Juan de Lurigancho",
                    asistencias = listOf(
                        AsistenciaParticipante(
                            id = 201L,
                            actividadId = 1L,
                            postulacionId = 101L,
                            volunteerId = 1L,
                            volunteerName = "Sebastian Tavara",
                            isPresent = false,
                            certifiedHours = 4
                        ),
                        AsistenciaParticipante(
                            id = 202L,
                            actividadId = 1L,
                            postulacionId = 104L,
                            volunteerId = 2L,
                            volunteerName = "Diego Cabrejos",
                            isPresent = false,
                            certifiedHours = 4
                        ),
                        AsistenciaParticipante(
                            id = 203L,
                            actividadId = 1L,
                            postulacionId = 105L,
                            volunteerId = 3L,
                            volunteerName = "Ghorghet Tuncar",
                            isPresent = false,
                            certifiedHours = 4
                        )
                    )
                ),
                Actividad(
                    id = 2L,
                    convocatoriaId = 2L,
                    titulo = "Reforestación y Mantenimiento Ecológico",
                    status = EstadoActividad.EN_CURSO,
                    fechaActividad = "2026-10-18",
                    lugar = "Lomas de Amancaes, Rímac",
                    asistencias = listOf(
                        AsistenciaParticipante(
                            id = 204L,
                            actividadId = 2L,
                            postulacionId = 102L,
                            volunteerId = 1L,
                            volunteerName = "Sebastian Tavara",
                            isPresent = true,
                            certifiedHours = 5,
                            checkInTime = "08:30 AM",
                            supervisorNotes = "Puntual en punto de encuentro"
                        ),
                        AsistenciaParticipante(
                            id = 205L,
                            actividadId = 2L,
                            postulacionId = 106L,
                            volunteerId = 2L,
                            volunteerName = "Diego Cabrejos",
                            isPresent = true,
                            certifiedHours = 5,
                            checkInTime = "08:35 AM"
                        )
                    )
                ),
                Actividad(
                    id = 3L,
                    convocatoriaId = 4L,
                    titulo = "Limpieza de Playas y Concientización Costera",
                    status = EstadoActividad.COMPLETADA,
                    fechaActividad = "2026-09-30",
                    lugar = "Playa Carpayo, Callao",
                    asistencias = listOf(
                        AsistenciaParticipante(
                            id = 206L,
                            actividadId = 3L,
                            postulacionId = 107L,
                            volunteerId = 1L,
                            volunteerName = "Sebastian Tavara",
                            isPresent = true,
                            certifiedHours = 6,
                            checkInTime = "07:00 AM",
                            supervisorNotes = "Excelente liderazgo en equipo de campo"
                        )
                    )
                )
            )
        )
    }

    override suspend fun getActividadById(id: Long): Result<Actividad> {
        return try {
            val dto = service.getActividadById(id)
            val domain = dto.toDomain()
            updateMemoryActividad(domain)
            Result.success(domain)
        } catch (e: Exception) {
            val local = memoryStore.find { it.id == id }
            if (local != null) Result.success(local)
            else Result.failure(e)
        }
    }

    override suspend fun getActividadByConvocatoria(convocatoriaId: Long): Result<Actividad> {
        return try {
            val dto = service.getActividadByConvocatoria(convocatoriaId)
            val domain = dto.toDomain()
            updateMemoryActividad(domain)
            Result.success(domain)
        } catch (e: Exception) {
            val local = memoryStore.find { it.convocatoriaId == convocatoriaId }
            if (local != null) Result.success(local)
            else Result.failure(e)
        }
    }

    override suspend fun getMyScheduledActivities(volunteerId: Long): Result<List<Actividad>> {
        // Return activities where volunteer is registered or default available activities
        val matched = memoryStore.filter { act ->
            act.asistencias.any { it.volunteerId == volunteerId } || act.asistencias.isEmpty()
        }
        return Result.success(if (matched.isNotEmpty()) matched else memoryStore.toList())
    }

    override suspend fun getParticipantesByActividad(actividadId: Long): Result<List<AsistenciaParticipante>> {
        return try {
            val dtos = service.getParticipantes(actividadId)
            val domainList = dtos.map { it.toDomain() }
            Result.success(domainList)
        } catch (e: Exception) {
            val act = memoryStore.find { it.id == actividadId }
            Result.success(act?.asistencias ?: emptyList())
        }
    }

    override suspend fun recordBulkAttendance(
        actividadId: Long,
        items: List<AsistenciaParticipante>
    ): Result<Actividad> {
        val request = RecordBulkAttendanceRequestDto(
            attendances = items.map {
                RecordAttendanceItemDto(
                    postulacionId = it.postulacionId,
                    volunteerId = it.volunteerId,
                    isPresent = it.isPresent,
                    certifiedHours = it.certifiedHours,
                    supervisorNotes = it.supervisorNotes
                )
            }
        )

        return try {
            val updatedDto = service.recordBulkAttendance(actividadId, request)
            val domain = updatedDto.toDomain()
            updateMemoryActividad(domain)
            Result.success(domain)
        } catch (e: Exception) {
            val index = memoryStore.indexOfFirst { it.id == actividadId }
            if (index != -1) {
                val current = memoryStore[index]
                val updated = current.copy(asistencias = items)
                memoryStore[index] = updated
                Result.success(updated)
            } else {
                Result.failure(e)
            }
        }
    }

    override suspend fun startActividad(actividadId: Long): Result<Actividad> {
        return try {
            val dto = service.startActividad(actividadId)
            val domain = dto.toDomain()
            updateMemoryActividad(domain)
            Result.success(domain)
        } catch (e: Exception) {
            val index = memoryStore.indexOfFirst { it.id == actividadId }
            if (index != -1) {
                val updated = memoryStore[index].copy(status = EstadoActividad.EN_CURSO)
                memoryStore[index] = updated
                Result.success(updated)
            } else {
                Result.failure(e)
            }
        }
    }

    override suspend fun completeActividad(actividadId: Long): Result<Actividad> {
        return try {
            val dto = service.completeActividad(actividadId)
            val domain = dto.toDomain()
            updateMemoryActividad(domain)
            Result.success(domain)
        } catch (e: Exception) {
            val index = memoryStore.indexOfFirst { it.id == actividadId }
            if (index != -1) {
                val updated = memoryStore[index].copy(status = EstadoActividad.COMPLETADA)
                memoryStore[index] = updated
                Result.success(updated)
            } else {
                Result.failure(e)
            }
        }
    }

    private fun updateMemoryActividad(actividad: Actividad) {
        val index = memoryStore.indexOfFirst { it.id == actividad.id }
        if (index != -1) {
            memoryStore[index] = actividad
        } else {
            memoryStore.add(actividad)
        }
    }
}
