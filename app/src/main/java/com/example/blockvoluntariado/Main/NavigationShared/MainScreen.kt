package com.example.blockvoluntariado.Main.NavigationShared

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.blockvoluntariado.Main.NavigationDiscoveryVolunteering.discoveryNavGraph
import com.example.blockvoluntariado.feature.discoveryVolunteering.presentation.ConvocatoriaDetailScreen
import com.example.blockvoluntariado.feature.discoveryVolunteering.presentation.HomeScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            MainNavigationBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavigationItem.entries.first().route,
            modifier = Modifier.padding(innerPadding)
        ) {


            composable<HomeRoute> {
                HomeScreen(
                    OnNavigateToDetail = { convocatoriaId ->
                        navController.navigate(
                            DetailRoute(convocatoriaId)
                        )
                    }
                )
            }

            composable<DetailRoute> { backStackEntry ->
                val detailRoute: DetailRoute =
                    backStackEntry.toRoute()

                ConvocatoriaDetailScreen(
                    convocatoriaId = detailRoute.id,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}