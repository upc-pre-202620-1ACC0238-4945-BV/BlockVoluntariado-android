package com.example.blockvoluntariado.core.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "auth_prefs")

@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private val KEY_TOKEN = stringPreferencesKey("jwt_token")
        private val KEY_USER_ID = longPreferencesKey("user_id")
        private val KEY_VOLUNTEER_ID = longPreferencesKey("volunteer_id")
        private val KEY_USERNAME = stringPreferencesKey("username")
        private val KEY_ROLE = stringPreferencesKey("user_role")
    }

    // In-memory cache for fast synchronous access in OkHttp interceptor
    @Volatile
    private var cachedToken: String? = null

    val tokenFlow: Flow<String?> = context.dataStore.data.map { prefs ->
        val token = prefs[KEY_TOKEN]
        cachedToken = token
        token
    }

    val userIdFlow: Flow<Long?> = context.dataStore.data.map { prefs ->
        prefs[KEY_USER_ID]
    }

    val volunteerIdFlow: Flow<Long?> = context.dataStore.data.map { prefs ->
        prefs[KEY_VOLUNTEER_ID]
    }

    val usernameFlow: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[KEY_USERNAME]
    }

    val roleFlow: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[KEY_ROLE]
    }

    fun getCachedToken(): String? = cachedToken

    suspend fun saveSession(
        token: String,
        userId: Long,
        username: String,
        role: String = "ROLE_STUDENT",
        volunteerId: Long? = null
    ) {
        cachedToken = token
        context.dataStore.edit { prefs ->
            prefs[KEY_TOKEN] = token
            prefs[KEY_USER_ID] = userId
            prefs[KEY_USERNAME] = username
            prefs[KEY_ROLE] = role
            if (volunteerId != null) {
                prefs[KEY_VOLUNTEER_ID] = volunteerId
            }
        }
    }

    suspend fun saveVolunteerId(volunteerId: Long) {
        context.dataStore.edit { prefs ->
            prefs[KEY_VOLUNTEER_ID] = volunteerId
        }
    }

    suspend fun getToken(): String? {
        return cachedToken ?: context.dataStore.data.map { it[KEY_TOKEN] }.firstOrNull().also {
            cachedToken = it
        }
    }

    suspend fun clearSession() {
        cachedToken = null
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
