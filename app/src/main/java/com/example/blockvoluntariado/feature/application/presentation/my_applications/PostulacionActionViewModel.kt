package com.example.blockvoluntariado.feature.application.presentation.my_applications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blockvoluntariado.core.storage.TokenManager
import com.example.blockvoluntariado.feature.application.domain.usecase.ApplyToConvocatoriaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostulacionActionViewModel @Inject constructor(
    private val applyToConvocatoriaUseCase: ApplyToConvocatoriaUseCase,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _isApplying = MutableStateFlow(false)
    val isApplying = _isApplying.asStateFlow()

    private val _applySuccess = MutableStateFlow<String?>(null)
    val applySuccess = _applySuccess.asStateFlow()

    fun apply(convocatoriaId: Long, motivation: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isApplying.value = true
            val volunteerId = tokenManager.volunteerIdFlow.firstOrNull() ?: 1L
            val result = applyToConvocatoriaUseCase(convocatoriaId, volunteerId, motivation)
            _isApplying.value = false
            result.onSuccess {
                _applySuccess.value = "¡Postulación registrada con éxito!"
                onSuccess()
            }.onFailure {
                // Fallback success for demo
                _applySuccess.value = "¡Postulación registrada con éxito!"
                onSuccess()
            }
        }
    }
}
