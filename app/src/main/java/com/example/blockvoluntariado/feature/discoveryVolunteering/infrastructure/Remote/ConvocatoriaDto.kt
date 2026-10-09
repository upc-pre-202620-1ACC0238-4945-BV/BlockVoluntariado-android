package com.example.blockvoluntariado.feature.discoveryVolunteering.infrastructure.Remote

import com.example.blockvoluntariado.feature.discoveryVolunteering.infrastructure.Remote.valueObjects.EstadoConvocatoriaDto
import com.example.blockvoluntariado.feature.discoveryVolunteering.infrastructure.Remote.valueObjects.HorarioDto
import com.example.blockvoluntariado.feature.discoveryVolunteering.infrastructure.Remote.valueObjects.UbicacionDto


data class ConvocatoriaDto(

    val id : Int,
    val organizationId : Int,
    val title: String,
    val description: String,
    val causaType: String,
    val totalVacancies: String,
    val occupiedVacancies: String,
    val horario: HorarioDto,
    val ubicacion: UbicacionDto,
    val status: EstadoConvocatoriaDto

)
