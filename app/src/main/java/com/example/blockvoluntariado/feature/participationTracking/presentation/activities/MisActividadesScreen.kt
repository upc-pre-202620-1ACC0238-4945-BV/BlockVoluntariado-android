package com.example.blockvoluntariado.feature.participationTracking.presentation.activities

import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.blockvoluntariado.core.ui.calendar_today
import com.example.blockvoluntariado.core.ui.location_on
import com.example.blockvoluntariado.core.ui.person
import com.example.blockvoluntariado.feature.participationTracking.domain.model.Actividad
import com.example.blockvoluntariado.feature.participationTracking.domain.model.EstadoActividad

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisActividadesScreen(
    viewModel: MisActividadesViewModel = hiltViewModel(),
    onNavigateToAttendance: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearMessages()
        }
    }

    LaunchedEffect(uiState.successMessage) {
        uiState.successMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearMessages()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Actividades y Asistencia",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Filters
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = uiState.selectedFilter == null,
                    onClick = { viewModel.setFilter(null) },
                    label = { Text("Todas") }
                )
                FilterChip(
                    selected = uiState.selectedFilter == EstadoActividad.PLANIFICADA,
                    onClick = { viewModel.setFilter(EstadoActividad.PLANIFICADA) },
                    label = { Text("Planificadas") }
                )
                FilterChip(
                    selected = uiState.selectedFilter == EstadoActividad.EN_CURSO,
                    onClick = { viewModel.setFilter(EstadoActividad.EN_CURSO) },
                    label = { Text("En Curso") }
                )
                FilterChip(
                    selected = uiState.selectedFilter == EstadoActividad.COMPLETADA,
                    onClick = { viewModel.setFilter(EstadoActividad.COMPLETADA) },
                    label = { Text("Completadas") }
                )
            }

            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                val filteredList = uiState.actividades.filter { act ->
                    uiState.selectedFilter == null || act.status == uiState.selectedFilter
                }

                if (filteredList.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No tienes actividades registradas en esta categoría.",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(filteredList, key = { it.id }) { actividad ->
                            ActividadCard(
                                actividad = actividad,
                                onOpenAttendance = { onNavigateToAttendance(actividad.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ActividadCard(
    actividad: Actividad,
    onOpenAttendance: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
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

            Spacer(modifier = Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = calendar_today,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = actividad.fechaActividad,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = location_on,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = actividad.lugar,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = person,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "${actividad.asistencias.size} voluntarios inscritos",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = onOpenAttendance,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = when (actividad.status) {
                        EstadoActividad.PLANIFICADA -> "Abrir Control de Asistencia"
                        EstadoActividad.EN_CURSO -> "Gestionar en Campo (En Curso)"
                        EstadoActividad.COMPLETADA -> "Ver Resumen de Asistencias"
                        EstadoActividad.CANCELADA -> "Ver Detalles"
                    }
                )
            }
        }
    }
}

@Composable
fun ActividadStatusBadge(status: EstadoActividad) {
    val (backgroundColor, textColor) = when (status) {
        EstadoActividad.PLANIFICADA -> Color(0xFFE3F2FD) to Color(0xFF1565C0)
        EstadoActividad.EN_CURSO -> Color(0xFFFFF3E0) to Color(0xFFE65100)
        EstadoActividad.COMPLETADA -> Color(0xFFE8F5E9) to Color(0xFF2E7D32)
        EstadoActividad.CANCELADA -> Color(0xFFFFEBEE) to Color(0xFFC62828)
    }

    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = status.displayName,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}
