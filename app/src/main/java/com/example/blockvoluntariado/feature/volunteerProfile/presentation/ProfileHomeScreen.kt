
package com.example.blockvoluntariado.feature.volunteerProfile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ProfileHomeScreen(
    volunteerId: Int = 1,
    viewModel: ProfileHomeViewModel = hiltViewModel(),

    onNavigateToProfileEdit: (Int) -> Unit,
    onNavigateToPreferencesEdit: (Int) -> Unit,

    onNavigateToCertificates: () -> Unit = {},
    onNavigateToNotifications: () -> Unit = {},

    // Nuevos parámetros para cerrar sesión
    onLogout: () -> Unit,
    isSigningOut: Boolean = false,
    logoutError: String? = null

) {

    // Obtener los datos actuales del voluntario
    val state = viewModel.state.collectAsStateWithLifecycle().value

    LaunchedEffect(volunteerId) {
        viewModel.loadVolunteer(volunteerId)
    }

    // Controlar la ventana de confirmación
    var showLogoutConfirmation by remember {
        mutableStateOf(false)
    }

    // Ventana para confirmar el cierre de sesión
    if (showLogoutConfirmation) {

        AlertDialog(
            onDismissRequest = {
                showLogoutConfirmation = false
            },

            title = {
                Text("¿Cerrar sesión?")
            },

            text = {
                Text(
                    "¿Estás seguro de que deseas cerrar tu sesión? " +
                            "Podrás iniciar sesión nuevamente con otra cuenta."
                )
            },

            confirmButton = {

                TextButton(
                    onClick = {
                        showLogoutConfirmation = false
                        onLogout()
                    }
                ) {
                    Text("Salir")
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        showLogoutConfirmation = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Contenedor principal de la pantalla
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA))
    ) {

        // Contenido original del perfil
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color(0xFFF5F7FA)),

            contentAlignment = Alignment.Center
        ) {

            when {

                // Mostrar indicador mientras cargan los datos
                state.isLoading -> {

                    CircularProgressIndicator()
                }

                // Mostrar información del voluntario
                state.volunteer != null -> {

                    val volunteer = state.volunteer

                    InformationList(
                        volunteer = volunteer,

                        preferences = state.preferences
                            ?: volunteer.preferences,

                        onEditProfile = {
                            onNavigateToProfileEdit(volunteerId)
                        },

                        onEditPreferences = {
                            onNavigateToPreferencesEdit(volunteerId)
                        },

                        onNavigateToCertificates =
                            onNavigateToCertificates,

                        onNavigateToNotifications =
                            onNavigateToNotifications,

                        modifier = Modifier.verticalScroll(
                            rememberScrollState()
                        )
                    )
                }

                // Mostrar error al obtener el perfil
                state.errorMessage != null -> {

                    Text(
                        text = state.errorMessage,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                // No existen datos para mostrar
                else -> {

                    Text(
                        text = "No hay información del voluntario."
                    )
                }
            }
        }

        // Mostrar error si falla el cierre de sesión
        if (logoutError != null) {

            Text(
                text = logoutError,

                color = MaterialTheme.colorScheme.error,

                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
            )
        }

        // Botón de cerrar sesión
        OutlinedButton(
            onClick = {
                showLogoutConfirmation = true
            },

            enabled = !isSigningOut,

            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 10.dp
                )
        ) {

            Text(
                text = if (isSigningOut) {
                    "Cerrando sesión..."
                } else {
                    "Cerrar sesión"
                }
            )
        }
    }
}
