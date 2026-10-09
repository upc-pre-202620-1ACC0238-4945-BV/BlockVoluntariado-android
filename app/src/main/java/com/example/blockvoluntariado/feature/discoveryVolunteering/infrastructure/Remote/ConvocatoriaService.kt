package com.example.blockvoluntariado.feature.discoveryVolunteering.infrastructure.Remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ConvocatoriaService {

    @GET ("convocatorias")
    suspend fun  getConvocatorias(): Response<ConvocatoriaResponseDto>


    @GET ("convocatorias/{convocatoriaId}")
    suspend fun getConvocatoriasByid(@Path("convocatoriaId")id: Int): Response<ConvocatoriaResponseDto>


}