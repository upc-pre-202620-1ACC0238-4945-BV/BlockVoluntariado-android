package com.example.blockvoluntariado.feature.authOnboarding.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blockvoluntariado.feature.authOnboarding.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailOrUsernameChange(value: String) {
        _uiState.update { it.copy(emailOrUsername = value, errorMessage = null) }
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value, errorMessage = null) }
    }

    fun togglePasswordVisibility() {
        _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun fillDemoCredentials() {
        _uiState.update {
            it.copy(
                emailOrUsername = "estudiante@upc.edu.pe",
                password = "password123",
                errorMessage = null
            )
        }
    }

    fun login(onSuccess: () -> Unit) {
        val currentState = _uiState.value
        if (currentState.emailOrUsername.isBlank() || currentState.password.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Por favor ingrese usuario y contraseña") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = loginUseCase(currentState.emailOrUsername, currentState.password)
            result.onSuccess {
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                onSuccess()
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Credenciales incorrectas"
                    )
                }
            }
        }
    }
}
