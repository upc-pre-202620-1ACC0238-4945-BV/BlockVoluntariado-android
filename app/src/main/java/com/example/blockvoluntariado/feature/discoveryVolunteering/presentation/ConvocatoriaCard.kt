package com.example.blockvoluntariado.feature.discoveryVolunteering.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.blockvoluntariado.feature.discoveryVolunteering.domain.Convocatoria

@Composable
fun ConvocatoriaCard(
    convocatoria: Convocatoria,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onClick()
            }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = convocatoria.title,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = convocatoria.description,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Lugar: ${convocatoria.ubicacion.district}"
            )

            Text(
                text = "Vacantes: ${convocatoria.occupiedVacancies}/${convocatoria.totalVacancies}"
            )

            Text(
                text = "Fecha: ${convocatoria.horario}"
            )

            Spacer(modifier = Modifier.height(12.dp))

            // BOTÓN DE VER DETALLE
            Button(
                onClick = onClick,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = "Ver detalle")
            }
        }
    }
}

