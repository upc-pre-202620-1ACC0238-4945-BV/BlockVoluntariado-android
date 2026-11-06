package com.example.blockvoluntariado.feature.application.infrastructure.repository

import com.example.blockvoluntariado.feature.application.domain.model.EstadoPostulacion
import com.example.blockvoluntariado.feature.application.domain.model.Postulacion
import com.example.blockvoluntariado.feature.application.domain.repository.ApplicationRepository
import com.example.blockvoluntariado.feature.application.infrastructure.remote.ApplicationService
import com.example.blockvoluntariado.feature.application.infrastructure.remote.dto.CreatePostulacionRequestDto
import com.example.blockvoluntariado.feature.application.infrastructure.remote.dto.RejectPostulacionRequestDto
import java.util.concurrent.CopyOnWriteArrayList
import javax.inject.Inject

class ApplicationRepositoryImpl @Inject constructor(
    private val service: ApplicationService
) : ApplicationRepository {

    // In-memory backing store for demo/offline and instant UI reactivity
    private val memoryStore = CopyOnWriteArrayList<Postulacion>().apply {
        addAll(
            listOf(
                Postulacion(
                    id = 101L,
                    convocatoriaId = 1L,
                    volunteerId = 1L,
                    status = EstadoPostulacion.ACEPTADA,
                    appliedAt = "2026-10-02",
                    convocatoriaTitle = "Apoyo Escolar en San Juan de Lurigancho",
                    volunteerName = "Sebastian Tavara"
                ),
                Postulacion(
                    id = 102L,
                    convocatoriaId = 2L,
                    volunteerId = 1L,
                    status = EstadoPostulacion.PENDIENTE,
                    appliedAt = "2026-10-05",
                    convocatoriaTitle = "Reforestación de Lomas de Amancaes",
                    volunteerName = "Sebastian Tavara"
                ),
                Postulacion(
                    id = 103L,
                    convocatoriaId = 3L,
                    volunteerId = 1L,
                    status = EstadoPostulacion.RECHAZADA,
                    rejectionReason = "Cupos completos para la jornada",
                    appliedAt = "2026-09-28",
                    convocatoriaTitle = "Campaña de Vacunación Animal",
                    volunteerName = "Sebastian Tavara"
                )
            )
        )
    }

    override suspend fun applyToConvocatoria(
        convocatoriaId: Long,
        volunteerId: Long,
        motivation: String?
    ): Result<Postulacion> {
        return try {
            val response = service.applyToConvocatoria(
                convocatoriaId = convocatoriaId,
                request = CreatePostulacionRequestDto(volunteerId = volunteerId)
            )
            if (response.isSuccessful && response.body() != null) {
                val post = response.body()!!.toDomain()
                memoryStore.add(0, post)
                Result.success(post)
            } else {
                // Fallback store
                val newPost = Postulacion(
                    id = System.currentTimeMillis(),
                    convocatoriaId = convocatoriaId,
                    volunteerId = volunteerId,
                    status = EstadoPostulacion.PENDIENTE,
                    appliedAt = "Hoy",
                    convocatoriaTitle = "Convocatoria #$convocatoriaId",
                    volunteerName = "Mi Perfil"
                )
                memoryStore.add(0, newPost)
                Result.success(newPost)
            }
        } catch (e: Exception) {
            val newPost = Postulacion(
                id = System.currentTimeMillis(),
                convocatoriaId = convocatoriaId,
                volunteerId = volunteerId,
                status = EstadoPostulacion.PENDIENTE,
                appliedAt = "Hoy",
                convocatoriaTitle = "Convocatoria #$convocatoriaId",
                volunteerName = "Mi Perfil"
            )
            memoryStore.add(0, newPost)
            Result.success(newPost)
        }
    }

    override suspend fun getMyApplications(volunteerId: Long): Result<List<Postulacion>> {
        return try {
            val response = service.getPostulacionesByVolunteer(volunteerId)
            if (response.isSuccessful && response.body() != null) {
                val apiList = response.body()!!.map { it.toDomain() }
                // Merge api list with any new memory items
                val combined = (apiList + memoryStore).distinctBy { it.id }
                Result.success(combined)
            } else {
                Result.success(memoryStore.toList())
            }
        } catch (e: Exception) {
            Result.success(memoryStore.toList())
        }
    }

    override suspend fun getApplicantsByConvocatoria(convocatoriaId: Long): Result<List<Postulacion>> {
        return try {
            val response = service.getPostulantesByConvocatoria(convocatoriaId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.map { it.toDomain() })
            } else {
                val filtered = memoryStore.filter { it.convocatoriaId == convocatoriaId }
                Result.success(filtered)
            }
        } catch (e: Exception) {
            val filtered = memoryStore.filter { it.convocatoriaId == convocatoriaId }
            Result.success(filtered)
        }
    }

    override suspend fun acceptApplication(postulacionId: Long): Result<Postulacion> {
        return try {
            val response = service.acceptPostulacion(postulacionId)
            if (response.isSuccessful && response.body() != null) {
                val domain = response.body()!!.toDomain()
                updateMemoryStatus(postulacionId, EstadoPostulacion.ACEPTADA)
                Result.success(domain)
            } else {
                val updated = updateMemoryStatus(postulacionId, EstadoPostulacion.ACEPTADA)
                Result.success(updated ?: Postulacion(postulacionId, 1L, 1L, EstadoPostulacion.ACEPTADA))
            }
        } catch (e: Exception) {
            val updated = updateMemoryStatus(postulacionId, EstadoPostulacion.ACEPTADA)
            Result.success(updated ?: Postulacion(postulacionId, 1L, 1L, EstadoPostulacion.ACEPTADA))
        }
    }

    override suspend fun rejectApplication(postulacionId: Long, reason: String?): Result<Postulacion> {
        return try {
            val response = service.rejectPostulacion(
                postulacionId = postulacionId,
                request = RejectPostulacionRequestDto(reason = reason)
            )
            if (response.isSuccessful && response.body() != null) {
                val domain = response.body()!!.toDomain()
                updateMemoryStatus(postulacionId, EstadoPostulacion.RECHAZADA, reason)
                Result.success(domain)
            } else {
                val updated = updateMemoryStatus(postulacionId, EstadoPostulacion.RECHAZADA, reason)
                Result.success(updated ?: Postulacion(postulacionId, 1L, 1L, EstadoPostulacion.RECHAZADA, rejectionReason = reason))
            }
        } catch (e: Exception) {
            val updated = updateMemoryStatus(postulacionId, EstadoPostulacion.RECHAZADA, reason)
            Result.success(updated ?: Postulacion(postulacionId, 1L, 1L, EstadoPostulacion.RECHAZADA, rejectionReason = reason))
        }
    }

    override suspend fun cancelApplication(postulacionId: Long): Result<Postulacion> {
        return try {
            val response = service.cancelPostulacion(postulacionId)
            if (response.isSuccessful && response.body() != null) {
                val domain = response.body()!!.toDomain()
                updateMemoryStatus(postulacionId, EstadoPostulacion.CANCELADA)
                Result.success(domain)
            } else {
                val updated = updateMemoryStatus(postulacionId, EstadoPostulacion.CANCELADA)
                Result.success(updated ?: Postulacion(postulacionId, 1L, 1L, EstadoPostulacion.CANCELADA))
            }
        } catch (e: Exception) {
            val updated = updateMemoryStatus(postulacionId, EstadoPostulacion.CANCELADA)
            Result.success(updated ?: Postulacion(postulacionId, 1L, 1L, EstadoPostulacion.CANCELADA))
        }
    }

    private fun updateMemoryStatus(
        id: Long,
        newStatus: EstadoPostulacion,
        reason: String? = null
    ): Postulacion? {
        val index = memoryStore.indexOfFirst { it.id == id }
        if (index != -1) {
            val updated = memoryStore[index].copy(
                status = newStatus,
                rejectionReason = reason ?: memoryStore[index].rejectionReason
            )
            memoryStore[index] = updated
            return updated
        }
        return null
    }
}
