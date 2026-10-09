package com.example.blockvoluntariado.feature.authOnboarding.domain.repository

import com.example.blockvoluntariado.feature.authOnboarding.domain.model.AuthSession
import com.example.blockvoluntariado.feature.authOnboarding.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(username: String, password: String): Result<User>
    suspend fun registerStudent(email: String, password: String): Result<User>
    suspend fun registerOrganization(email: String, password: String): Result<User>
    suspend fun recoverPassword(email: String): Result<String>
    fun getSessionFlow(): Flow<AuthSession>
    suspend fun logout()
}
