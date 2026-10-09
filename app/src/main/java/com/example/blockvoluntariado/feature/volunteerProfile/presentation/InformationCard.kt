
package com.example.blockvoluntariado.feature.volunteerProfile.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private val ProfilePrimaryBlue = Color(0xFF014F92)
private val ProfileBackgroundWhite = Color(0xFFFFFFFF)
private val ProfileBorderColor = Color(0xFFD9E1E8)
private val ProfileTextColor = Color(0xFF17202A)

@Composable
fun InformationCard(
    title: String,
    modifier: Modifier = Modifier,
    onEdit: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = ProfileBackgroundWhite
        ),
        border = BorderStroke(
            width = 1.dp,
            color = ProfileBorderColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleMedium,
                    color = ProfileTextColor,
                    fontWeight = FontWeight.Bold
                )

                if (onEdit != null) {
                    Spacer(modifier = Modifier.width(4.dp))

                    TextButton(
                        onClick = onEdit
                    ) {
                        Text(
                            text = "Editar",
                            color = ProfilePrimaryBlue,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            content()
        }
    }
}
