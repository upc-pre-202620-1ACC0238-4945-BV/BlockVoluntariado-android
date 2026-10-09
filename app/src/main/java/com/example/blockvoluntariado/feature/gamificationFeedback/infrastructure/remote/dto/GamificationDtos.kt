package com.example.blockvoluntariado.feature.gamificationFeedback.infrastructure.remote.dto

import com.example.blockvoluntariado.feature.gamificationFeedback.domain.model.DigitalCertificate
import com.example.blockvoluntariado.feature.gamificationFeedback.domain.model.Evaluacion
import com.example.blockvoluntariado.feature.gamificationFeedback.domain.model.GamificationBadge
import com.example.blockvoluntariado.feature.gamificationFeedback.domain.model.GamificationProfile
import com.example.blockvoluntariado.feature.gamificationFeedback.domain.model.TipoEvaluador
import com.example.blockvoluntariado.feature.gamificationFeedback.domain.model.VolunteerHistoryItem
import com.google.gson.annotations.SerializedName

data class DigitalCertificateDto(
    @SerializedName("id") val id: Long,
    @SerializedName("volunteerId") val volunteerId: Long,
    @SerializedName("convocatoriaId") val convocatoriaId: Long,
    @SerializedName("verificationHash") val verificationHash: String,
    @SerializedName("accreditedHours") val accreditedHours: Int,
    @SerializedName("pdfDownloadUrl") val pdfDownloadUrl: String? = null,
    @SerializedName("issuedAt") val issuedAt: String? = null
) {
    fun toDomain(): DigitalCertificate {
        return DigitalCertificate(
            id = id,
            volunteerId = volunteerId,
            convocatoriaId = convocatoriaId,
            verificationHash = verificationHash,
            accreditedHours = accreditedHours,
            pdfDownloadUrl = pdfDownloadUrl,
            issuedAt = issuedAt ?: "2026-10-01",
            convocatoriaTitle = "Voluntariado Social Universitario #$convocatoriaId",
            organizationName = "Alianza BlockVoluntariado",
            isBlockchainVerified = verificationHash.isNotBlank()
        )
    }
}

data class GamificationBadgeDto(
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("iconName") val iconName: String? = "badge",
    @SerializedName("isUnlocked") val isUnlocked: Boolean = true,
    @SerializedName("unlockedAt") val unlockedAt: String? = null
) {
    fun toDomain(): GamificationBadge {
        return GamificationBadge(
            id = id ?: name.lowercase().replace(" ", "_"),
            name = name,
            description = description,
            iconName = iconName ?: "badge",
            isUnlocked = isUnlocked,
            unlockedAt = unlockedAt
        )
    }
}

data class GamificationProfileDto(
    @SerializedName("volunteerId") val volunteerId: Long,
    @SerializedName("totalHours") val totalHours: Int = 0,
    @SerializedName("level") val level: String = "Voluntario Activo",
    @SerializedName("levelNumber") val levelNumber: Int = 1,
    @SerializedName("badges") val badges: List<GamificationBadgeDto>? = emptyList(),
    @SerializedName("totalCertificates") val totalCertificates: Int = 0
) {
    fun toDomain(): GamificationProfile {
        return GamificationProfile(
            volunteerId = volunteerId,
            totalHours = totalHours,
            level = level,
            levelNumber = levelNumber,
            badges = badges?.map { it.toDomain() } ?: emptyList(),
            totalCertificates = totalCertificates
        )
    }
}

data class VolunteerHistoryItemDto(
    @SerializedName("convocatoriaId") val convocatoriaId: Long,
    @SerializedName("convocatoriaTitle") val convocatoriaTitle: String = "",
    @SerializedName("organizationName") val organizationName: String = "",
    @SerializedName("accreditedHours") val accreditedHours: Int = 0,
    @SerializedName("verificationHash") val verificationHash: String = "",
    @SerializedName("certificateUrl") val certificateUrl: String? = null,
    @SerializedName("issuedAt") val issuedAt: String? = null
) {
    fun toDomain(): VolunteerHistoryItem {
        return VolunteerHistoryItem(
            convocatoriaId = convocatoriaId,
            convocatoriaTitle = convocatoriaTitle,
            organizationName = organizationName,
            accreditedHours = accreditedHours,
            verificationHash = verificationHash,
            certificateUrl = certificateUrl,
            issuedAt = issuedAt ?: "2026-10-01"
        )
    }
}

data class EvaluacionDto(
    @SerializedName("id") val id: Long,
    @SerializedName("evaluadorId") val evaluadorId: Long,
    @SerializedName("evaluadoId") val evaluadoId: Long,
    @SerializedName("tipoEvaluador") val tipoEvaluador: String = "VOLUNTARIO",
    @SerializedName("score") val score: Int = 5,
    @SerializedName("feedback") val feedback: String? = null,
    @SerializedName("fecha") val fecha: String? = null
) {
    fun toDomain(): Evaluacion {
        val tipo = if (tipoEvaluador.equals("ONG", ignoreCase = true)) TipoEvaluador.ONG else TipoEvaluador.VOLUNTARIO
        return Evaluacion(
            id = id,
            evaluadorId = evaluadorId,
            evaluadoId = evaluadoId,
            tipoEvaluador = tipo,
            score = score,
            feedback = feedback,
            fecha = fecha ?: "Hoy"
        )
    }
}

data class CreateEvaluacionRequestDto(
    @SerializedName("evaluadorId") val evaluadorId: Long?,
    @SerializedName("score") val score: Int,
    @SerializedName("feedback") val feedback: String?
)
