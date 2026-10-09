package com.example.blockvoluntariado.feature.notifications.domain.model

data class NotificationPreference(
    val userId: Long,
    val emailEnabled: Boolean = true,
    val pushEnabled: Boolean = true,
    val causeAlerts: Boolean = true
)
