package com.example.blockvoluntariado.feature.discoveryVolunteering.domain.valueObjects

import java.time.LocalDate

data class Horario(
    val startDate: String?,
    val endDate: String?,
    val startTime: String?,
    val endTime: String?
)