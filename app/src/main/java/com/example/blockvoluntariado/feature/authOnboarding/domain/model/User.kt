package com.example.blockvoluntariado.feature.authOnboarding.domain.model

data class User(
    val id: Long,
    val email: String,
    val token: String = "",
    val roles: List<String> = listOf("ROLE_STUDENT")
) {
    val isStudent: Boolean
        get() = roles.contains("ROLE_STUDENT")

    val isOrganization: Boolean
        get() = roles.contains("ROLE_ORGANIZATION")
}

data class AuthSession(
    val token: String? = null,
    val userId: Long? = null,
    val username: String? = null,
    val role: String? = null,
    val volunteerId: Long? = null
) {
    val isLoggedIn: Boolean
        get() = !token.isNullOrBlank() && userId != null && userId > 0
}
