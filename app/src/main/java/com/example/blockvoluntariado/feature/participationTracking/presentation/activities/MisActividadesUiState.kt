package com.example.blockvoluntariado.feature.participationTracking.presentation.activities

import com.example.blockvoluntariado.feature.participationTracking.domain.model.Actividad
import com.example.blockvoluntariado.feature.participationTracking.domain.model.EstadoActividad

data class MisActividadesUiState(
    val isLoading: Boolean = false,
    val actividades: List<Actividad> = emptyList(),
    val selectedFilter: EstadoActividad? = null,
    val errorMessage: String? = null,
    val successMessage: String? = null
)
