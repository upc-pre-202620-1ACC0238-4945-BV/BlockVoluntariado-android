package com.example.blockvoluntariado.feature.participationTracking.domain.model

enum class EstadoActividad(val displayName: String) {
    PLANIFICADA("Planificada"),
    EN_CURSO("En Curso"),
    COMPLETADA("Completada"),
    CANCELADA("Cancelada");

    companion object {
        fun fromString(status: String?): EstadoActividad {
            return when (status?.uppercase()) {
                "EN_CURSO", "IN_PROGRESS" -> EN_CURSO
                "COMPLETADA", "COMPLETED" -> COMPLETADA
                "CANCELADA", "CANCELED" -> CANCELADA
                else -> PLANIFICADA
            }
        }
    }
}

data class AsistenciaParticipante(
    val id: Long,
    val actividadId: Long,
    val postulacionId: Long,
    val volunteerId: Long,
    val volunteerName: String = "Estudiante Voluntario",
    val isPresent: Boolean = false,
    val certifiedHours: Int = 4,
    val checkInTime: String? = null,
    val supervisorNotes: String? = null
)

data class Actividad(
    val id: Long,
    val convocatoriaId: Long,
    val titulo: String,
    val status: EstadoActividad = EstadoActividad.PLANIFICADA,
    val fechaActividad: String = "2026-10-15",
    val lugar: String = "Sede Central de Voluntariado",
    val asistencias: List<AsistenciaParticipante> = emptyList()
)
