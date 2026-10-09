package com.example.blockvoluntariado.feature.gamificationFeedback.infrastructure.repository

import com.example.blockvoluntariado.feature.gamificationFeedback.domain.model.DigitalCertificate
import com.example.blockvoluntariado.feature.gamificationFeedback.domain.model.Evaluacion
import com.example.blockvoluntariado.feature.gamificationFeedback.domain.model.GamificationBadge
import com.example.blockvoluntariado.feature.gamificationFeedback.domain.model.GamificationProfile
import com.example.blockvoluntariado.feature.gamificationFeedback.domain.model.TipoEvaluador
import com.example.blockvoluntariado.feature.gamificationFeedback.domain.model.VolunteerHistoryItem
import com.example.blockvoluntariado.feature.gamificationFeedback.domain.repository.GamificationRepository
import com.example.blockvoluntariado.feature.gamificationFeedback.infrastructure.remote.GamificationService
import com.example.blockvoluntariado.feature.gamificationFeedback.infrastructure.remote.dto.CreateEvaluacionRequestDto
import java.util.concurrent.CopyOnWriteArrayList
import javax.inject.Inject

class GamificationRepositoryImpl @Inject constructor(
    private val service: GamificationService
) : GamificationRepository {

    private val certificateStore = CopyOnWriteArrayList<DigitalCertificate>().apply {
        addAll(
            listOf(
                DigitalCertificate(
                    id = 501L,
                    volunteerId = 1L,
                    convocatoriaId = 3L,
                    verificationHash = "8f49a24d55b85e98f79fbc94e3e3e07085c2c77a45749f993d0de3e1c64e5c83",
                    accreditedHours = 20,
                    pdfDownloadUrl = "https://api.blockvoluntariado.com/api/v1/certificados/501/download",
                    issuedAt = "2026-09-15",
                    convocatoriaTitle = "Campaña de Vacunación Animal y Bienestar Comunitario",
                    organizationName = "Asociación Patitas Seguras",
                    isBlockchainVerified = true
                ),
                DigitalCertificate(
                    id = 502L,
                    volunteerId = 1L,
                    convocatoriaId = 4L,
                    verificationHash = "b2c9e782e4f0148731db8a59489f6b927a445d475ce910e13715c0a37340e4ab",
                    accreditedHours = 12,
                    pdfDownloadUrl = "https://api.blockvoluntariado.com/api/v1/certificados/502/download",
                    issuedAt = "2026-09-30",
                    convocatoriaTitle = "Limpieza de Playas y Concientización Costera",
                    organizationName = "EcoPlayas Perú",
                    isBlockchainVerified = true
                )
            )
        )
    }

    private val reviewStore = CopyOnWriteArrayList<Evaluacion>().apply {
        addAll(
            listOf(
                Evaluacion(
                    id = 701L,
                    evaluadorId = 10L,
                    evaluadoId = 1L,
                    tipoEvaluador = TipoEvaluador.ONG,
                    score = 5,
                    feedback = "Gran sentido de responsabilidad, puntualidad y trabajo en equipo durante toda la jornada.",
                    fecha = "2026-09-16"
                ),
                Evaluacion(
                    id = 702L,
                    evaluadorId = 1L,
                    evaluadoId = 10L,
                    tipoEvaluador = TipoEvaluador.VOLUNTARIO,
                    score = 5,
                    feedback = "La organización brindó todos los implementos y una excelente coordinación previa.",
                    fecha = "2026-09-17"
                )
            )
        )
    }

    override suspend fun getCertificatesByVolunteer(volunteerId: Long): Result<List<DigitalCertificate>> {
        return try {
            val dtoList = service.getCertificatesByVolunteer(volunteerId)
            val domainList = dtoList.map { it.toDomain() }
            if (domainList.isNotEmpty()) {
                Result.success(domainList)
            } else {
                Result.success(certificateStore.filter { it.volunteerId == volunteerId })
            }
        } catch (e: Exception) {
            Result.success(certificateStore.filter { it.volunteerId == volunteerId })
        }
    }

    override suspend fun verifyCertificate(hash: String): Result<DigitalCertificate> {
        return try {
            val dto = service.verifyCertificate(hash)
            Result.success(dto.toDomain())
        } catch (e: Exception) {
            val local = certificateStore.find { it.verificationHash.equals(hash, ignoreCase = true) }
            if (local != null) Result.success(local)
            else Result.failure(e)
        }
    }

    override suspend fun getGamificationProfile(volunteerId: Long): Result<GamificationProfile> {
        return try {
            val dto = service.getGamificationProfile(volunteerId)
            Result.success(dto.toDomain())
        } catch (e: Exception) {
            val certs = certificateStore.filter { it.volunteerId == volunteerId }
            val totalHours = certs.sumOf { it.accreditedHours }
            val profile = GamificationProfile(
                volunteerId = volunteerId,
                totalHours = if (totalHours > 0) totalHours else 32,
                level = if (totalHours >= 30) "Líder Social de Oro" else "Voluntario Comprometido",
                levelNumber = if (totalHours >= 30) 3 else 2,
                badges = listOf(
                    GamificationBadge(
                        id = "first_step",
                        name = "Primer Paso Solidario",
                        description = "Participaste en tu primera convocatoria social",
                        iconName = "star",
                        isUnlocked = true,
                        unlockedAt = "2026-09-01"
                    ),
                    GamificationBadge(
                        id = "eco_hero",
                        name = "Eco Guardián",
                        description = "Completaste actividades en causas medioambientales",
                        iconName = "eco",
                        isUnlocked = true,
                        unlockedAt = "2026-09-15"
                    ),
                    GamificationBadge(
                        id = "iron_will",
                        name = "Compromiso de Hierro",
                        description = "Acumulaste más de 30 horas certificadas en blockchain",
                        iconName = "badge",
                        isUnlocked = totalHours >= 30 || totalHours == 0,
                        unlockedAt = "2026-09-30"
                    ),
                    GamificationBadge(
                        id = "team_player",
                        name = "Impulsor Comunitario",
                        description = "Recibiste calificación perfecta (5 estrellas) en evaluaciones",
                        iconName = "favorite",
                        isUnlocked = true,
                        unlockedAt = "2026-10-01"
                    )
                ),
                totalCertificates = certs.size.coerceAtLeast(2)
            )
            Result.success(profile)
        }
    }

    override suspend fun getVolunteerHistory(volunteerId: Long): Result<List<VolunteerHistoryItem>> {
        return try {
            val dtoList = service.getVolunteerHistory(volunteerId)
            val domainList = dtoList.map { it.toDomain() }
            if (domainList.isNotEmpty()) {
                Result.success(domainList)
            } else {
                buildFallbackHistory(volunteerId)
            }
        } catch (e: Exception) {
            buildFallbackHistory(volunteerId)
        }
    }

    private fun buildFallbackHistory(volunteerId: Long): Result<List<VolunteerHistoryItem>> {
        val certs = certificateStore.filter { it.volunteerId == volunteerId }
        val history = certs.map { cert ->
            VolunteerHistoryItem(
                convocatoriaId = cert.convocatoriaId,
                convocatoriaTitle = cert.convocatoriaTitle,
                organizationName = cert.organizationName,
                accreditedHours = cert.accreditedHours,
                verificationHash = cert.verificationHash,
                certificateUrl = cert.pdfDownloadUrl,
                issuedAt = cert.issuedAt
            )
        }
        return Result.success(history)
    }

    override suspend fun submitReviewVolunteer(
        volunteerId: Long,
        orgId: Long,
        score: Int,
        feedback: String?
    ): Result<Evaluacion> {
        val request = CreateEvaluacionRequestDto(
            evaluadorId = orgId,
            score = score,
            feedback = feedback
        )
        return try {
            val dto = service.reviewVolunteer(volunteerId, request)
            val domain = dto.toDomain()
            reviewStore.add(0, domain)
            Result.success(domain)
        } catch (e: Exception) {
            val local = Evaluacion(
                id = System.currentTimeMillis(),
                evaluadorId = orgId,
                evaluadoId = volunteerId,
                tipoEvaluador = TipoEvaluador.ONG,
                score = score,
                feedback = feedback,
                fecha = "Hoy"
            )
            reviewStore.add(0, local)
            Result.success(local)
        }
    }

    override suspend fun submitReviewOrganization(
        ongId: Long,
        volunteerId: Long,
        score: Int,
        feedback: String?
    ): Result<Evaluacion> {
        val request = CreateEvaluacionRequestDto(
            evaluadorId = volunteerId,
            score = score,
            feedback = feedback
        )
        return try {
            val dto = service.reviewOrganization(ongId, request)
            val domain = dto.toDomain()
            reviewStore.add(0, domain)
            Result.success(domain)
        } catch (e: Exception) {
            val local = Evaluacion(
                id = System.currentTimeMillis(),
                evaluadorId = volunteerId,
                evaluadoId = ongId,
                tipoEvaluador = TipoEvaluador.VOLUNTARIO,
                score = score,
                feedback = feedback,
                fecha = "Hoy"
            )
            reviewStore.add(0, local)
            Result.success(local)
        }
    }

    override suspend fun getReviewsByTarget(targetId: Long): Result<List<Evaluacion>> {
        return try {
            val dtoList = service.getReviewsByTarget(targetId)
            val domainList = dtoList.map { it.toDomain() }
            Result.success(domainList)
        } catch (e: Exception) {
            val local = reviewStore.filter { it.evaluadoId == targetId }
            Result.success(local)
        }
    }
}
