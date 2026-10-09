package com.example.blockvoluntariado.feature.gamificationFeedback.domain.model

data class GamificationBadge(
    val id: String,
    val name: String,
    val description: String,
    val iconName: String,
    val isUnlocked: Boolean = true,
    val unlockedAt: String? = null
)

data class GamificationProfile(
    val volunteerId: Long,
    val totalHours: Int,
    val level: String,
    val levelNumber: Int,
    val badges: List<GamificationBadge>,
    val totalCertificates: Int
)

data class VolunteerHistoryItem(
    val convocatoriaId: Long,
    val convocatoriaTitle: String,
    val organizationName: String,
    val accreditedHours: Int,
    val verificationHash: String,
    val certificateUrl: String?,
    val issuedAt: String
)
