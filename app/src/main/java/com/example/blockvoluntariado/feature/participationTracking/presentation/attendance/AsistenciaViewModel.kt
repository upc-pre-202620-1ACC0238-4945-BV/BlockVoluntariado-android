package com.example.blockvoluntariado.feature.participationTracking.presentation.attendance

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blockvoluntariado.feature.participationTracking.domain.model.AsistenciaParticipante
import com.example.blockvoluntariado.feature.participationTracking.domain.model.EstadoActividad
import com.example.blockvoluntariado.feature.participationTracking.domain.usecase.CompleteActividadUseCase
import com.example.blockvoluntariado.feature.participationTracking.domain.usecase.GetActividadDetailUseCase
import com.example.blockvoluntariado.feature.participationTracking.domain.usecase.GetParticipantesActividadUseCase
import com.example.blockvoluntariado.feature.participationTracking.domain.usecase.RecordBulkAttendanceUseCase
import com.example.blockvoluntariado.feature.participationTracking.domain.usecase.StartActividadUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AsistenciaViewModel @Inject constructor(
    private val getActividadDetailUseCase: GetActividadDetailUseCase,
    private val getParticipantesActividadUseCase: GetParticipantesActividadUseCase,
    private val recordBulkAttendanceUseCase: RecordBulkAttendanceUseCase,
    private val startActividadUseCase: StartActividadUseCase,
    private val completeActividadUseCase: CompleteActividadUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val actividadId: Long = savedStateHandle.get<Long>("actividadId") ?: 1L

    private val _uiState = MutableStateFlow(AsistenciaUiState(isLoading = true))
    val uiState: StateFlow<AsistenciaUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val actResult = getActividadDetailUseCase(actividadId)
            val partsResult = getParticipantesActividadUseCase(actividadId)

            val actividad = actResult.getOrNull()
            val participants = partsResult.getOrNull() ?: actividad?.asistencias ?: emptyList()

            _uiState.update {
                it.copy(
                    isLoading = false,
                    actividad = actividad,
                    participants = participants,
                    isActivityCompleted = actividad?.status == EstadoActividad.COMPLETADA
                )
            }
        }
    }

    fun toggleAttendance(volunteerId: Long, isPresent: Boolean) {
        _uiState.update { state ->
            val updated = state.participants.map { item ->
                if (item.volunteerId == volunteerId) {
                    item.copy(isPresent = isPresent)
                } else item
            }
            state.copy(participants = updated)
        }
    }

    fun updateHours(volunteerId: Long, hours: Int) {
        _uiState.update { state ->
            val updated = state.participants.map { item ->
                if (item.volunteerId == volunteerId) {
                    item.copy(certifiedHours = hours)
                } else item
            }
            state.copy(participants = updated)
        }
    }

    fun updateNotes(volunteerId: Long, notes: String) {
        _uiState.update { state ->
            val updated = state.participants.map { item ->
                if (item.volunteerId == volunteerId) {
                    item.copy(supervisorNotes = notes)
                } else item
            }
            state.copy(participants = updated)
        }
    }

    fun saveAttendance() {
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            val currentList = _uiState.value.participants
            recordBulkAttendanceUseCase(actividadId, currentList)
                .onSuccess { updatedAct ->
                    _uiState.update {
                        it.copy(
                            isSaving = false,
                            actividad = updatedAct,
                            participants = updatedAct.asistencias.ifEmpty { currentList },
                            feedbackMessage = "Asistencias registradas correctamente en blockchain/servidor"
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isSaving = false,
                            feedbackMessage = "Error al sincronizar: ${error.message}"
                        )
                    }
                }
        }
    }

    fun startActividad() {
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            startActividadUseCase(actividadId)
                .onSuccess { updated ->
                    _uiState.update {
                        it.copy(
                            isSaving = false,
                            actividad = updated,
                            feedbackMessage = "Jornada iniciada. Estado: EN CURSO"
                        )
                    }
                }
                .onFailure {
                    _uiState.update { state ->
                        state.copy(
                            isSaving = false,
                            feedbackMessage = "No se pudo iniciar la actividad"
                        )
                    }
                }
        }
    }

    fun completeActividad() {
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            completeActividadUseCase(actividadId)
                .onSuccess { updated ->
                    _uiState.update {
                        it.copy(
                            isSaving = false,
                            actividad = updated,
                            isActivityCompleted = true,
                            feedbackMessage = "¡Actividad finalizada! Horas sociales acreditadas a los participantes."
                        )
                    }
                }
                .onFailure {
                    _uiState.update { state ->
                        state.copy(
                            isSaving = false,
                            feedbackMessage = "No se pudo finalizar la actividad"
                        )
                    }
                }
        }
    }

    fun clearFeedback() {
        _uiState.update { it.copy(feedbackMessage = null) }
    }
}
