package com.example.blockvoluntariado.feature.notifications.presentation.preferences

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.blockvoluntariado.core.ui.west
import com.example.blockvoluntariado.feature.notifications.presentation.inbox.NotificationsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationPreferencesScreen(
    viewModel: NotificationsViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var pushEnabled by remember { mutableStateOf(true) }
    var emailEnabled by remember { mutableStateOf(true) }
    var causeAlerts by remember { mutableStateOf(true) }

    LaunchedEffect(uiState.preferences) {
        uiState.preferences?.let {
            pushEnabled = it.pushEnabled
            emailEnabled = it.emailEnabled
            causeAlerts = it.causeAlerts
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Preferencias de Alertas", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = west, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Configura cómo y cuándo deseas enterarte de cambios en tus postulaciones, actividades de campo y certificados digitales emitidos.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            PreferenceToggleRow(
                title = "Notificaciones Push",
                description = "Alertas en tiempo real sobre aprobación de postulaciones y recordatorios",
                isChecked = pushEnabled,
                onCheckedChange = { pushEnabled = it }
            )

            PreferenceToggleRow(
                title = "Notificaciones por Correo",
                description = "Resúmenes semanales y constancias formales enviadas a tu email",
                isChecked = emailEnabled,
                onCheckedChange = { emailEnabled = it }
            )

            PreferenceToggleRow(
                title = "Alertas por Causas de Interés",
                description = "Novedades inmediatas cuando se publiquen voluntariados acordes a tu perfil",
                isChecked = causeAlerts,
                onCheckedChange = { causeAlerts = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.updatePreferences(emailEnabled, pushEnabled, causeAlerts) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                enabled = !uiState.isUpdatingPreferences
            ) {
                if (uiState.isUpdatingPreferences) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(2.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Guardar Preferencias")
                }
            }
        }
    }
}

@Composable
fun PreferenceToggleRow(
    title: String,
    description: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Switch(
                checked = isChecked,
                onCheckedChange = onCheckedChange
            )
        }
    }
}
