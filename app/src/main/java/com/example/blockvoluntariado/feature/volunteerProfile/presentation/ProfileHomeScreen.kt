package com.example.blockvoluntariado.feature.volunteerProfile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ProfileHomeScreen(
    volunteerId: Int = 1,
    viewModel: ProfileHomeViewModel = hiltViewModel(), // Usar ProfileViewModel
    onNavigateToProfileEdit: (Int) -> Unit,
    onNavigateToPreferencesEdit: (Int) -> Unit,
    onNavigateToCertificates: () -> Unit = {},
    onNavigateToNotifications: () -> Unit = {}
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    LaunchedEffect(volunteerId) { viewModel.loadVolunteer(volunteerId) }

    Box(

        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA)),
        contentAlignment = Alignment.Center

    ) {
        when {
            state.isLoading -> CircularProgressIndicator()
            state.volunteer != null -> {
                val volunteer = state.volunteer
                InformationList(
                    volunteer = volunteer,
                    preferences = state.preferences ?: volunteer.preferences,
                    onEditProfile = { onNavigateToProfileEdit(volunteerId) },
                    onEditPreferences = { onNavigateToPreferencesEdit(volunteerId) },
                    onNavigateToCertificates = onNavigateToCertificates,
                    onNavigateToNotifications = onNavigateToNotifications,
                    modifier = Modifier.verticalScroll(rememberScrollState())
                )
            }
            state.errorMessage != null -> Text(state.errorMessage, color = MaterialTheme.colorScheme.error)
            else -> Text("No hay información del voluntario.")
        }
    }
}