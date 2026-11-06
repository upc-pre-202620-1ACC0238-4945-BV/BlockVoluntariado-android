package com.example.blockvoluntariado.feature.participationTracking.domain.usecase

import com.example.blockvoluntariado.feature.participationTracking.domain.model.Actividad
import com.example.blockvoluntariado.feature.participationTracking.domain.model.AsistenciaParticipante
import com.example.blockvoluntariado.feature.participationTracking.domain.repository.ParticipationRepository
import javax.inject.Inject

class GetMyScheduledActivitiesUseCase @Inject constructor(
    private val repository: ParticipationRepository
) {
    suspend operator fun invoke(volunteerId: Long): Result<List<Actividad>> {
        return repository.getMyScheduledActivities(volunteerId)
    }
}

class GetActividadDetailUseCase @Inject constructor(
    private val repository: ParticipationRepository
) {
    suspend operator fun invoke(actividadId: Long): Result<Actividad> {
        return repository.getActividadById(actividadId)
    }
}

class GetParticipantesActividadUseCase @Inject constructor(
    private val repository: ParticipationRepository
) {
    suspend operator fun invoke(actividadId: Long): Result<List<AsistenciaParticipante>> {
        return repository.getParticipantesByActividad(actividadId)
    }
}

class RecordBulkAttendanceUseCase @Inject constructor(
    private val repository: ParticipationRepository
) {
    suspend operator fun invoke(actividadId: Long, items: List<AsistenciaParticipante>): Result<Actividad> {
        return repository.recordBulkAttendance(actividadId, items)
    }
}

class StartActividadUseCase @Inject constructor(
    private val repository: ParticipationRepository
) {
    suspend operator fun invoke(actividadId: Long): Result<Actividad> {
        return repository.startActividad(actividadId)
    }
}

class CompleteActividadUseCase @Inject constructor(
    private val repository: ParticipationRepository
) {
    suspend operator fun invoke(actividadId: Long): Result<Actividad> {
        return repository.completeActividad(actividadId)
    }
}
