package com.example.blockvoluntariado.feature.discoveryVolunteering.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blockvoluntariado.core.ui.calendar_today
import com.example.blockvoluntariado.core.ui.location_on
import com.example.blockvoluntariado.feature.discoveryVolunteering.domain.Convocatoria

val PrimaryBlue = Color(0xFF014F92)
val SecondaryOrange = Color(0xFFFE6802)
val BackgroundWhite = Color(0xFFFFFFFF)
val TextPrimary = Color(0xFF17202A)
val TextSecondary = Color(0xFF667085)

@Composable
fun ConvocatoriaCard(
    convocatoria: Convocatoria,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = BackgroundWhite
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Text(
                text = convocatoria.title,
                color = PrimaryBlue,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))


            Text(
                text = convocatoria.description,
                color = TextSecondary,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(12.dp))


            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = location_on,
                    contentDescription = "Ubicación",
                    tint = SecondaryOrange,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = convocatoria.ubicacion.district ?: "Ubicación no especificada",
                    color = TextPrimary,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(6.dp))


            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = calendar_today,
                    contentDescription = "Fecha",
                    tint = SecondaryOrange,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))

                val fechaFormatted = listOfNotNull(
                    convocatoria.horario.startDate?.toString(),
                    convocatoria.horario.startTime
                ).joinToString(" - ")

                Text(
                    text = if (fechaFormatted.isNotBlank()) fechaFormatted else "Fecha no especificada",
                    color = TextPrimary,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))


            Text(
                text = "Vacantes: ${convocatoria.occupiedVacancies}/${convocatoria.totalVacancies}",
                color = TextSecondary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(16.dp))


            Button(
                onClick = onClick,
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SecondaryOrange,
                    contentColor = BackgroundWhite
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Ver detalle",
                    fontWeight = FontWeight.SemiBold

                )
            }
        }
    }
}