package com.example.blockvoluntariado.feature.notifications.infrastructure.remote.dto

import com.example.blockvoluntariado.feature.notifications.domain.model.AppNotification
import com.example.blockvoluntariado.feature.notifications.domain.model.NotificationPreference
import com.example.blockvoluntariado.feature.notifications.domain.model.NotificationType
import com.google.gson.annotations.SerializedName

data class NotificationDto(
    @SerializedName("id") val id: Long,
    @SerializedName("userId") val userId: Long,
    @SerializedName("title") val title: String,
    @SerializedName("message") val message: String,
    @SerializedName("type") val type: String? = null,
    @SerializedName("isRead") val isRead: Boolean = false,
    @SerializedName("sentAt") val sentAt: String? = null
) {
    fun toDomain(): AppNotification {
        return AppNotification(
            id = id,
            userId = userId,
            title = title,
            message = message,
            type = NotificationType.fromString(type),
            isRead = isRead,
            sentAt = sentAt ?: "Hoy"
        )
    }
}

data class NotificationPreferencesDto(
    @SerializedName("userId") val userId: Long,
    @SerializedName("emailEnabled") val emailEnabled: Boolean = true,
    @SerializedName("pushEnabled") val pushEnabled: Boolean = true,
    @SerializedName("causeAlerts") val causeAlerts: Boolean = true
) {
    fun toDomain(): NotificationPreference {
        return NotificationPreference(
            userId = userId,
            emailEnabled = emailEnabled,
            pushEnabled = pushEnabled,
            causeAlerts = causeAlerts
        )
    }
}
