package com.example.blockvoluntariado.feature.authOnboarding.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blockvoluntariado.feature.authOnboarding.domain.usecase.GetSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getSessionUseCase: GetSessionUseCase
) : ViewModel() {

    fun checkAuthStatus(
        onAuthenticated: () -> Unit,
        onUnauthenticated: () -> Unit
    ) {
        viewModelScope.launch {
            // Short splash delay for smooth branding display
            delay(1200)
            val session = getSessionUseCase().first()
            if (session.isLoggedIn) {
                onAuthenticated()
            } else {
                onUnauthenticated()
            }
        }
    }
}
