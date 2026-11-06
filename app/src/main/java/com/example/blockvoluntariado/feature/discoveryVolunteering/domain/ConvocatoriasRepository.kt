package com.example.blockvoluntariado.feature.discoveryVolunteering.domain

interface ConvocatoriasRepository {

    suspend fun getConvocatorias(): Result<List<Convocatoria>>

    suspend fun getConvocatoriasById(id: Int): Result<Convocatoria?>

}