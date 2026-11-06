
package com.example.blockvoluntariado.extras

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainExtraTopBar(
    showBack: Boolean,
    onBack: () -> Unit,
    isMusicPlaying: Boolean? = null,
    onToggleMusic: (() -> Unit)? = null
) {
    TopAppBar(
        title = {
            Text(
                text = "BlockVoluntariado",
                style = MaterialTheme.typography.titleMedium
            )
        },

        navigationIcon = {
            if (showBack) {
                TextButton(
                    onClick = onBack
                ) {
                    Text("← Atrás")
                }
            }
        },

        actions = {
            if (
                isMusicPlaying != null &&
                onToggleMusic != null
            ) {
                TextButton(
                    onClick = onToggleMusic
                ) {
                    Text(
                        if (isMusicPlaying) {
                            "♫ Pausar"
                        } else {
                            "♫ Música"
                        }
                    )
                }
            }
        }
    )
}
