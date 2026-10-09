
package com.example.blockvoluntariado.feature.volunteerProfile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

private val PreferenceBlue = Color(0xFF014F92)
private val PreferenceOrange = Color(0xFFFE6802)
private val PreferenceBackground = Color(0xFFF5F7FA)
private val PreferenceBorder = Color(0xFFD9E1E8)
private val PreferenceText = Color(0xFF17202A)
private val PreferenceSecondaryText = Color(0xFF667085)
private val PreferenceSuccess = Color(0xFF16A34A)
private val PreferenceError = Color(0xFFDC2626)

@Composable
fun DetailPreference(
    volunteerId: Int,
    viewModel: ProfileHomeViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val preferences = state.preferences ?: state.volunteer?.preferences

    var causes by remember {
        mutableStateOf("")
    }
    var availability by remember {
        mutableStateOf("")
    }
    var modality by remember {
        mutableStateOf("")
    }

    LaunchedEffect(volunteerId) {
        if (state.volunteer == null) {
            viewModel.loadVolunteer(volunteerId)
        }
    }

    LaunchedEffect(preferences) {
        if (preferences != null) {
            causes = preferences.causes.joinToString(", ")
            availability = preferences.availability
            modality = preferences.preferredModality
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PreferenceBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Encabezado
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Mis preferencias",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = PreferenceBlue
            )

            Text(
                text = "Personaliza tu experiencia de voluntariado.",
                style = MaterialTheme.typography.bodyMedium,
                color = PreferenceSecondaryText
            )
        }

        // Formulario
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            border = androidx.compose.foundation.BorderStroke(
                1.dp,
                PreferenceBorder
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Text(
                    text = "Información de preferencias",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = PreferenceBlue
                )

                // Causas de interés
                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Causas de interés",
                        style = MaterialTheme.typography.labelLarge,
                        color = PreferenceText,
                        fontWeight = FontWeight.SemiBold
                    )

                    OutlinedTextField(
                        value = causes,
                        onValueChange = { causes = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text("Educación, medio ambiente...")
                        },
                        supportingText = {
                            Text("Separa las causas mediante comas.")
                        },
                        minLines = 2,
                        shape = RoundedCornerShape(10.dp),
                        colors = preferenceFieldColors()
                    )
                }

                // Disponibilidad
                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Disponibilidad",
                        style = MaterialTheme.typography.labelLarge,
                        color = PreferenceText,
                        fontWeight = FontWeight.SemiBold
                    )

                    OutlinedTextField(
                        value = availability,
                        onValueChange = { availability = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text("Ej. fines de semana")
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        colors = preferenceFieldColors()
                    )
                }

                // Modalidad
                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Modalidad preferida",
                        style = MaterialTheme.typography.labelLarge,
                        color = PreferenceText,
                        fontWeight = FontWeight.SemiBold
                    )

                    OutlinedTextField(
                        value = modality,
                        onValueChange = { modality = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text("Presencial, virtual o híbrida")
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        colors = preferenceFieldColors()
                    )
                }
            }
        }

        // Mensajes de estado
        state.errorMessage?.let { message ->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFEF2F2)
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = message,
                    modifier = Modifier.padding(14.dp),
                    color = PreferenceError,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        state.successMessage?.let { message ->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF0FDF4)
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = message,
                    modifier = Modifier.padding(14.dp),
                    color = PreferenceSuccess,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        // Acciones
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = onBack,
                modifier = Modifier.weight(1f),
                enabled = !state.isSaving
            ) {
                Text(
                    text = "Cancelar",
                    color = PreferenceBlue,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Button(
                onClick = {
                    viewModel.savePreferences(
                        volunteerId,
                        causes,
                        availability,
                        modality
                    )
                },
                modifier = Modifier.weight(1.5f),
                enabled = !state.isSaving,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PreferenceOrange,
                    contentColor = Color.White,
                    disabledContainerColor = PreferenceOrange.copy(
                        alpha = 0.5f
                    )
                )
            ) {
                if (state.isSaving) {
                    CircularProgressIndicator(
                        modifier = Modifier.height(20.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Guardar cambios",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun preferenceFieldColors() =
    OutlinedTextFieldDefaults.colors(
        focusedBorderColor = PreferenceBlue,
        unfocusedBorderColor = PreferenceBorder,
        focusedLabelColor = PreferenceBlue,
        cursorColor = PreferenceBlue,
        focusedTextColor = PreferenceText,
        unfocusedTextColor = PreferenceText,
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        focusedPlaceholderColor = PreferenceSecondaryText,
        unfocusedPlaceholderColor = PreferenceSecondaryText
    )
