package com.example.blockvoluntariado.feature.authOnboarding.presentation.recovery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blockvoluntariado.feature.authOnboarding.domain.usecase.RecoverPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordRecoveryViewModel @Inject constructor(
    private val recoverPasswordUseCase: RecoverPasswordUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PasswordRecoveryUiState())
    val uiState: StateFlow<PasswordRecoveryUiState> = _uiState.asStateFlow()

    fun onEmailChange(value: String) {
        _uiState.update { it.copy(email = value, errorMessage = null) }
    }

    fun submitRecovery() {
        val email = _uiState.value.email
        if (!email.contains("@")) {
            _uiState.update { it.copy(errorMessage = "Ingrese un correo válido") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null, successMessage = null) }
            val result = recoverPasswordUseCase(email)
            result.onSuccess { msg ->
                _uiState.update { it.copy(isLoading = false, successMessage = msg) }
            }.onFailure { err ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = err.message ?: "No se pudo procesar la solicitud"
                    )
                }
            }
        }
    }
}
