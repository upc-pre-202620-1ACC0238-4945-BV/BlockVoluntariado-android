package com.example.blockvoluntariado.feature.authOnboarding.domain.usecase

import com.example.blockvoluntariado.feature.authOnboarding.domain.model.AuthSession
import com.example.blockvoluntariado.feature.authOnboarding.domain.model.User
import com.example.blockvoluntariado.feature.authOnboarding.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(username: String, password: String): Result<User> {
        if (username.isBlank() || password.isBlank()) {
            return Result.failure(IllegalArgumentException("El correo y contraseña son obligatorios"))
        }
        return repository.login(username.trim(), password)
    }
}

class RegisterStudentUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        if (!email.contains("@") || password.length < 6) {
            return Result.failure(IllegalArgumentException("Ingrese un correo válido y una contraseña de al menos 6 caracteres"))
        }
        return repository.registerStudent(email.trim(), password)
    }
}

class RegisterOrganizationUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        if (!email.contains("@") || password.length < 6) {
            return Result.failure(IllegalArgumentException("Ingrese un correo corporativo válido y una contraseña de al menos 6 caracteres"))
        }
        return repository.registerOrganization(email.trim(), password)
    }
}

class RecoverPasswordUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String): Result<String> {
        if (!email.contains("@")) {
            return Result.failure(IllegalArgumentException("Ingrese un correo electrónico válido"))
        }
        return repository.recoverPassword(email.trim())
    }
}

class GetSessionUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): Flow<AuthSession> = repository.getSessionFlow()
}

class LogoutUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() = repository.logout()
}
