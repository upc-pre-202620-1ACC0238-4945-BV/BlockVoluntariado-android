package com.example.blockvoluntariado.feature.discoveryVolunteering.infrastructure.Remote

import com.example.blockvoluntariado.feature.discoveryVolunteering.infrastructure.Remote.valueObjects.EstadoConvocatoriaDto
import com.example.blockvoluntariado.feature.discoveryVolunteering.infrastructure.Remote.valueObjects.HorarioDto
import com.example.blockvoluntariado.feature.discoveryVolunteering.infrastructure.Remote.valueObjects.UbicacionDto
import com.google.gson.annotations.SerializedName

data class ConvocatoriaDto(
    @SerializedName("id") val id: Int,
    @SerializedName("organizationId") val organizationId: Int = 1,
    @SerializedName("title") val title: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName(value = "causaType", alternate = ["causeType"]) val causaType: String? = null,
    @SerializedName("totalVacancies") val totalVacancies: String? = null,
    @SerializedName("occupiedVacancies") val occupiedVacancies: String? = null,
    @SerializedName("horario") val horario: HorarioDto? = null,
    @SerializedName("ubicacion") val ubicacion: UbicacionDto? = null,
    @SerializedName("status") val status: EstadoConvocatoriaDto? = null
)
