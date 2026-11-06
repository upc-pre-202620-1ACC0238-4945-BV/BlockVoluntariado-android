package com.example.blockvoluntariado.feature.discoveryVolunteering.domain

import com.example.blockvoluntariado.feature.discoveryVolunteering.domain.valueObjects.EstadoConvocatoria
import com.example.blockvoluntariado.feature.discoveryVolunteering.domain.valueObjects.Horario
import com.example.blockvoluntariado.feature.discoveryVolunteering.domain.valueObjects.Ubicacion

data class Convocatoria(

    val id : Int,
    val organizationId : Int,
    val title: String,
    val description: String,
    val causaType: String,
    val totalVacancies: String,
    val occupiedVacancies: String,
    val horario: Horario,
    val ubicacion: Ubicacion,
    val status: EstadoConvocatoria

)



