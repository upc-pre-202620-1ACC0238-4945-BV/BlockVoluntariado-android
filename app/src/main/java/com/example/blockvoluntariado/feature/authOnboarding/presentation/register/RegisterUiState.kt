package com.example.blockvoluntariado.feature.authOnboarding.presentation.register

data class RegisterUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val selectedRole: String = "ROLE_STUDENT", // "ROLE_STUDENT" or "ROLE_ORGANIZATION"
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)
