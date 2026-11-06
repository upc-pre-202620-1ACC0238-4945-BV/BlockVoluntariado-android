package com.example.blockvoluntariado.feature.application.presentation.my_applications

import com.example.blockvoluntariado.feature.application.domain.model.EstadoPostulacion
import com.example.blockvoluntariado.feature.application.domain.model.Postulacion

data class MisPostulacionesUiState(
    val applications: List<Postulacion> = emptyList(),
    val filteredApplications: List<Postulacion> = emptyList(),
    val selectedStatus: EstadoPostulacion? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successActionMessage: String? = null
)
