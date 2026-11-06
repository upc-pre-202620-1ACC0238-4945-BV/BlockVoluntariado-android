
package com.example.blockvoluntariado.extras

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.blockvoluntariado.R

class BackgroundMusicState(context: Context) {


    private val player: MediaPlayer? =
        MediaPlayer.create(
            context.applicationContext,
            R.raw.theme
        )?.apply {
            isLooping = true
            setVolume(0.35f, 0.35f)
        }

    var isPlaying by mutableStateOf(false)
        private set

    fun toggle() {
        val mediaPlayer = player ?: return

        try {
            if (isPlaying) {
                mediaPlayer.pause()
                isPlaying = false
            } else {
                mediaPlayer.start()
                isPlaying = true
            }
        } catch (_: IllegalStateException) {
            isPlaying = false
        }
    }

    fun onBackground() {
        try {
            if (player?.isPlaying == true) {
                player.pause()
            }
        } catch (_: IllegalStateException) {
        }
    }

    fun onForeground() {
        try {
            if (isPlaying && player?.isPlaying == false) {
                player.start()
            }
        } catch (_: IllegalStateException) {
            isPlaying = false
        }
    }

    fun stop() {
        isPlaying = false

        try {
            player?.pause()
            player?.seekTo(0)
        } catch (_: IllegalStateException) {
        }
    }

    fun release() {
        isPlaying = false
        player?.release()
    }
}

@Composable
fun rememberBackgroundMusicState(): BackgroundMusicState {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val music = remember(context.applicationContext) {
        BackgroundMusicState(context.applicationContext)
    }

    DisposableEffect(lifecycleOwner, music) {

        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_STOP -> music.onBackground()
                Lifecycle.Event.ON_START -> music.onForeground()
                else -> Unit
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            music.release()
        }
    }

    return music
}
