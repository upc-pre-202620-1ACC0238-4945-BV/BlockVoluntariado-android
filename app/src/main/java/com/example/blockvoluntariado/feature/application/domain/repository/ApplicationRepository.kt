package com.example.blockvoluntariado.feature.application.domain.repository

import com.example.blockvoluntariado.feature.application.domain.model.Postulacion

interface ApplicationRepository {
    suspend fun applyToConvocatoria(convocatoriaId: Long, volunteerId: Long, motivation: String? = null): Result<Postulacion>
    suspend fun getMyApplications(volunteerId: Long): Result<List<Postulacion>>
    suspend fun getApplicantsByConvocatoria(convocatoriaId: Long): Result<List<Postulacion>>
    suspend fun acceptApplication(postulacionId: Long): Result<Postulacion>
    suspend fun rejectApplication(postulacionId: Long, reason: String? = null): Result<Postulacion>
    suspend fun cancelApplication(postulacionId: Long): Result<Postulacion>
}
