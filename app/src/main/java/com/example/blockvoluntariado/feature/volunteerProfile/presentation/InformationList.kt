package com.example.blockvoluntariado.feature.volunteerProfile.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.blockvoluntariado.feature.volunteerProfile.domain.Volunteer
import com.example.blockvoluntariado.feature.volunteerProfile.domain.valueObjects.VolunteerPreferences

@Composable
fun InformationList(
    volunteer: Volunteer,
    preferences: VolunteerPreferences,
    onEditProfile: () -> Unit,
    onEditPreferences: () -> Unit,
    onNavigateToCertificates: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Mi información", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)

        InformationCard(title = "Información del perfil", onEdit = onEditProfile) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                InformationRow("Nombre", "${volunteer.name.firstName} ${volunteer.name.lastName}")
                InformationRow("DNI", volunteer.dniDocument)
                InformationRow("Universidad", volunteer.universityName)
                InformationRow("Código de estudiante", volunteer.studentCode)
                InformationRow("Teléfono", volunteer.phoneNumber)
            }
        }

        InformationCard(title = "Preferencias de voluntariado", onEdit = onEditPreferences) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                InformationRow("Causas", preferences.causes.ifEmpty { listOf("Sin preferencias") }.joinToString(", "))
                InformationRow("Disponibilidad", preferences.availability.ifBlank { "No especificada" })
                InformationRow("Modalidad", preferences.preferredModality.ifBlank { "No especificada" })
            }
        }

        InformationCard(title = "Mi experiencia y Logros") {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                InformationRow("Horas acumuladas", "${volunteer.accumulatedHours} horas")
                androidx.compose.material3.Button(
                    onClick = onNavigateToCertificates,
                    modifier = Modifier.fillMaxWidth(),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                ) {
                    Text("Ver Diplomas y Logros Blockchain")
                }
            }
        }
    }
}

@Composable
private fun InformationRow(label: String, value: String) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, style = MaterialTheme.typography.bodyLarge)
    }
}
