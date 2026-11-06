package com.example.blockvoluntariado.feature.gamificationFeedback.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.blockvoluntariado.feature.gamificationFeedback.presentation.recognition.LogrosCertificadosScreen
import kotlinx.serialization.Serializable

@Serializable
data object GamificationRoute

fun NavGraphBuilder.gamificationNavGraph(
    navController: NavController
) {
    composable<GamificationRoute> {
        LogrosCertificadosScreen()
    }
}
