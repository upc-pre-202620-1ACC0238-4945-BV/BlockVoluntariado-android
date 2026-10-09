package com.example.blockvoluntariado.feature.participationTracking.infrastructure.remote.dto

import com.example.blockvoluntariado.feature.participationTracking.domain.model.Actividad
import com.example.blockvoluntariado.feature.participationTracking.domain.model.AsistenciaParticipante
import com.example.blockvoluntariado.feature.participationTracking.domain.model.EstadoActividad
import com.google.gson.annotations.SerializedName

data class AttendanceRecordDto(
    @SerializedName("id") val id: Long,
    @SerializedName("actividadId") val actividadId: Long,
    @SerializedName("postulacionId") val postulacionId: Long,
    @SerializedName("volunteerId") val volunteerId: Long,
    @SerializedName("isPresent") val isPresent: Boolean = false,
    @SerializedName("certifiedHours") val certifiedHours: Int = 0,
    @SerializedName("checkInTime") val checkInTime: String? = null,
    @SerializedName("supervisorNotes") val supervisorNotes: String? = null
) {
    fun toDomain(): AsistenciaParticipante {
        return AsistenciaParticipante(
            id = id,
            actividadId = actividadId,
            postulacionId = postulacionId,
            volunteerId = volunteerId,
            volunteerName = "Voluntario #$volunteerId",
            isPresent = isPresent,
            certifiedHours = certifiedHours,
            checkInTime = checkInTime,
            supervisorNotes = supervisorNotes
        )
    }
}

data class ActividadDto(
    @SerializedName("id") val id: Long,
    @SerializedName("convocatoriaId") val convocatoriaId: Long,
    @SerializedName("titulo") val titulo: String = "",
    @SerializedName("status") val status: String? = "PLANIFICADA",
    @SerializedName("fechaActividad") val fechaActividad: String? = null,
    @SerializedName("asistencias") val asistencias: List<AttendanceRecordDto>? = emptyList()
) {
    fun toDomain(): Actividad {
        return Actividad(
            id = id,
            convocatoriaId = convocatoriaId,
            titulo = titulo,
            status = EstadoActividad.fromString(status),
            fechaActividad = fechaActividad ?: "Por coordinar",
            asistencias = asistencias?.map { it.toDomain() } ?: emptyList()
        )
    }
}

data class RecordBulkAttendanceRequestDto(
    @SerializedName("attendances") val attendances: List<RecordAttendanceItemDto>
)

data class RecordAttendanceItemDto(
    @SerializedName("postulacionId") val postulacionId: Long,
    @SerializedName("volunteerId") val volunteerId: Long,
    @SerializedName("isPresent") val isPresent: Boolean,
    @SerializedName("certifiedHours") val certifiedHours: Int?,
    @SerializedName("supervisorNotes") val supervisorNotes: String?
)
