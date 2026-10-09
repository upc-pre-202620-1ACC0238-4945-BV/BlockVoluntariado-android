package com.example.blockvoluntariado.feature.application.presentation.my_applications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blockvoluntariado.core.storage.TokenManager
import com.example.blockvoluntariado.feature.application.domain.model.EstadoPostulacion
import com.example.blockvoluntariado.feature.application.domain.usecase.CancelApplicationUseCase
import com.example.blockvoluntariado.feature.application.domain.usecase.GetMyApplicationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MisPostulacionesViewModel @Inject constructor(
    private val getMyApplicationsUseCase: GetMyApplicationsUseCase,
    private val cancelApplicationUseCase: CancelApplicationUseCase,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(MisPostulacionesUiState())
    val uiState: StateFlow<MisPostulacionesUiState> = _uiState.asStateFlow()

    init {
        loadApplications()
    }

    fun loadApplications() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val volunteerId = tokenManager.volunteerIdFlow.firstOrNull() ?: 1L
            val result = getMyApplicationsUseCase(volunteerId)
            result.onSuccess { list ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        applications = list,
                        filteredApplications = filterList(list, state.selectedStatus)
                    )
                }
            }.onFailure { err ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = err.message ?: "No se pudieron cargar las postulaciones"
                    )
                }
            }
        }
    }

    fun filterByStatus(status: EstadoPostulacion?) {
        _uiState.update { state ->
            state.copy(
                selectedStatus = status,
                filteredApplications = filterList(state.applications, status)
            )
        }
    }

    fun cancelApplication(postulacionId: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = cancelApplicationUseCase(postulacionId)
            result.onSuccess {
                loadApplications()
                _uiState.update { it.copy(successActionMessage = "Postulación cancelada con éxito") }
            }.onFailure { err ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = err.message ?: "No se pudo cancelar la postulación"
                    )
                }
            }
        }
    }

    fun clearActionMessage() {
        _uiState.update { it.copy(successActionMessage = null) }
    }

    private fun filterList(
        list: List<com.example.blockvoluntariado.feature.application.domain.model.Postulacion>,
        status: EstadoPostulacion?
    ): List<com.example.blockvoluntariado.feature.application.domain.model.Postulacion> {
        return if (status == null) list else list.filter { it.status == status }
    }
}
