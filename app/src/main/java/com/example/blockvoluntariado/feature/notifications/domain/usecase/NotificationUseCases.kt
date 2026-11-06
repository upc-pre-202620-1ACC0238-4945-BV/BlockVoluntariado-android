package com.example.blockvoluntariado.feature.notifications.domain.usecase

import com.example.blockvoluntariado.feature.notifications.domain.model.AppNotification
import com.example.blockvoluntariado.feature.notifications.domain.model.NotificationPreference
import com.example.blockvoluntariado.feature.notifications.domain.repository.NotificationRepository
import javax.inject.Inject

class GetUserNotificationsUseCase @Inject constructor(
    private val repository: NotificationRepository
) {
    suspend operator fun invoke(userId: Long): Result<List<AppNotification>> {
        return repository.getUserNotifications(userId)
    }
}

class MarkNotificationAsReadUseCase @Inject constructor(
    private val repository: NotificationRepository
) {
    suspend operator fun invoke(notificationId: Long): Result<AppNotification> {
        return repository.markAsRead(notificationId)
    }
}

class MarkAllNotificationsAsReadUseCase @Inject constructor(
    private val repository: NotificationRepository
) {
    suspend operator fun invoke(userId: Long): Result<List<AppNotification>> {
        return repository.markAllAsRead(userId)
    }
}

class GetNotificationPreferencesUseCase @Inject constructor(
    private val repository: NotificationRepository
) {
    suspend operator fun invoke(userId: Long): Result<NotificationPreference> {
        return repository.getPreferences(userId)
    }
}

class UpdateNotificationPreferencesUseCase @Inject constructor(
    private val repository: NotificationRepository
) {
    suspend operator fun invoke(
        userId: Long,
        emailEnabled: Boolean,
        pushEnabled: Boolean,
        causeAlerts: Boolean
    ): Result<NotificationPreference> {
        return repository.updatePreferences(userId, emailEnabled, pushEnabled, causeAlerts)
    }
}
