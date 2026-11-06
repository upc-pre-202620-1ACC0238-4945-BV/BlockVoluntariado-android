package com.example.blockvoluntariado.feature.notifications.presentation.inbox

import com.example.blockvoluntariado.feature.notifications.domain.model.AppNotification
import com.example.blockvoluntariado.feature.notifications.domain.model.NotificationPreference

data class NotificationsUiState(
    val isLoading: Boolean = false,
    val notifications: List<AppNotification> = emptyList(),
    val filterUnreadOnly: Boolean = false,
    val preferences: NotificationPreference? = null,
    val isUpdatingPreferences: Boolean = false,
    val statusMessage: String? = null
)
