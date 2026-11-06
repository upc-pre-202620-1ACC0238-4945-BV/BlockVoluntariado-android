package com.example.blockvoluntariado.feature.participationTracking.presentation.attendance

import com.example.blockvoluntariado.feature.participationTracking.domain.model.Actividad
import com.example.blockvoluntariado.feature.participationTracking.domain.model.AsistenciaParticipante

data class AsistenciaUiState(
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val actividad: Actividad? = null,
    val participants: List<AsistenciaParticipante> = emptyList(),
    val feedbackMessage: String? = null,
    val isActivityCompleted: Boolean = false
)
