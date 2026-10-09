package com.example.blockvoluntariado.feature.discoveryVolunteering.domain.valueObjects

import java.time.LocalDate

data class Horario(
    val startDate: LocalDate?,
    val endDate: LocalDate?,
    val startTime: String?,
    val endTime: String?
)
