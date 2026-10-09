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
                val list = response.body() ?: emptyList()
                val convocatorias = list.map { it.toDomain() }
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
            val response = service.getConvocatoriasById(id)
            if (response.isSuccessful) {
                val dto = response.body()
                Result.success(dto?.toDomain())
            } else {
                Result.failure(Exception("Error HTTP: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}