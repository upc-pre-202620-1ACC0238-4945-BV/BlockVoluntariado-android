package com.example.blockvoluntariado.feature.discoveryVolunteering.presentation

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.blockvoluntariado.feature.discoveryVolunteering.domain.Convocatoria


@Composable

fun ConvocatoriaList(convocatorias: List<Convocatoria>, onConvocatoriaClick: (Int) -> Unit){

    LazyColumn{
        items(convocatorias){ convocatoria ->
            ConvocatoriaCard(convocatoria = convocatoria,
                onClick = {
                    onConvocatoriaClick(convocatoria.id)
                }
            )
        }
    }
}