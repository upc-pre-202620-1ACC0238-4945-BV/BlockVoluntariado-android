package com.example.blockvoluntariado.feature.discoveryVolunteering.infrastructure.Remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ConvocatoriaService {

    @GET("v1/convocatorias")
    suspend fun getConvocatorias(): Response<List<ConvocatoriaDto>>

    @GET("v1/convocatorias/{convocatoriaId}")
    suspend fun getConvocatoriasById(@Path("convocatoriaId") id: Int): Response<ConvocatoriaDto>
}