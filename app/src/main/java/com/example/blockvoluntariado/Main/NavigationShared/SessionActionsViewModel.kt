
package com.example.blockvoluntariado.Main.NavigationShared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blockvoluntariado.core.storage.TokenManager
import com.example.blockvoluntariado.feature.authOnboarding.domain.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionActionsViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    tokenManager: TokenManager
) : ViewModel() {

    val currentVolunteerId = combine(
        tokenManager.volunteerIdFlow,
        tokenManager.userIdFlow
    ) { volunteerId, userId ->

        (volunteerId ?: userId ?: 1L)
            .coerceIn(1L, Int.MAX_VALUE.toLong())
            .toInt()

    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        1
    )

    private val _isSigningOut = MutableStateFlow(false)
    val isSigningOut = _isSigningOut.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun signOut(onComplete: () -> Unit) {

        if (_isSigningOut.value) return

        viewModelScope.launch {

            _isSigningOut.value = true
            _error.value = null

            try {
                logoutUseCase()
                onComplete()
            } catch (exception: Exception) {

                if (exception is CancellationException) {
                    throw exception
                }

                _error.value =
                    "No fue posible cerrar la sesión."
            } finally {
                _isSigningOut.value = false
            }
        }
    }
}
