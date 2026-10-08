package com.example.blockvoluntariado.Main.NavigationDiscoveryVolunteering


import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.blockvoluntariado.feature.discoveryVolunteering.presentation.ConvocatoriaDetailScreen
import com.example.blockvoluntariado.feature.discoveryVolunteering.presentation.HomeScreen

fun NavGraphBuilder.discoveryNavGraph(navController: NavController) {
    // 1. Pantalla Inicio / Lista de convocatorias
    composable<HomeRoute> {
        HomeScreen(
            OnNavigateToDetail = { id ->
                navController.navigate(DetailRoute(id))
            }
        )
    }

    // 2. Pantalla de Detalle de Convocatoria
    composable<DetailRoute> { backStackEntry ->
        val detailRoute: DetailRoute = backStackEntry.toRoute()
        ConvocatoriaDetailScreen(convocatoriaId = detailRoute.id)
    }
}