package com.example.blockvoluntariado.feature.notifications.presentation.inbox

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blockvoluntariado.core.storage.TokenManager
import com.example.blockvoluntariado.feature.notifications.domain.usecase.GetNotificationPreferencesUseCase
import com.example.blockvoluntariado.feature.notifications.domain.usecase.GetUserNotificationsUseCase
import com.example.blockvoluntariado.feature.notifications.domain.usecase.MarkAllNotificationsAsReadUseCase
import com.example.blockvoluntariado.feature.notifications.domain.usecase.MarkNotificationAsReadUseCase
import com.example.blockvoluntariado.feature.notifications.domain.usecase.UpdateNotificationPreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val getUserNotificationsUseCase: GetUserNotificationsUseCase,
    private val markNotificationAsReadUseCase: MarkNotificationAsReadUseCase,
    private val markAllNotificationsAsReadUseCase: MarkAllNotificationsAsReadUseCase,
    private val getNotificationPreferencesUseCase: GetNotificationPreferencesUseCase,
    private val updateNotificationPreferencesUseCase: UpdateNotificationPreferencesUseCase,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationsUiState(isLoading = true))
    val uiState: StateFlow<NotificationsUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val userId = tokenManager.userIdFlow.first() ?: 1L

            val notifsResult = getUserNotificationsUseCase(userId)
            val prefsResult = getNotificationPreferencesUseCase(userId)

            _uiState.update {
                it.copy(
                    isLoading = false,
                    notifications = notifsResult.getOrDefault(emptyList()),
                    preferences = prefsResult.getOrNull()
                )
            }
        }
    }

    fun toggleFilter(unreadOnly: Boolean) {
        _uiState.update { it.copy(filterUnreadOnly = unreadOnly) }
    }

    fun markAsRead(notificationId: Long) {
        viewModelScope.launch {
            markNotificationAsReadUseCase(notificationId)
            _uiState.update { state ->
                val updated = state.notifications.map { notif ->
                    if (notif.id == notificationId) notif.copy(isRead = true)
                    else notif
                }
                state.copy(notifications = updated)
            }
        }
    }

    fun markAllAsRead() {
        viewModelScope.launch {
            val userId = tokenManager.userIdFlow.first() ?: 1L
            markAllNotificationsAsReadUseCase(userId)
            _uiState.update { state ->
                val updated = state.notifications.map { it.copy(isRead = true) }
                state.copy(notifications = updated, statusMessage = "Todas las notificaciones marcadas como leídas")
            }
        }
    }

    fun updatePreferences(emailEnabled: Boolean, pushEnabled: Boolean, causeAlerts: Boolean) {
        viewModelScope.launch {
            _uiState.update { it.copy(isUpdatingPreferences = true) }
            val userId = tokenManager.userIdFlow.first() ?: 1L
            updateNotificationPreferencesUseCase(userId, emailEnabled, pushEnabled, causeAlerts)
                .onSuccess { updatedPrefs ->
                    _uiState.update {
                        it.copy(
                            isUpdatingPreferences = false,
                            preferences = updatedPrefs,
                            statusMessage = "Preferencias actualizadas con éxito"
                        )
                    }
                }
                .onFailure {
                    _uiState.update { state ->
                        state.copy(
                            isUpdatingPreferences = false,
                            statusMessage = "No se pudieron guardar las preferencias"
                        )
                    }
                }
        }
    }

    fun clearStatusMessage() {
        _uiState.update { it.copy(statusMessage = null) }
    }
}
