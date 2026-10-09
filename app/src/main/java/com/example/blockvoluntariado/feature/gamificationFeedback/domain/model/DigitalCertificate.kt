package com.example.blockvoluntariado.feature.gamificationFeedback.domain.model

data class DigitalCertificate(
    val id: Long,
    val volunteerId: Long,
    val convocatoriaId: Long,
    val verificationHash: String,
    val accreditedHours: Int,
    val pdfDownloadUrl: String?,
    val issuedAt: String,
    val convocatoriaTitle: String = "Voluntariado Universitario",
    val organizationName: String = "Organización Acreditada",
    val isBlockchainVerified: Boolean = true
)
