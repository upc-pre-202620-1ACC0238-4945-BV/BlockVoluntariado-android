package com.example.blockvoluntariado.feature.authOnboarding.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blockvoluntariado.feature.authOnboarding.domain.usecase.RegisterOrganizationUseCase
import com.example.blockvoluntariado.feature.authOnboarding.domain.usecase.RegisterStudentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerStudentUseCase: RegisterStudentUseCase,
    private val registerOrganizationUseCase: RegisterOrganizationUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onEmailChange(value: String) {
        _uiState.update { it.copy(email = value, errorMessage = null) }
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value, errorMessage = null) }
    }

    fun onConfirmPasswordChange(value: String) {
        _uiState.update { it.copy(confirmPassword = value, errorMessage = null) }
    }

    fun onRoleSelected(role: String) {
        _uiState.update { it.copy(selectedRole = role, errorMessage = null) }
    }

    fun togglePasswordVisibility() {
        _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun register(onSuccess: () -> Unit) {
        val state = _uiState.value
        if (!state.email.contains("@")) {
            _uiState.update { it.copy(errorMessage = "Ingrese un correo electrónico válido") }
            return
        }
        if (state.password.length < 6) {
            _uiState.update { it.copy(errorMessage = "La contraseña debe tener al menos 6 caracteres") }
            return
        }
        if (state.password != state.confirmPassword) {
            _uiState.update { it.copy(errorMessage = "Las contraseñas no coinciden") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = if (state.selectedRole == "ROLE_STUDENT") {
                registerStudentUseCase(state.email, state.password)
            } else {
                registerOrganizationUseCase(state.email, state.password)
            }

            result.onSuccess {
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                onSuccess()
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Error al registrar la cuenta"
                    )
                }
            }
        }
    }
}
