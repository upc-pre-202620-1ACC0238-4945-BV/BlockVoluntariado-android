package com.example.blockvoluntariado.feature.discoveryVolunteering.presentation

import com.example.blockvoluntariado.feature.discoveryVolunteering.domain.Convocatoria

data class HomeUiState(

    val convocatorias: List<Convocatoria> = emptyList(),

    val selectedConvocatoria: Convocatoria? = null,
    val isDetailLoading: Boolean = false,

    val isLoading: Boolean = false,
    val errorMessage: String? = null

)
