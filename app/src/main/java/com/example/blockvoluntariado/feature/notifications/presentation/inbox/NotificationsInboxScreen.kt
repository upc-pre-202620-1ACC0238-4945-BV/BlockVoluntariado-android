package com.example.blockvoluntariado.feature.notifications.presentation.inbox

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.example.blockvoluntariado.core.ui.notifications
import com.example.blockvoluntariado.core.ui.west
import com.example.blockvoluntariado.feature.notifications.domain.model.AppNotification
import com.example.blockvoluntariado.feature.notifications.domain.model.NotificationType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsInboxScreen(
    viewModel: NotificationsViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToPreferences: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.statusMessage) {
        uiState.statusMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearStatusMessage()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Notificaciones",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = west,
                            contentDescription = "Volver"
                        )
                    }
                },
                actions = {
                    TextButton(onClick = { viewModel.markAllAsRead() }) {
                        Text("Leer todas", fontSize = 12.sp)
                    }
                    IconButton(onClick = onNavigateToPreferences) {
                        Icon(
                            imageVector = notifications,
                            contentDescription = "Preferencias"
                        )
                    }
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
            // Filter row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = !uiState.filterUnreadOnly,
                    onClick = { viewModel.toggleFilter(false) },
                    label = { Text("Todas") }
                )
                FilterChip(
                    selected = uiState.filterUnreadOnly,
                    onClick = { viewModel.toggleFilter(true) },
                    label = {
                        val unreadCount = uiState.notifications.count { !it.isRead }
                        Text(if (unreadCount > 0) "No leídas ($unreadCount)" else "No leídas")
                    }
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
                val displayedList = if (uiState.filterUnreadOnly) {
                    uiState.notifications.filter { !it.isRead }
                } else {
                    uiState.notifications
                }

                if (displayedList.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (uiState.filterUnreadOnly) "No tienes notificaciones pendientes."
                            else "Tu bandeja de notificaciones está vacía.",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(displayedList, key = { it.id }) { notif ->
                            NotificationItemCard(
                                notification = notif,
                                onClick = { viewModel.markAsRead(notif.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationItemCard(
    notification: AppNotification,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (!notification.isRead) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f)
            else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (!notification.isRead) 2.dp else 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Icon Pill
            Surface(
                modifier = Modifier.size(38.dp),
                shape = CircleShape,
                color = when (notification.type) {
                    NotificationType.POSTULACION_STATUS -> Color(0xFFE3F2FD)
                    NotificationType.ACTIVIDAD_RECORDATORIO -> Color(0xFFFFF3E0)
                    NotificationType.CERTIFICADO_EMITIDO -> Color(0xFFE8F5E9)
                    NotificationType.SISTEMA -> Color(0xFFECEFF1)
                }
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = when (notification.type) {
                            NotificationType.POSTULACION_STATUS -> "📋"
                            NotificationType.ACTIVIDAD_RECORDATORIO -> "🗓️"
                            NotificationType.CERTIFICADO_EMITIDO -> "🎓"
                            NotificationType.SISTEMA -> "🔔"
                        },
                        fontSize = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = notification.title,
                        fontWeight = if (!notification.isRead) FontWeight.Bold else FontWeight.SemiBold,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.weight(1f)
                    )

                    if (!notification.isRead) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(Color(0xFF014F92), CircleShape)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = notification.message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "${notification.type.displayName} • ${notification.sentAt}",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }
        }
    }
}
