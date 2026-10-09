package com.example.blockvoluntariado.feature.discoveryVolunteering.infrastructure.Repositories

import com.example.blockvoluntariado.feature.discoveryVolunteering.domain.Convocatoria
import com.example.blockvoluntariado.feature.discoveryVolunteering.domain.valueObjects.EstadoConvocatoria
import com.example.blockvoluntariado.feature.discoveryVolunteering.domain.valueObjects.Horario
import com.example.blockvoluntariado.feature.discoveryVolunteering.domain.valueObjects.Ubicacion
import com.example.blockvoluntariado.feature.discoveryVolunteering.infrastructure.Remote.ConvocatoriaDto
import com.example.blockvoluntariado.feature.discoveryVolunteering.infrastructure.Remote.valueObjects.EstadoConvocatoriaDto
import com.example.blockvoluntariado.feature.discoveryVolunteering.infrastructure.Remote.valueObjects.HorarioDto
import com.example.blockvoluntariado.feature.discoveryVolunteering.infrastructure.Remote.valueObjects.UbicacionDto

fun ConvocatoriaDto.toDomain(): Convocatoria {
    return Convocatoria(
        id = this.id,
        organizationId = this.organizationId,
        title = this.title,
        description = this.description,
        causaType = this.causaType,
        totalVacancies = this.totalVacancies,
        occupiedVacancies = this.occupiedVacancies,
        horario = this.horario.toDomain(),
        ubicacion = this.ubicacion.toDomain(),
        status = this.status.toDomain()
    )
}

fun HorarioDto.toDomain(): Horario {
    return Horario(
        startDate = this.startDate,
        endDate = this.endDate,
        startTime = this.startTime,
        endTime = this.endTime
    )
}

fun UbicacionDto.toDomain(): Ubicacion {
    return Ubicacion(
        address = this.address,
        district = this.district,
        latitude = this.latitude,
        longitude = this.longitude
    )
}

fun EstadoConvocatoriaDto.toDomain(): EstadoConvocatoria {
    return when (this) {
        EstadoConvocatoriaDto.BORRADOR -> EstadoConvocatoria.BORRADOR
        EstadoConvocatoriaDto.PUBLICADA -> EstadoConvocatoria.PUBLICADA
        EstadoConvocatoriaDto.CERRADA -> EstadoConvocatoria.CERRADA
    }
}