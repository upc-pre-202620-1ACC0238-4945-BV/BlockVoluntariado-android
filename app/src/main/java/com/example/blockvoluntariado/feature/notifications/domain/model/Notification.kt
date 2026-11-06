package com.example.blockvoluntariado.feature.notifications.domain.model

enum class NotificationType(val displayName: String) {
    POSTULACION_STATUS("Postulaciones"),
    ACTIVIDAD_RECORDATORIO("Actividad en Campo"),
    CERTIFICADO_EMITIDO("Certificado Digital"),
    SISTEMA("Sistema");

    companion object {
        fun fromString(type: String?): NotificationType {
            return when (type?.uppercase()) {
                "POSTULACION", "POSTULACION_STATUS", "APPLICATION" -> POSTULACION_STATUS
                "ACTIVIDAD", "ACTIVIDAD_RECORDATORIO", "ACTIVITY" -> ACTIVIDAD_RECORDATORIO
                "CERTIFICADO", "CERTIFICADO_EMITIDO", "CERTIFICATE" -> CERTIFICADO_EMITIDO
                else -> SISTEMA
            }
        }
    }
}

data class AppNotification(
    val id: Long,
    val userId: Long,
    val title: String,
    val message: String,
    val type: NotificationType,
    val isRead: Boolean = false,
    val sentAt: String = "Hoy"
)
