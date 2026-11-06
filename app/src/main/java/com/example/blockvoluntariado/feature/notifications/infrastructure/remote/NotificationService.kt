package com.example.blockvoluntariado.feature.notifications.infrastructure.remote

import com.example.blockvoluntariado.feature.notifications.infrastructure.remote.dto.NotificationDto
import com.example.blockvoluntariado.feature.notifications.infrastructure.remote.dto.NotificationPreferencesDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.PUT
import retrofit2.http.Path

interface NotificationService {

    @GET("v1/usuarios/{userId}/notificaciones")
    suspend fun getUserNotifications(
        @Path("userId") userId: Long
    ): List<NotificationDto>

    @PATCH("v1/notificaciones/{notificacionId}/leer")
    suspend fun markNotificationAsRead(
        @Path("notificacionId") notificacionId: Long
    ): NotificationDto

    @PATCH("v1/usuarios/{userId}/notificaciones/leer-todas")
    suspend fun markAllAsRead(
        @Path("userId") userId: Long
    ): List<NotificationDto>

    @GET("v1/usuarios/{userId}/preferencias-notificaciones")
    suspend fun getPreferences(
        @Path("userId") userId: Long
    ): NotificationPreferencesDto

    @PUT("v1/usuarios/{userId}/preferencias-notificaciones")
    suspend fun updatePreferences(
        @Path("userId") userId: Long,
        @Body preferences: NotificationPreferencesDto
    ): NotificationPreferencesDto
}
