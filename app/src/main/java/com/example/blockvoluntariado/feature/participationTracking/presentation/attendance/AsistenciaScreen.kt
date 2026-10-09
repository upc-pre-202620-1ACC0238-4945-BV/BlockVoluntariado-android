package com.example.blockvoluntariado.feature.participationTracking.presentation.attendance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.blockvoluntariado.core.ui.west
import com.example.blockvoluntariado.feature.participationTracking.domain.model.AsistenciaParticipante
import com.example.blockvoluntariado.feature.participationTracking.domain.model.EstadoActividad
import com.example.blockvoluntariado.feature.participationTracking.presentation.activities.ActividadStatusBadge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsistenciaScreen(
    viewModel: AsistenciaViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    var showConfirmCompleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.feedbackMessage) {
        uiState.feedbackMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearFeedback()
        }
    }

    if (showConfirmCompleteDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmCompleteDialog = false },
            title = { Text("Finalizar Jornada") },
            text = {
                Text("¿Confirmas que la actividad ha culminado? Esto acreditará oficialmente las horas de voluntariado y emitirá el registro de participación.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showConfirmCompleteDialog = false
                        viewModel.completeActividad()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                ) {
                    Text("Confirmar y Acreditar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmCompleteDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Control de Asistencia", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = west,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            Surface(
                tonalElevation = 8.dp,
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { viewModel.saveAttendance() },
                        enabled = !uiState.isSaving && !uiState.isLoading,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        if (uiState.isSaving) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Guardando...")
                        } else {
                            Text("Guardar Asistencias en Campo", fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            val actividad = uiState.actividad
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header activity info
                if (actividad != null) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = actividad.titulo,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.weight(1f)
                                    )
                                    ActividadStatusBadge(actividad.status)
                                }

                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Lugar: ${actividad.lugar}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "Fecha: ${actividad.fechaActividad}",
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                Spacer(modifier = Modifier.height(14.dp))

                                // State control buttons
                                when (actividad.status) {
                                    EstadoActividad.PLANIFICADA -> {
                                        Button(
                                            onClick = { viewModel.startActividad() },
                                            modifier = Modifier.fillMaxWidth(),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color(0xFFE65100)
                                            ),
                                            shape = RoundedCornerShape(10.dp)
                                        ) {
                                            Text("Iniciar Actividad (Pase de Lista)")
                                        }
                                    }
                                    EstadoActividad.EN_CURSO -> {
                                        Button(
                                            onClick = { showConfirmCompleteDialog = true },
                                            modifier = Modifier.fillMaxWidth(),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color(0xFF2E7D32)
                                            ),
                                            shape = RoundedCornerShape(10.dp)
                                        ) {
                                            Text("Finalizar Jornada y Acreditar Horas")
                                        }
                                    }
                                    EstadoActividad.COMPLETADA -> {
                                        Surface(
                                            color = Color(0xFFE8F5E9),
                                            shape = RoundedCornerShape(8.dp),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text(
                                                text = "✓ Actividad culminada con éxito y horas certificadas.",
                                                color = Color(0xFF2E7D32),
                                                fontWeight = FontWeight.SemiBold,
                                                modifier = Modifier.padding(12.dp)
                                            )
                                        }
                                    }
                                    EstadoActividad.CANCELADA -> {}
                                }
                            }
                        }
                    }
                }

                item {
                    Text(
                        text = "Participantes (${uiState.participants.size})",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                items(uiState.participants, key = { it.volunteerId }) { participant ->
                    ParticipantAttendanceCard(
                        participant = participant,
                        isEditable = actividad?.status != EstadoActividad.COMPLETADA,
                        onToggleAttendance = { isChecked ->
                            viewModel.toggleAttendance(participant.volunteerId, isChecked)
                        },
                        onHoursChanged = { hours ->
                            viewModel.updateHours(participant.volunteerId, hours)
                        },
                        onNotesChanged = { notes ->
                            viewModel.updateNotes(participant.volunteerId, notes)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ParticipantAttendanceCard(
    participant: AsistenciaParticipante,
    isEditable: Boolean,
    onToggleAttendance: (Boolean) -> Unit,
    onHoursChanged: (Int) -> Unit,
    onNotesChanged: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (participant.isPresent) MaterialTheme.colorScheme.surfaceVariant
            else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = participant.volunteerName,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "ID: ${participant.volunteerId} | Postulación #${participant.postulacionId}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = if (participant.isPresent) "Presente" else "Ausente",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (participant.isPresent) Color(0xFF2E7D32) else MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Switch(
                        checked = participant.isPresent,
                        onCheckedChange = { if (isEditable) onToggleAttendance(it) },
                        enabled = isEditable
                    )
                }
            }

            if (participant.isPresent) {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Horas a certificar:",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        OutlinedButton(
                            onClick = {
                                if (isEditable && participant.certifiedHours > 1) {
                                    onHoursChanged(participant.certifiedHours - 1)
                                }
                            },
                            enabled = isEditable && participant.certifiedHours > 1,
                            modifier = Modifier.size(36.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("-", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }

                        Text(
                            text = "${participant.certifiedHours} hrs",
                            modifier = Modifier.padding(horizontal = 12.dp),
                            fontWeight = FontWeight.Bold
                        )

                        OutlinedButton(
                            onClick = {
                                if (isEditable && participant.certifiedHours < 12) {
                                    onHoursChanged(participant.certifiedHours + 1)
                                }
                            },
                            enabled = isEditable && participant.certifiedHours < 12,
                            modifier = Modifier.size(36.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("+", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = participant.supervisorNotes ?: "",
                    onValueChange = { if (isEditable) onNotesChanged(it) },
                    enabled = isEditable,
                    label = { Text("Notas de supervisión / desempeño") },
                    placeholder = { Text("Ej. Excelente puntualidad y liderazgo") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true
                )
            }
        }
    }
}
