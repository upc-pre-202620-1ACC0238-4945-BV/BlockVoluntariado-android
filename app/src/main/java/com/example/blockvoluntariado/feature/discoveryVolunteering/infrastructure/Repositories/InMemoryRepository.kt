package com.example.blockvoluntariado.feature.discoveryVolunteering.infrastructure.Repositories

import com.example.blockvoluntariado.feature.discoveryVolunteering.domain.Convocatoria
import com.example.blockvoluntariado.feature.discoveryVolunteering.domain.ConvocatoriasRepository
import com.example.blockvoluntariado.feature.discoveryVolunteering.domain.valueObjects.EstadoConvocatoria
import com.example.blockvoluntariado.feature.discoveryVolunteering.domain.valueObjects.Horario
import com.example.blockvoluntariado.feature.discoveryVolunteering.domain.valueObjects.Ubicacion
import kotlinx.coroutines.delay
import java.time.LocalDate
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

class InMemoryRepository @Inject constructor() : ConvocatoriasRepository {


    private val _convocatorias = listOf(
        Convocatoria(
            id = 1,
            organizationId = 101,
            title = "Limpieza de Playas en Miraflores",
            description = "Únete a nuestra jornada de limpieza para proteger la fauna marina y mantener nuestras playas libres de plástico.",
            causaType = "Medio Ambiente",
            totalVacancies = "30",
            occupiedVacancies = "12",
            horario = Horario(
                startDate = "2025-04-15",
                endDate = "2025-04-15",
                startTime = "08:00 AM",
                endTime = "01:00 PM"
            ),
            ubicacion = Ubicacion(
                address = "Playa Makaha",
                district = "Miraflores",
                latitude = -12.122,
                longitude = -77.036
            ),
            status = EstadoConvocatoria.PUBLICADA
        ),
        Convocatoria(
            id = 2,
            organizationId = 102,
            title = "Apoyo Escolar para Niños de SJL",
            description = "Buscamos voluntarios para reforzamiento académico en matemáticas y comunicación a niños de nivel primaria.",
            causaType = "Educación",
            totalVacancies = "20",
            occupiedVacancies = "8",
            horario = Horario(
                startDate = "2025-04-15",
                endDate = "2025-04-15",
                startTime = "08:00 AM",
                endTime = "01:00 PM"
            ),
            ubicacion = Ubicacion(
                address = "Av. Próceres de la Independencia 1234",
                district = "San Juan de Lurigancho",
                latitude = -12.001,
                longitude = -77.005
            ),
            status = EstadoConvocatoria.PUBLICADA
        ),
        Convocatoria(
            id = 3,
            organizationId = 103,
            title = "Colecta de Alimentos para Albergue Animal",
            description = "Apóyanos a recolectar y clasificar comida y suministros médicos para más de 80 perritos rescatados.",
            causaType = "Protección Animal",
            totalVacancies = "15",
            occupiedVacancies = "15",
            horario = Horario(
                startDate = "2025-04-15",
                endDate = "2025-04-15",
                startTime = "08:00 AM",
                endTime = "01:00 PM"
            ),
            ubicacion = Ubicacion(
                address = "Calle Los Jazmines 450",
                district = "Santiago de Surco",
                latitude = -12.135,
                longitude = -76.982
            ),
            status = EstadoConvocatoria.CERRADA
        )
    )

    override suspend fun getConvocatorias(): Result<List<Convocatoria>> {
        delay(1000.milliseconds)
        return Result.success(_convocatorias)
    }

    override suspend fun getConvocatoriasById(id: Int): Result<Convocatoria?> {
        delay(500.milliseconds)
        val convocatoria = _convocatorias.find { it.id == id }
        return Result.success(convocatoria)
    }
}