package com.example.blockvoluntariado.feature.discoveryVolunteering.infrastructure.Remote.valueObjects

import java.time.LocalDate

data class HorarioDto(

    val startDate: LocalDate?,
    val endDate: LocalDate?,
    val startTime: String?,
    val endTime: String?

)
