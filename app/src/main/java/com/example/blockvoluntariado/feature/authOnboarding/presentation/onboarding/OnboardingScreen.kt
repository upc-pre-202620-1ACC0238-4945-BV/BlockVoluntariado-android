package com.example.blockvoluntariado.feature.authOnboarding.presentation.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blockvoluntariado.core.ui.search
import com.example.blockvoluntariado.core.ui.person

data class OnboardingStep(
    val title: String,
    val subtitle: String,
    val tag: String
)

@Composable
fun OnboardingScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val steps = listOf(
        OnboardingStep(
            tag = "CONECTA CON CAUSAS",
            title = "Encuentra tu Voluntariado Ideal",
            subtitle = "Explora oportunidades comunitarias diseñadas para universitarios con horarios flexibles y cercanía a tu zona."
        ),
        OnboardingStep(
            tag = "ASISTENCIA EN CAMPO",
            title = "Valida tu Impacto en Tiempo Real",
            subtitle = "Registra tu participación en jornadas sociales de forma ágil y lleva el cómputo oficial de tus horas sociales."
        ),
        OnboardingStep(
            tag = "CERTIFICADOS DIGITALES",
            title = "Reconocimiento y Respaldo Oficial",
            subtitle = "Obtén certificados emitidos con firma criptográfica SHA-256 e insignias para enriquecer tu perfil profesional."
        )
    )

    var currentStepIndex by remember { mutableIntStateOf(0) }
    val currentStep = steps[currentStepIndex]

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.colorScheme.surface
                    )
                )
            )
    ) {
        // Upper Visual Hero Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.60f)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .size(110.dp)
                    .clip(RoundedCornerShape(32.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.20f)
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "BV",
                        fontSize = 44.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "BlockVoluntariado",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = currentStep.tag,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp,
                color = Color.White.copy(alpha = 0.85f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Step dots indicator
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                steps.indices.forEach { index ->
                    val isSelected = index == currentStepIndex
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .height(8.dp)
                            .width(if (isSelected) 24.dp else 8.dp)
                            .clip(CircleShape)
                            .background(
                                if (isSelected) Color.White else Color.White.copy(alpha = 0.4f)
                            )
                    )
                }
            }
        }

        // Bottom Content Card
        OnboardingInformationCard(
            title = currentStep.title,
            subTitle = currentStep.subtitle,
            primaryButtonText = if (currentStepIndex < steps.lastIndex) "Siguiente" else "Iniciar Sesión",
            secondaryButtonText = if (currentStepIndex < steps.lastIndex) "Saltar" else "Crear Cuenta Nueva",
            onPrimaryClick = {
                if (currentStepIndex < steps.lastIndex) {
                    currentStepIndex++
                } else {
                    onNavigateToLogin()
                }
            },
            onSecondaryClick = {
                if (currentStepIndex < steps.lastIndex) {
                    onNavigateToLogin()
                } else {
                    onNavigateToRegister()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.44f)
                .align(Alignment.BottomCenter)
        )
    }
}
