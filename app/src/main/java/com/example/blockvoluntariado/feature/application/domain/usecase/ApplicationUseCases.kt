package com.example.blockvoluntariado.feature.application.domain.usecase

import com.example.blockvoluntariado.feature.application.domain.model.Postulacion
import com.example.blockvoluntariado.feature.application.domain.repository.ApplicationRepository
import javax.inject.Inject

class ApplyToConvocatoriaUseCase @Inject constructor(
    private val repository: ApplicationRepository
) {
    suspend operator fun invoke(convocatoriaId: Long, volunteerId: Long, motivation: String? = null): Result<Postulacion> {
        return repository.applyToConvocatoria(convocatoriaId, volunteerId, motivation)
    }
}

class GetMyApplicationsUseCase @Inject constructor(
    private val repository: ApplicationRepository
) {
    suspend operator fun invoke(volunteerId: Long): Result<List<Postulacion>> {
        return repository.getMyApplications(volunteerId)
    }
}

class GetApplicantsByConvocatoriaUseCase @Inject constructor(
    private val repository: ApplicationRepository
) {
    suspend operator fun invoke(convocatoriaId: Long): Result<List<Postulacion>> {
        return repository.getApplicantsByConvocatoria(convocatoriaId)
    }
}

class AcceptApplicationUseCase @Inject constructor(
    private val repository: ApplicationRepository
) {
    suspend operator fun invoke(postulacionId: Long): Result<Postulacion> {
        return repository.acceptApplication(postulacionId)
    }
}

class RejectApplicationUseCase @Inject constructor(
    private val repository: ApplicationRepository
) {
    suspend operator fun invoke(postulacionId: Long, reason: String? = null): Result<Postulacion> {
        return repository.rejectApplication(postulacionId, reason)
    }
}

class CancelApplicationUseCase @Inject constructor(
    private val repository: ApplicationRepository
) {
    suspend operator fun invoke(postulacionId: Long): Result<Postulacion> {
        return repository.cancelApplication(postulacionId)
    }
}
