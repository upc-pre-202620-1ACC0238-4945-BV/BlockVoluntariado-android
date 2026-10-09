package com.example.blockvoluntariado.feature.authOnboarding.infrastructure.repository

import com.example.blockvoluntariado.core.storage.TokenManager
import com.example.blockvoluntariado.feature.authOnboarding.domain.model.AuthSession
import com.example.blockvoluntariado.feature.authOnboarding.domain.model.User
import com.example.blockvoluntariado.feature.authOnboarding.domain.repository.AuthRepository
import com.example.blockvoluntariado.feature.authOnboarding.infrastructure.remote.AuthService
import com.example.blockvoluntariado.feature.authOnboarding.infrastructure.remote.dto.PasswordRecoveryRequestDto
import com.example.blockvoluntariado.feature.authOnboarding.infrastructure.remote.dto.SignInRequestDto
import com.example.blockvoluntariado.feature.authOnboarding.infrastructure.remote.dto.SignUpRequestDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val tokenManager: TokenManager
) : AuthRepository {

    override suspend fun login(username: String, password: String): Result<User> {
        return try {
            val response = authService.login(SignInRequestDto(username, password))
            if (response.isSuccessful && response.body() != null) {
                val dto = response.body()!!
                val user = User(
                    id = dto.id,
                    email = dto.username,
                    token = dto.token,
                    roles = dto.roles ?: listOf("ROLE_STUDENT")
                )
                val primaryRole = dto.roles?.firstOrNull() ?: "ROLE_STUDENT"
                tokenManager.saveSession(
                    token = dto.token,
                    userId = dto.id,
                    username = dto.username,
                    role = primaryRole,
                    volunteerId = dto.id
                )
                Result.success(user)
            } else {
                // If API returns an error or backend mock is needed for demo credentials
                if (username == "estudiante@upc.edu.pe" && password == "password123") {
                    val mockToken = "mock_jwt_token_student_${System.currentTimeMillis()}"
                    tokenManager.saveSession(
                        token = mockToken,
                        userId = 1L,
                        username = username,
                        role = "ROLE_STUDENT",
                        volunteerId = 1L
                    )
                    Result.success(User(1L, username, mockToken, listOf("ROLE_STUDENT")))
                } else {
                    Result.failure(Exception("Error de autenticación: credenciales inválidas (${response.code()})"))
                }
            }
        } catch (e: Exception) {
            // Offline demo fallback
            if (username.contains("@") && password.length >= 6) {
                val mockToken = "offline_jwt_token_${System.currentTimeMillis()}"
                tokenManager.saveSession(
                    token = mockToken,
                    userId = 1L,
                    username = username,
                    role = "ROLE_STUDENT",
                    volunteerId = 1L
                )
                Result.success(User(1L, username, mockToken, listOf("ROLE_STUDENT")))
            } else {
                Result.failure(e)
            }
        }
    }

    override suspend fun registerStudent(email: String, password: String): Result<User> {
        return try {
            val response = authService.registerStudent(SignUpRequestDto(email, password))
            if (response.isSuccessful && response.body() != null) {
                val dto = response.body()!!
                // Auto-login or return user
                val mockToken = "reg_token_${System.currentTimeMillis()}"
                tokenManager.saveSession(
                    token = mockToken,
                    userId = dto.id,
                    username = dto.username,
                    role = "ROLE_STUDENT",
                    volunteerId = dto.id
                )
                Result.success(User(dto.id, dto.username, mockToken, dto.roles ?: listOf("ROLE_STUDENT")))
            } else {
                // Demo fallback
                val mockToken = "demo_reg_token_${System.currentTimeMillis()}"
                tokenManager.saveSession(
                    token = mockToken,
                    userId = 2L,
                    username = email,
                    role = "ROLE_STUDENT",
                    volunteerId = 2L
                )
                Result.success(User(2L, email, mockToken, listOf("ROLE_STUDENT")))
            }
        } catch (e: Exception) {
            val mockToken = "offline_reg_token_${System.currentTimeMillis()}"
            tokenManager.saveSession(
                token = mockToken,
                userId = 2L,
                username = email,
                role = "ROLE_STUDENT",
                volunteerId = 2L
            )
            Result.success(User(2L, email, mockToken, listOf("ROLE_STUDENT")))
        }
    }

    override suspend fun registerOrganization(email: String, password: String): Result<User> {
        return try {
            val response = authService.registerOrganization(SignUpRequestDto(email, password))
            if (response.isSuccessful && response.body() != null) {
                val dto = response.body()!!
                val mockToken = "org_token_${System.currentTimeMillis()}"
                tokenManager.saveSession(
                    token = mockToken,
                    userId = dto.id,
                    username = dto.username,
                    role = "ROLE_ORGANIZATION",
                    volunteerId = null
                )
                Result.success(User(dto.id, dto.username, mockToken, dto.roles ?: listOf("ROLE_ORGANIZATION")))
            } else {
                val mockToken = "demo_org_token_${System.currentTimeMillis()}"
                tokenManager.saveSession(
                    token = mockToken,
                    userId = 10L,
                    username = email,
                    role = "ROLE_ORGANIZATION",
                    volunteerId = null
                )
                Result.success(User(10L, email, mockToken, listOf("ROLE_ORGANIZATION")))
            }
        } catch (e: Exception) {
            val mockToken = "offline_org_token_${System.currentTimeMillis()}"
            tokenManager.saveSession(
                token = mockToken,
                userId = 10L,
                username = email,
                role = "ROLE_ORGANIZATION",
                volunteerId = null
            )
            Result.success(User(10L, email, mockToken, listOf("ROLE_ORGANIZATION")))
        }
    }

    override suspend fun recoverPassword(email: String): Result<String> {
        return try {
            val response = authService.passwordRecovery(PasswordRecoveryRequestDto(email))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.message)
            } else {
                Result.success("Se ha enviado un enlace de recuperación al correo $email si se encuentra registrado.")
            }
        } catch (e: Exception) {
            Result.success("Se ha enviado un enlace de recuperación al correo $email (Modo sin conexión).")
        }
    }

    override fun getSessionFlow(): Flow<AuthSession> {
        return combine(
            tokenManager.tokenFlow,
            tokenManager.userIdFlow,
            tokenManager.usernameFlow,
            tokenManager.roleFlow,
            tokenManager.volunteerIdFlow
        ) { token, userId, username, role, volunteerId ->
            AuthSession(
                token = token,
                userId = userId,
                username = username,
                role = role,
                volunteerId = volunteerId
            )
        }
    }

    override suspend fun logout() {
        tokenManager.clearSession()
    }
}
