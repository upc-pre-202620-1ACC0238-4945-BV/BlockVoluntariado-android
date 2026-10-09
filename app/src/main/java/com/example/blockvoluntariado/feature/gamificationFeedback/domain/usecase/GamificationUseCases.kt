package com.example.blockvoluntariado.feature.gamificationFeedback.domain.usecase

import com.example.blockvoluntariado.feature.gamificationFeedback.domain.model.DigitalCertificate
import com.example.blockvoluntariado.feature.gamificationFeedback.domain.model.Evaluacion
import com.example.blockvoluntariado.feature.gamificationFeedback.domain.model.GamificationProfile
import com.example.blockvoluntariado.feature.gamificationFeedback.domain.model.VolunteerHistoryItem
import com.example.blockvoluntariado.feature.gamificationFeedback.domain.repository.GamificationRepository
import javax.inject.Inject

class GetCertificatesByVolunteerUseCase @Inject constructor(
    private val repository: GamificationRepository
) {
    suspend operator fun invoke(volunteerId: Long): Result<List<DigitalCertificate>> {
        return repository.getCertificatesByVolunteer(volunteerId)
    }
}

class VerifyCertificateUseCase @Inject constructor(
    private val repository: GamificationRepository
) {
    suspend operator fun invoke(hash: String): Result<DigitalCertificate> {
        return repository.verifyCertificate(hash)
    }
}

class GetGamificationProfileUseCase @Inject constructor(
    private val repository: GamificationRepository
) {
    suspend operator fun invoke(volunteerId: Long): Result<GamificationProfile> {
        return repository.getGamificationProfile(volunteerId)
    }
}

class GetVolunteerHistoryUseCase @Inject constructor(
    private val repository: GamificationRepository
) {
    suspend operator fun invoke(volunteerId: Long): Result<List<VolunteerHistoryItem>> {
        return repository.getVolunteerHistory(volunteerId)
    }
}

class SubmitReviewOrganizationUseCase @Inject constructor(
    private val repository: GamificationRepository
) {
    suspend operator fun invoke(
        ongId: Long,
        volunteerId: Long,
        score: Int,
        feedback: String?
    ): Result<Evaluacion> {
        return repository.submitReviewOrganization(ongId, volunteerId, score, feedback)
    }
}

class SubmitReviewVolunteerUseCase @Inject constructor(
    private val repository: GamificationRepository
) {
    suspend operator fun invoke(
        volunteerId: Long,
        orgId: Long,
        score: Int,
        feedback: String?
    ): Result<Evaluacion> {
        return repository.submitReviewVolunteer(volunteerId, orgId, score, feedback)
    }
}

class GetReviewsByTargetUseCase @Inject constructor(
    private val repository: GamificationRepository
) {
    suspend operator fun invoke(targetId: Long): Result<List<Evaluacion>> {
        return repository.getReviewsByTarget(targetId)
    }
}
