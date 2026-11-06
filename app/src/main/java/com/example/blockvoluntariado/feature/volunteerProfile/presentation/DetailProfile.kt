
package com.example.blockvoluntariado.feature.volunteerProfile.presentation

import androidx.compose.foundation.BorderStroke
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

private val ProfileBlue = Color(0xFF014F92)
private val ProfileOrange = Color(0xFFFE6802)
private val ProfileBackground = Color(0xFFF5F7FA)
private val ProfileBorder = Color(0xFFD9E1E8)
private val ProfileText = Color(0xFF17202A)
private val ProfileSecondaryText = Color(0xFF667085)
private val ProfileSuccess = Color(0xFF16A34A)
private val ProfileError = Color(0xFFDC2626)

@Composable
fun DetailProfile(
    volunteerId: Int,
    viewModel: ProfileHomeViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    var firstName by remember {
        mutableStateOf("")
    }

    var lastName by remember {
        mutableStateOf("")
    }

    LaunchedEffect(volunteerId) {
        if (state.volunteer == null) {
            viewModel.loadVolunteer(volunteerId)
        }
    }

    LaunchedEffect(state.volunteer?.name) {
        state.volunteer?.name?.let { name ->
            firstName = name.firstName
            lastName = name.lastName
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ProfileBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Encabezado
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Editar perfil",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = ProfileBlue
            )

            Text(
                text = "Mantén tus datos personales actualizados.",
                style = MaterialTheme.typography.bodyMedium,
                color = ProfileSecondaryText
            )
        }

        // Tarjeta del formulario
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            border = BorderStroke(
                width = 1.dp,
                color = ProfileBorder
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
                    text = "Información personal",
                    style = MaterialTheme.typography.titleMedium,
                    color = ProfileBlue,
                    fontWeight = FontWeight.Bold
                )

                // Nombre
                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Nombre",
                        style = MaterialTheme.typography.labelLarge,
                        color = ProfileText,
                        fontWeight = FontWeight.SemiBold
                    )

                    OutlinedTextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text("Ingresa tu nombre")
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        colors = profileFieldColors()
                    )
                }

                // Apellidos
                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Apellidos",
                        style = MaterialTheme.typography.labelLarge,
                        color = ProfileText,
                        fontWeight = FontWeight.SemiBold
                    )

                    OutlinedTextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text("Ingresa tus apellidos")
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        colors = profileFieldColors()
                    )
                }
            }
        }

        // Mensaje de error
        state.errorMessage?.let { message ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFEF2F2)
                )
            ) {
                Text(
                    text = message,
                    modifier = Modifier.padding(14.dp),
                    color = ProfileError,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        // Mensaje de éxito
        state.successMessage?.let { message ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF0FDF4)
                )
            ) {
                Text(
                    text = message,
                    modifier = Modifier.padding(14.dp),
                    color = ProfileSuccess,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        // Botones de acción
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
                    color = ProfileBlue,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Button(
                onClick = {
                    viewModel.saveProfile(
                        volunteerId,
                        firstName.trim(),
                        lastName.trim()
                    )
                },
                modifier = Modifier.weight(1.5f),
                enabled = !state.isSaving &&
                        firstName.isNotBlank() &&
                        lastName.isNotBlank(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ProfileOrange,
                    contentColor = Color.White,
                    disabledContainerColor = ProfileOrange.copy(
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
private fun profileFieldColors() =
    OutlinedTextFieldDefaults.colors(
        focusedBorderColor = ProfileBlue,
        unfocusedBorderColor = ProfileBorder,
        focusedLabelColor = ProfileBlue,
        cursorColor = ProfileBlue,
        focusedTextColor = ProfileText,
        unfocusedTextColor = ProfileText,
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        focusedPlaceholderColor = ProfileSecondaryText,
        unfocusedPlaceholderColor = ProfileSecondaryText
    )
