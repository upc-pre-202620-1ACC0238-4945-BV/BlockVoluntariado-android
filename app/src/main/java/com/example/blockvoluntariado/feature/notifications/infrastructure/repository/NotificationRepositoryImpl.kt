package com.example.blockvoluntariado.feature.notifications.infrastructure.repository

import com.example.blockvoluntariado.feature.notifications.domain.model.AppNotification
import com.example.blockvoluntariado.feature.notifications.domain.model.NotificationPreference
import com.example.blockvoluntariado.feature.notifications.domain.model.NotificationType
import com.example.blockvoluntariado.feature.notifications.domain.repository.NotificationRepository
import com.example.blockvoluntariado.feature.notifications.infrastructure.remote.NotificationService
import com.example.blockvoluntariado.feature.notifications.infrastructure.remote.dto.NotificationPreferencesDto
import java.util.concurrent.CopyOnWriteArrayList
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val service: NotificationService
) : NotificationRepository {

    private val memoryStore = CopyOnWriteArrayList<AppNotification>().apply {
        addAll(
            listOf(
                AppNotification(
                    id = 801L,
                    userId = 1L,
                    title = "¡Postulación Aceptada!",
                    message = "La organización ha aceptado tu postulación a 'Apoyo Escolar en San Juan de Lurigancho'. Revisa el horario de campo.",
                    type = NotificationType.POSTULACION_STATUS,
                    isRead = false,
                    sentAt = "Hace 2 horas"
                ),
                AppNotification(
                    id = 802L,
                    userId = 1L,
                    title = "Recordatorio de Actividad",
                    message = "Tu jornada 'Reforestación de Lomas de Amancaes' está próxima a comenzar. El supervisor pasará asistencia en el punto de encuentro.",
                    type = NotificationType.ACTIVIDAD_RECORDATORIO,
                    isRead = false,
                    sentAt = "Ayer"
                ),
                AppNotification(
                    id = 803L,
                    userId = 1L,
                    title = "Nuevo Certificado Emitido en Blockchain",
                    message = "Tu certificado de participación por 20 horas en 'Campaña de Vacunación Animal' ha sido firmado e inscrito con SHA-256.",
                    type = NotificationType.CERTIFICADO_EMITIDO,
                    isRead = true,
                    sentAt = "15 Sep 2026"
                )
            )
        )
    }

    private var currentPreferences = NotificationPreference(
        userId = 1L,
        emailEnabled = true,
        pushEnabled = true,
        causeAlerts = true
    )

    override suspend fun getUserNotifications(userId: Long): Result<List<AppNotification>> {
        return try {
            val dtoList = service.getUserNotifications(userId)
            val domainList = dtoList.map { it.toDomain() }
            if (domainList.isNotEmpty()) {
                Result.success(domainList)
            } else {
                Result.success(memoryStore.toList())
            }
        } catch (e: Exception) {
            Result.success(memoryStore.toList())
        }
    }

    override suspend fun markAsRead(notificationId: Long): Result<AppNotification> {
        return try {
            val dto = service.markNotificationAsRead(notificationId)
            val domain = dto.toDomain()
            updateMemoryRead(notificationId)
            Result.success(domain)
        } catch (e: Exception) {
            val updated = updateMemoryRead(notificationId)
            if (updated != null) Result.success(updated)
            else Result.failure(e)
        }
    }

    override suspend fun markAllAsRead(userId: Long): Result<List<AppNotification>> {
        return try {
            val dtoList = service.markAllAsRead(userId)
            val domainList = dtoList.map { it.toDomain() }
            memoryStore.forEachIndexed { index, notif ->
                memoryStore[index] = notif.copy(isRead = true)
            }
            Result.success(domainList)
        } catch (e: Exception) {
            memoryStore.forEachIndexed { index, notif ->
                memoryStore[index] = notif.copy(isRead = true)
            }
            Result.success(memoryStore.toList())
        }
    }

    override suspend fun getPreferences(userId: Long): Result<NotificationPreference> {
        return try {
            val dto = service.getPreferences(userId)
            val domain = dto.toDomain()
            currentPreferences = domain
            Result.success(domain)
        } catch (e: Exception) {
            Result.success(currentPreferences)
        }
    }

    override suspend fun updatePreferences(
        userId: Long,
        emailEnabled: Boolean,
        pushEnabled: Boolean,
        causeAlerts: Boolean
    ): Result<NotificationPreference> {
        val dto = NotificationPreferencesDto(
            userId = userId,
            emailEnabled = emailEnabled,
            pushEnabled = pushEnabled,
            causeAlerts = causeAlerts
        )
        return try {
            val updatedDto = service.updatePreferences(userId, dto)
            val domain = updatedDto.toDomain()
            currentPreferences = domain
            Result.success(domain)
        } catch (e: Exception) {
            currentPreferences = currentPreferences.copy(
                emailEnabled = emailEnabled,
                pushEnabled = pushEnabled,
                causeAlerts = causeAlerts
            )
            Result.success(currentPreferences)
        }
    }

    private fun updateMemoryRead(id: Long): AppNotification? {
        val index = memoryStore.indexOfFirst { it.id == id }
        if (index != -1) {
            val updated = memoryStore[index].copy(isRead = true)
            memoryStore[index] = updated
            return updated
        }
        return null
    }
}
