package com.example.blockvoluntariado.feature.authOnboarding.presentation.recovery

data class PasswordRecoveryUiState(
    val email: String = "",
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null
)
