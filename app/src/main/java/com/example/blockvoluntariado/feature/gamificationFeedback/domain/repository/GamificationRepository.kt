package com.example.blockvoluntariado.feature.gamificationFeedback.domain.repository

import com.example.blockvoluntariado.feature.gamificationFeedback.domain.model.DigitalCertificate
import com.example.blockvoluntariado.feature.gamificationFeedback.domain.model.Evaluacion
import com.example.blockvoluntariado.feature.gamificationFeedback.domain.model.GamificationProfile
import com.example.blockvoluntariado.feature.gamificationFeedback.domain.model.VolunteerHistoryItem

interface GamificationRepository {
    suspend fun getCertificatesByVolunteer(volunteerId: Long): Result<List<DigitalCertificate>>
    suspend fun verifyCertificate(hash: String): Result<DigitalCertificate>
    suspend fun getGamificationProfile(volunteerId: Long): Result<GamificationProfile>
    suspend fun getVolunteerHistory(volunteerId: Long): Result<List<VolunteerHistoryItem>>
    suspend fun submitReviewVolunteer(volunteerId: Long, orgId: Long, score: Int, feedback: String?): Result<Evaluacion>
    suspend fun submitReviewOrganization(ongId: Long, volunteerId: Long, score: Int, feedback: String?): Result<Evaluacion>
    suspend fun getReviewsByTarget(targetId: Long): Result<List<Evaluacion>>
}
