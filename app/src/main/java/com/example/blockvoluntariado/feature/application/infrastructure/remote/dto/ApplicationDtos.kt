package com.example.blockvoluntariado.feature.application.infrastructure.remote.dto

import com.example.blockvoluntariado.feature.application.domain.model.EstadoPostulacion
import com.example.blockvoluntariado.feature.application.domain.model.Postulacion
import com.google.gson.annotations.SerializedName

data class PostulacionDto(
    @SerializedName("id") val id: Long,
    @SerializedName("convocatoriaId") val convocatoriaId: Long,
    @SerializedName("volunteerId") val volunteerId: Long,
    @SerializedName("status") val status: String? = "PENDIENTE",
    @SerializedName("rejectionReason") val rejectionReason: String? = null,
    @SerializedName("appliedAt") val appliedAt: String? = null
) {
    fun toDomain(): Postulacion {
        return Postulacion(
            id = id,
            convocatoriaId = convocatoriaId,
            volunteerId = volunteerId,
            status = EstadoPostulacion.fromString(status),
            rejectionReason = rejectionReason,
            appliedAt = appliedAt?.take(10) ?: "Reciente",
            convocatoriaTitle = "Convocatoria #$convocatoriaId",
            volunteerName = "Estudiante #$volunteerId"
        )
    }
}

data class CreatePostulacionRequestDto(
    @SerializedName("volunteerId") val volunteerId: Long
)

data class RejectPostulacionRequestDto(
    @SerializedName("reason") val reason: String?
)
