package com.example.blockvoluntariado.feature.gamificationFeedback.presentation.recognition

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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.blockvoluntariado.core.ui.calendar_today
import com.example.blockvoluntariado.core.ui.leaderboard
import com.example.blockvoluntariado.core.ui.location_on
import com.example.blockvoluntariado.feature.gamificationFeedback.domain.model.DigitalCertificate
import com.example.blockvoluntariado.feature.gamificationFeedback.domain.model.GamificationBadge
import com.example.blockvoluntariado.feature.gamificationFeedback.domain.model.VolunteerHistoryItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogrosCertificadosScreen(
    viewModel: LogrosCertificadosViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    var selectedTab by remember { mutableIntStateOf(0) }

    LaunchedEffect(uiState.statusMessage) {
        uiState.statusMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearStatusMessage()
        }
    }

    if (uiState.selectedCertificate != null) {
        CertificadoDetailDialog(
            certificate = uiState.selectedCertificate!!,
            onDismiss = { viewModel.selectCertificate(null) }
        )
    }

    if (uiState.isReviewDialogVisible && uiState.reviewTargetId != null) {
        EvaluacionDialog(
            targetName = uiState.reviewTargetName,
            onDismiss = { viewModel.closeReviewDialog() },
            onSubmit = { score, feedback ->
                viewModel.submitReview(score, feedback)
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Logros y Certificados",
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Gamification Level & Stats Card
                item {
                    val profile = uiState.profile
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF014F92))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = profile?.level ?: "Líder Social de Oro",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                    Text(
                                        text = "Nivel ${profile?.levelNumber ?: 3} en Impacto Ciudadano",
                                        color = Color(0xFFB0BEC5),
                                        fontSize = 13.sp
                                    )
                                }
                                Surface(
                                    color = Color(0xFFFE6802),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = "★ TOP VOLUNTARIO",
                                        color = Color.White,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = "${profile?.totalHours ?: 32} hrs",
                                        color = Color.White,
                                        fontWeight = FontWeight.Black,
                                        fontSize = 24.sp
                                    )
                                    Text(
                                        text = "Horas Acreditadas",
                                        color = Color(0xFFECEFF1),
                                        fontSize = 12.sp
                                    )
                                }

                                Column {
                                    Text(
                                        text = "${uiState.certificates.size} diplomas",
                                        color = Color.White,
                                        fontWeight = FontWeight.Black,
                                        fontSize = 24.sp
                                    )
                                    Text(
                                        text = "Certificados SHA-256",
                                        color = Color(0xFFECEFF1),
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }
                }

                // Insignias / Badges Section
                item {
                    val badges = uiState.profile?.badges ?: emptyList()
                    if (badges.isNotEmpty()) {
                        Column {
                            Text(
                                text = "Insignias y Reconocimientos",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .horizontalScroll(rememberScrollState()),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                badges.forEach { badge ->
                                    BadgeCard(badge = badge)
                                }
                            }
                        }
                    }
                }

                // Blockchain verification search box
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = "Verificador Público Blockchain (SHA-256)",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Ingresa el hash o código para autenticar la validez inmutable de cualquier certificado.",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedTextField(
                                    value = uiState.verificationInput,
                                    onValueChange = { viewModel.onVerificationInputChanged(it) },
                                    placeholder = { Text("Hash ej: 8f49a24d...", fontSize = 12.sp) },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(8.dp),
                                    singleLine = true
                                )
                                Button(
                                    onClick = { viewModel.verifyCertificate() },
                                    enabled = !uiState.isVerifying && uiState.verificationInput.isNotBlank(),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    if (uiState.isVerifying) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(16.dp),
                                            color = Color.White,
                                            strokeWidth = 2.dp
                                        )
                                    } else {
                                        Text("Verificar", fontSize = 12.sp)
                                    }
                                }
                            }
                            if (uiState.verificationError != null) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = uiState.verificationError!!,
                                    color = MaterialTheme.colorScheme.error,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }

                // Tabs: Certificados vs Historial CV
                item {
                    TabRow(selectedTabIndex = selectedTab) {
                        Tab(
                            selected = selectedTab == 0,
                            onClick = { selectedTab = 0 },
                            text = { Text("Certificados (${uiState.certificates.size})") }
                        )
                        Tab(
                            selected = selectedTab == 1,
                            onClick = { selectedTab = 1 },
                            text = { Text("Historial para CV") }
                        )
                    }
                }

                if (selectedTab == 0) {
                    if (uiState.certificates.isEmpty()) {
                        item {
                            Text(
                                text = "Aún no tienes certificados emitidos.",
                                modifier = Modifier.padding(vertical = 24.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        items(uiState.certificates, key = { it.id }) { cert ->
                            CertificateCard(
                                certificate = cert,
                                onViewDetail = { viewModel.selectCertificate(cert) },
                                onEvaluate = {
                                    viewModel.openReviewDialog(
                                        targetId = cert.convocatoriaId,
                                        targetName = cert.organizationName,
                                        isOrg = true
                                    )
                                }
                            )
                        }
                    }
                } else {
                    items(uiState.historyItems, key = { it.convocatoriaId }) { history ->
                        HistoryCvCard(
                            history = history,
                            onViewDetail = {
                                val cert = uiState.certificates.find { it.convocatoriaId == history.convocatoriaId }
                                if (cert != null) viewModel.selectCertificate(cert)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BadgeCard(badge: GamificationBadge) {
    Card(
        modifier = Modifier.width(140.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (badge.isUnlocked) Color(0xFFFFF8E1) else Color(0xFFF5F5F5)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (badge.isUnlocked) "🏅" else "🔒",
                fontSize = 28.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = badge.name,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = if (badge.isUnlocked) Color(0xFFE65100) else Color.Gray,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = badge.description,
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                color = Color.DarkGray,
                maxLines = 2
            )
        }
    }
}

@Composable
fun CertificateCard(
    certificate: DigitalCertificate,
    onViewDetail: () -> Unit,
    onEvaluate: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = certificate.convocatoriaTitle,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    modifier = Modifier.weight(1f)
                )
                Surface(
                    color = Color(0xFFE8F5E9),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "${certificate.accreditedHours} hrs",
                        color = Color(0xFF2E7D32),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Entidad: ${certificate.organizationName} • ${certificate.issuedAt}",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Hash: ${certificate.verificationHash.take(18)}...",
                fontFamily = FontFamily.Monospace,
                fontSize = 11.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onEvaluate,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Evaluar ONG", fontSize = 12.sp)
                }

                Button(
                    onClick = onViewDetail,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF014F92)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Ver Diploma", fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun HistoryCvCard(
    history: VolunteerHistoryItem,
    onViewDetail: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = history.convocatoriaTitle,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${history.organizationName} • Acreditó ${history.accreditedHours} horas sociales",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Emisión: ${history.issuedAt} | Verificado en Blockchain",
                fontSize = 11.sp,
                color = Color(0xFF2E7D32)
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(
                onClick = onViewDetail,
                contentPadding = PaddingValues(0.dp)
            ) {
                Text("Ver Registro Oficial y Hash SHA-256", fontSize = 12.sp)
            }
        }
    }
}
