package com.example.blockvoluntariado.feature.discoveryVolunteering.application

import com.example.blockvoluntariado.feature.discoveryVolunteering.domain.ConvocatoriasRepository
import javax.inject.Inject

class GetConvocatoriaByIdUseCase @Inject constructor(val convocatoria: ConvocatoriasRepository) {

    suspend operator fun invoke(id: Int) =  convocatoria.getConvocatoriasById(id)

}