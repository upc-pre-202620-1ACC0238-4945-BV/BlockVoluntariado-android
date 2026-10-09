package com.example.blockvoluntariado.feature.participationTracking.presentation.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blockvoluntariado.core.storage.TokenManager
import com.example.blockvoluntariado.feature.participationTracking.domain.model.EstadoActividad
import com.example.blockvoluntariado.feature.participationTracking.domain.usecase.GetMyScheduledActivitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MisActividadesViewModel @Inject constructor(
    private val getMyScheduledActivitiesUseCase: GetMyScheduledActivitiesUseCase,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(MisActividadesUiState(isLoading = true))
    val uiState: StateFlow<MisActividadesUiState> = _uiState.asStateFlow()

    init {
        loadActividades()
    }

    fun loadActividades() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val userId = tokenManager.userIdFlow.first() ?: 1L
            getMyScheduledActivitiesUseCase(userId)
                .onSuccess { list ->
                    _uiState.update { it.copy(isLoading = false, actividades = list) }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(isLoading = false, errorMessage = error.message) }
                }
        }
    }

    fun setFilter(status: EstadoActividad?) {
        _uiState.update { it.copy(selectedFilter = status) }
    }

    fun clearMessages() {
        _uiState.update { it.copy(errorMessage = null, successMessage = null) }
    }
}
