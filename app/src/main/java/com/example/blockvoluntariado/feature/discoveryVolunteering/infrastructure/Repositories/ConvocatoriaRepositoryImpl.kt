package com.example.blockvoluntariado.feature.discoveryVolunteering.infrastructure.Repositories

import com.example.blockvoluntariado.feature.discoveryVolunteering.domain.Convocatoria
import com.example.blockvoluntariado.feature.discoveryVolunteering.domain.ConvocatoriasRepository
import com.example.blockvoluntariado.feature.discoveryVolunteering.infrastructure.Remote.ConvocatoriaService
import javax.inject.Inject

class ConvocatoriaRepositoryImpl @Inject constructor(
    private val service: ConvocatoriaService
) : ConvocatoriasRepository {

    override suspend fun getConvocatorias(): Result<List<Convocatoria>> {
        return try {

            val response = service.getConvocatorias()

            if (response.isSuccessful) {
                val responseDto = response.body()
                val convocatorias = responseDto?.convocatoria?.map { dto ->
                    dto.toDomain()
                } ?: emptyList()

                Result.success(convocatorias)
            } else {
                Result.failure(Exception("Error HTTP: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getConvocatoriasById(id: Int): Result<Convocatoria?> {
        return try {
            val response = service.getConvocatoriasByid(id)

            if (response.isSuccessful) {
                val responseDto = response.body()
                val convocatoria = responseDto?.convocatoria?.firstOrNull()?.toDomain()
                Result.success(convocatoria)
            } else {
                Result.failure(Exception("Error HTTP: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}