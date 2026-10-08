package com.example.blockvoluntariado.feature.discoveryVolunteering.application

import com.example.blockvoluntariado.feature.discoveryVolunteering.domain.ConvocatoriasRepository
import javax.inject.Inject

class GetConvocatoriaUseCase @Inject constructor(val convocatoria: ConvocatoriasRepository){

    suspend operator fun invoke() = convocatoria.getConvocatorias()

}