package com.example.blockvoluntariado.feature.discoveryVolunteering.presentation

import com.example.blockvoluntariado.feature.discoveryVolunteering.domain.Convocatoria

data class HomeUiState(

    val convocatorias: List<Convocatoria> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null

)
