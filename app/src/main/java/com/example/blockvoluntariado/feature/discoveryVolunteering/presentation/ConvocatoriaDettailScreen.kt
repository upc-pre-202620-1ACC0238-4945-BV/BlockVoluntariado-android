package com.example.blockvoluntariado.feature.discoveryVolunteering.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ConvocatoriaDetailScreen(
    convocatoriaId: Int,
    viewModel: HomeViewModel = hiltViewModel()
) {
    // Cuando la pantalla inicia o el ID cambia, solicita cargar la convocatoria por ID
    LaunchedEffect(convocatoriaId) {
        viewModel.selectConvocatoriaById(convocatoriaId)
    }

    val state = viewModel.state.collectAsStateWithLifecycle().value

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                state.isDetailLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                state.selectedConvocatoria != null -> {
                    val convocatoria = state.selectedConvocatoria
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = convocatoria.title,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = convocatoria.description,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Distrito: ${convocatoria.ubicacion.district ?: "No especificado"}"
                        )
                        Text(
                            text = "Vacantes: ${convocatoria.occupiedVacancies} / ${convocatoria.totalVacancies}"
                        )
                    }
                }
                state.errorMessage != null -> {
                    Text(
                        text = state.errorMessage,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}