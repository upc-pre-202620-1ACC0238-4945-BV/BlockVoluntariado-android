package com.example.blockvoluntariado.feature.authOnboarding.infrastructure.remote

import com.example.blockvoluntariado.feature.authOnboarding.infrastructure.remote.dto.AuthenticatedUserResponseDto
import com.example.blockvoluntariado.feature.authOnboarding.infrastructure.remote.dto.MessageResponseDto
import com.example.blockvoluntariado.feature.authOnboarding.infrastructure.remote.dto.PasswordRecoveryRequestDto
import com.example.blockvoluntariado.feature.authOnboarding.infrastructure.remote.dto.SignInRequestDto
import com.example.blockvoluntariado.feature.authOnboarding.infrastructure.remote.dto.SignUpRequestDto
import com.example.blockvoluntariado.feature.authOnboarding.infrastructure.remote.dto.UserResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("v1/auth/login")
    suspend fun login(
        @Body request: SignInRequestDto
    ): Response<AuthenticatedUserResponseDto>

    @POST("v1/auth/register/student")
    suspend fun registerStudent(
        @Body request: SignUpRequestDto
    ): Response<UserResponseDto>

    @POST("v1/auth/register/ong")
    suspend fun registerOrganization(
        @Body request: SignUpRequestDto
    ): Response<UserResponseDto>

    @POST("v1/auth/password-recovery")
    suspend fun passwordRecovery(
        @Body request: PasswordRecoveryRequestDto
    ): Response<MessageResponseDto>
}
