package com.example.blockvoluntariado.feature.gamificationFeedback.presentation.recognition

import com.example.blockvoluntariado.feature.gamificationFeedback.domain.model.DigitalCertificate
import com.example.blockvoluntariado.feature.gamificationFeedback.domain.model.GamificationProfile
import com.example.blockvoluntariado.feature.gamificationFeedback.domain.model.VolunteerHistoryItem

data class LogrosCertificadosUiState(
    val isLoading: Boolean = false,
    val profile: GamificationProfile? = null,
    val certificates: List<DigitalCertificate> = emptyList(),
    val historyItems: List<VolunteerHistoryItem> = emptyList(),
    val selectedCertificate: DigitalCertificate? = null,
    val isVerifying: Boolean = false,
    val verificationInput: String = "",
    val verifiedCertificate: DigitalCertificate? = null,
    val verificationError: String? = null,
    val isReviewDialogVisible: Boolean = false,
    val reviewTargetId: Long? = null,
    val reviewTargetName: String = "",
    val isReviewTargetOrg: Boolean = true,
    val statusMessage: String? = null
)
