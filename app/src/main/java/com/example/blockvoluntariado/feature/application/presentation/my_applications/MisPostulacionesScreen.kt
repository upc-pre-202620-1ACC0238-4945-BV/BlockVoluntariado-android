package com.example.blockvoluntariado.feature.application.presentation.my_applications

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.blockvoluntariado.feature.application.domain.model.EstadoPostulacion
import com.example.blockvoluntariado.feature.application.domain.model.Postulacion

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisPostulacionesScreen(
    viewModel: MisPostulacionesViewModel = hiltViewModel(),
    onNavigateToConvocatoriaDetail: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    var applicationToCancel by remember { mutableStateOf<Postulacion?>(null) }

    LaunchedEffect(uiState.successActionMessage) {
        uiState.successActionMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearActionMessage()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Mis Postulaciones",
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
            // Filter chips horizontally
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = uiState.selectedStatus == null,
                    onClick = { viewModel.filterByStatus(null) },
                    label = { Text("Todas") }
                )
                FilterChip(
                    selected = uiState.selectedStatus == EstadoPostulacion.PENDIENTE,
                    onClick = { viewModel.filterByStatus(EstadoPostulacion.PENDIENTE) },
                    label = { Text("Pendientes") }
                )
                FilterChip(
                    selected = uiState.selectedStatus == EstadoPostulacion.ACEPTADA,
                    onClick = { viewModel.filterByStatus(EstadoPostulacion.ACEPTADA) },
                    label = { Text("Aceptadas") }
                )
                FilterChip(
                    selected = uiState.selectedStatus == EstadoPostulacion.RECHAZADA,
                    onClick = { viewModel.filterByStatus(EstadoPostulacion.RECHAZADA) },
                    label = { Text("Rechazadas") }
                )
                FilterChip(
                    selected = uiState.selectedStatus == EstadoPostulacion.CANCELADA,
                    onClick = { viewModel.filterByStatus(EstadoPostulacion.CANCELADA) },
                    label = { Text("Canceladas") }
                )
            }

            when {
                uiState.isLoading && uiState.applications.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                uiState.filteredApplications.isEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "No tienes postulaciones en este estado",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = "Explora el catálogo de convocatorias y postula a las causas que te apasionen.",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.outline,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        items(uiState.filteredApplications, key = { it.id }) { post ->
                            PostulacionCard(
                                postulacion = post,
                                onCancelClick = { applicationToCancel = post },
                                onCardClick = { onNavigateToConvocatoriaDetail(post.convocatoriaId) }
                            )
                        }
                    }
                }
            }
        }
    }

    // Cancel confirmation dialog
    if (applicationToCancel != null) {
        AlertDialog(
            onDismissRequest = { applicationToCancel = null },
            title = { Text("¿Cancelar postulación?") },
            text = { Text("¿Estás seguro de que deseas retirar tu postulación a '${applicationToCancel!!.convocatoriaTitle}'?") },
            confirmButton = {
                Button(
                    onClick = {
                        val id = applicationToCancel!!.id
                        applicationToCancel = null
                        viewModel.cancelApplication(id)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Sí, cancelar")
                }
            },
            dismissButton = {
                TextButton(onClick = { applicationToCancel = null }) {
                    Text("Volver")
                }
            }
        )
    }
}

@Composable
fun PostulacionCard(
    postulacion: Postulacion,
    onCancelClick: () -> Unit,
    onCardClick: () -> Unit
) {
    Card(
        onClick = onCardClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = postulacion.convocatoriaTitle,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )

                StatusBadge(status = postulacion.status)
            }

            Spacer(Modifier.height(10.dp))

            Text(
                text = "Fecha de postulación: ${postulacion.appliedAt}",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (!postulacion.rejectionReason.isNullOrBlank()) {
                Spacer(Modifier.height(8.dp))
                Surface(
                    color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Motivo: ${postulacion.rejectionReason}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            if (postulacion.status == EstadoPostulacion.PENDIENTE) {
                Spacer(Modifier.height(14.dp))
                OutlinedButton(
                    onClick = onCancelClick,
                    modifier = Modifier.align(Alignment.End),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Cancelar Postulación",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
fun StatusBadge(status: EstadoPostulacion) {
    val (bgColor, textColor) = when (status) {
        EstadoPostulacion.ACEPTADA -> Color(0xFFE8F5E9) to Color(0xFF2E7D32)
        EstadoPostulacion.PENDIENTE -> Color(0xFFFFF3E0) to Color(0xFFE65100)
        EstadoPostulacion.RECHAZADA -> Color(0xFFFFEBEE) to Color(0xFFC62828)
        EstadoPostulacion.CANCELADA -> Color(0xFFECEFF1) to Color(0xFF546E7A)
    }

    Surface(
        color = bgColor,
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = status.displayName,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}
