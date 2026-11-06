package com.example.blockvoluntariado.feature.authOnboarding.infrastructure.remote.dto

import com.google.gson.annotations.SerializedName

data class SignInRequestDto(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)

data class SignUpRequestDto(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

data class PasswordRecoveryRequestDto(
    @SerializedName("email") val email: String
)

data class AuthenticatedUserResponseDto(
    @SerializedName("id") val id: Long,
    @SerializedName("username") val username: String,
    @SerializedName("token") val token: String,
    @SerializedName("roles") val roles: List<String>? = listOf("ROLE_STUDENT")
)

data class UserResponseDto(
    @SerializedName("id") val id: Long,
    @SerializedName("username") val username: String,
    @SerializedName("roles") val roles: List<String>? = listOf("ROLE_STUDENT")
)

data class MessageResponseDto(
    @SerializedName("message") val message: String
)
