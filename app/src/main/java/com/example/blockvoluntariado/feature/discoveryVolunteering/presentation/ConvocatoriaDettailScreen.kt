
package com.example.blockvoluntariado.feature.discoveryVolunteering.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.blockvoluntariado.core.ui.calendar_today
import com.example.blockvoluntariado.core.ui.location_on
import com.example.blockvoluntariado.core.ui.person
import com.example.blockvoluntariado.core.ui.west
import com.example.blockvoluntariado.feature.discoveryVolunteering.domain.valueObjects.EstadoConvocatoria

private val DetailBlue = Color(0xFF014F92)
private val DetailOrange = Color(0xFFFE6802)
private val DetailBackground = Color(0xFFF5F7FA)
private val DetailText = Color(0xFF17202A)
private val DetailSecondary = Color(0xFF667085)
private val DetailBorder = Color(0xFFE5EAF0)
private val DetailGreen = Color(0xFF218653)
private val DetailRed = Color(0xFFB42318)

@Composable
fun ConvocatoriaDetailScreen(
    convocatoriaId: Int,
    onBack: (() -> Unit)? = null,
    viewModel: HomeViewModel = hiltViewModel()
) {
    LaunchedEffect(convocatoriaId) {
        viewModel.selectConvocatoriaById(convocatoriaId)
    }

    val state = viewModel.state.collectAsStateWithLifecycle().value

    Scaffold(
        containerColor = DetailBackground
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Encabezado
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    onClick = { onBack?.invoke() },
                    enabled = onBack != null,
                    shape = CircleShape,
                    color = DetailBackground
                ) {
                    Box(
                        modifier = Modifier.size(44.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = west,
                            contentDescription = "Volver",
                            tint = DetailBlue,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }

                Spacer(Modifier.width(14.dp))

                Column {
                    Text(
                        text = "DETALLES",
                        color = DetailOrange,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp
                    )

                    Text(
                        text = "Convocatoria",
                        color = DetailBlue,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            when {
                state.isDetailLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator(
                                color = DetailOrange
                            )
                            Spacer(Modifier.height(12.dp))
                            Text(
                                text = "Cargando convocatoria...",
                                color = DetailSecondary
                            )
                        }
                    }
                }

                state.selectedConvocatoria != null -> {
                    val convocatoria = state.selectedConvocatoria

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Tarjeta principal
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 2.dp
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp)
                            ) {
                                StatusLabel(convocatoria.status)

                                Spacer(Modifier.height(16.dp))

                                Text(
                                    text = convocatoria.title,
                                    color = DetailBlue,
                                    fontSize = 25.sp,
                                    lineHeight = 32.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(Modifier.height(12.dp))

                                Text(
                                    text = convocatoria.description.ifBlank {
                                        "No hay una descripción disponible."
                                    },
                                    color = DetailSecondary,
                                    fontSize = 15.sp,
                                    lineHeight = 24.sp
                                )

                                Spacer(Modifier.height(20.dp))

                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = DetailBlue.copy(alpha = 0.06f)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(14.dp),
                                        verticalAlignment =
                                            Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = person,
                                            contentDescription = "Vacantes",
                                            tint = DetailOrange,
                                            modifier = Modifier.size(25.dp)
                                        )

                                        Spacer(Modifier.width(12.dp))

                                        Column {
                                            Text(
                                                text = "Participación voluntaria",
                                                color = DetailBlue,
                                                fontWeight = FontWeight.SemiBold,
                                                fontSize = 14.sp
                                            )
                                            Text(
                                                text = "Contribuye y genera un impacto positivo",
                                                color = DetailSecondary,
                                                fontSize = 12.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }


                        DetailSection(title = "Ubicación") {
                            DetailInfoRow(
                                icon = {
                                    Icon(
                                        imageVector = location_on,
                                        contentDescription = null,
                                        tint = DetailOrange,
                                        modifier = Modifier.size(23.dp)
                                    )
                                },
                                title = "Distrito",
                                value = convocatoria.ubicacion.district
                                    ?.takeIf { it.isNotBlank() }
                                    ?: "No especificado"
                            )

                            DetailDivider()

                            DetailInfoRow(
                                icon = {
                                    Icon(
                                        imageVector = location_on,
                                        contentDescription = null,
                                        tint = DetailBlue,
                                        modifier = Modifier.size(23.dp)
                                    )
                                },
                                title = "Dirección",
                                value = convocatoria.ubicacion.address
                                    ?.takeIf { it.isNotBlank() }
                                    ?: "No especificada"
                            )
                        }

                        // Fecha y horario
                        DetailSection(title = "Fecha y horario") {
                            DetailInfoRow(
                                icon = {
                                    Icon(
                                        imageVector = calendar_today,
                                        contentDescription = null,
                                        tint = DetailOrange,
                                        modifier = Modifier.size(23.dp)
                                    )
                                },
                                title = "Fecha de inicio",
                                value = convocatoria.horario.startDate
                                    ?.toString() ?: "No especificada"
                            )

                            DetailDivider()

                            DetailInfoRow(
                                icon = {
                                    Icon(
                                        imageVector = calendar_today,
                                        contentDescription = null,
                                        tint = DetailBlue,
                                        modifier = Modifier.size(23.dp)
                                    )
                                },
                                title = "Fecha de finalización",
                                value = convocatoria.horario.endDate
                                    ?.toString() ?: "No especificada"
                            )

                            DetailDivider()

                            DetailInfoRow(
                                icon = {
                                    Icon(
                                        imageVector = calendar_today,
                                        contentDescription = null,
                                        tint = DetailOrange,
                                        modifier = Modifier.size(23.dp)
                                    )
                                },
                                title = "Horario",
                                value = listOfNotNull(
                                    convocatoria.horario.startTime,
                                    convocatoria.horario.endTime
                                )
                                    .filter { it.isNotBlank() }
                                    .joinToString(" - ")
                                    .ifBlank { "No especificado" }
                            )
                        }


                        DetailSection(title = "Vacantes disponibles") {
                            val total = convocatoria.totalVacancies
                                .toIntOrNull() ?: 0
                            val ocupadas = convocatoria.occupiedVacancies
                                .toIntOrNull() ?: 0
                            val disponibles = (total - ocupadas).coerceAtLeast(0)

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement =
                                    Arrangement.spacedBy(10.dp)
                            ) {
                                VacancyCard(
                                    modifier = Modifier.weight(1f),
                                    number = total.toString(),
                                    label = "Total",
                                    color = DetailBlue
                                )

                                VacancyCard(
                                    modifier = Modifier.weight(1f),
                                    number = ocupadas.toString(),
                                    label = "Ocupadas",
                                    color = DetailOrange
                                )

                                VacancyCard(
                                    modifier = Modifier.weight(1f),
                                    number = disponibles.toString(),
                                    label = "Disponibles",
                                    color = DetailGreen
                                )
                            }

                            Spacer(Modifier.height(12.dp))

                            Text(
                                text = "Las vacantes disponibles se calculan restando las ocupadas del total.",
                                color = DetailSecondary,
                                fontSize = 12.sp,
                                lineHeight = 18.sp
                            )
                        }

                        Spacer(Modifier.height(4.dp))
                    }
                }

                state.errorMessage != null -> {
                    MessageState(
                        title = "No se pudo cargar",
                        message = state.errorMessage,
                        onRetry = {
                            viewModel.selectConvocatoriaById(convocatoriaId)
                        }
                    )
                }

                else -> {
                    MessageState(
                        title = "Convocatoria no encontrada",
                        message = "No encontramos información para esta convocatoria.",
                        onRetry = {
                            viewModel.selectConvocatoriaById(convocatoriaId)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun StatusLabel(status: EstadoConvocatoria) {
    val (label, color) = when (status) {
        EstadoConvocatoria.PUBLICADA -> "PUBLICADA" to DetailGreen
        EstadoConvocatoria.CERRADA -> "CERRADA" to DetailRed
        EstadoConvocatoria.BORRADOR -> "BORRADOR" to DetailSecondary
    }

    Surface(
        shape = RoundedCornerShape(50),
        color = color.copy(alpha = 0.10f)
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 7.dp
            ),
            color = color,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.6.sp
        )
    }
}

@Composable
private fun DetailSection(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Text(
                text = title,
                color = DetailBlue,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(16.dp))
            content()
        }
    }
}

@Composable
private fun DetailInfoRow(
    icon: @Composable () -> Unit,
    title: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = DetailBackground,
            modifier = Modifier.size(44.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                icon()
            }
        }

        Spacer(Modifier.width(12.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 2.dp)
        ) {
            Text(
                text = title,
                color = DetailSecondary,
                fontSize = 12.sp
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = value,
                color = DetailText,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
private fun DetailDivider() {
    Spacer(Modifier.height(14.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(DetailBorder)
    )
    Spacer(Modifier.height(14.dp))
}

@Composable
private fun VacancyCard(
    modifier: Modifier = Modifier,
    number: String,
    label: String,
    color: Color
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        color = color.copy(alpha = 0.08f)
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = 6.dp,
                vertical = 16.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = number,
                color = color,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = label,
                color = DetailSecondary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun MessageState(
    title: String,
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            color = DetailBlue,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(10.dp))

        Text(
            text = message,
            color = DetailSecondary,
            fontSize = 14.sp,
            lineHeight = 21.sp
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(
                containerColor = DetailOrange
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text("Intentar nuevamente")
        }
    }
}
