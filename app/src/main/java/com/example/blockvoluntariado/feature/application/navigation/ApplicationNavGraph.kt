package com.example.blockvoluntariado.feature.application.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.blockvoluntariado.feature.application.presentation.my_applications.MisPostulacionesScreen
import kotlinx.serialization.Serializable

@Serializable
data object MyApplicationsRoute

fun NavGraphBuilder.applicationNavGraph(
    navController: NavController,
    onNavigateToConvocatoriaDetail: (Long) -> Unit
) {
    composable<MyApplicationsRoute> {
        MisPostulacionesScreen(
            onNavigateToConvocatoriaDetail = onNavigateToConvocatoriaDetail
        )
    }
}
