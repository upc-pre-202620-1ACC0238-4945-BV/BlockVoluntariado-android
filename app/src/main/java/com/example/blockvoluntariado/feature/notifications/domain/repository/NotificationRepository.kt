package com.example.blockvoluntariado.feature.notifications.domain.repository

import com.example.blockvoluntariado.feature.notifications.domain.model.AppNotification
import com.example.blockvoluntariado.feature.notifications.domain.model.NotificationPreference

interface NotificationRepository {
    suspend fun getUserNotifications(userId: Long): Result<List<AppNotification>>
    suspend fun markAsRead(notificationId: Long): Result<AppNotification>
    suspend fun markAllAsRead(userId: Long): Result<List<AppNotification>>
    suspend fun getPreferences(userId: Long): Result<NotificationPreference>
    suspend fun updatePreferences(
        userId: Long,
        emailEnabled: Boolean,
        pushEnabled: Boolean,
        causeAlerts: Boolean
    ): Result<NotificationPreference>
}
