package com.example.blockvoluntariado.feature.application.domain.model

enum class EstadoPostulacion(val displayName: String) {
    PENDIENTE("Pendiente"),
    ACEPTADA("Aceptada"),
    RECHAZADA("Rechazada"),
    CANCELADA("Cancelada");

    companion object {
        fun fromString(status: String?): EstadoPostulacion {
            return when (status?.uppercase()) {
                "ACEPTADA", "ACCEPTED" -> ACEPTADA
                "RECHAZADA", "REJECTED" -> RECHAZADA
                "CANCELADA", "CANCELED" -> CANCELADA
                else -> PENDIENTE
            }
        }
    }
}

data class Postulacion(
    val id: Long,
    val convocatoriaId: Long,
    val volunteerId: Long,
    val status: EstadoPostulacion = EstadoPostulacion.PENDIENTE,
    val rejectionReason: String? = null,
    val appliedAt: String = "",
    val convocatoriaTitle: String = "Voluntariado Comunitario",
    val volunteerName: String = "Estudiante Voluntario"
)
