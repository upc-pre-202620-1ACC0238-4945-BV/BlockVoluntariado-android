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
import com.example.blockvoluntariado.feature.application.navigation.applicationNavGraph
import com.example.blockvoluntariado.feature.participationTracking.navigation.participationNavGraph
import com.example.blockvoluntariado.feature.volunteerProfile.presentation.DetailPreference
import com.example.blockvoluntariado.feature.volunteerProfile.presentation.DetailProfile
import com.example.blockvoluntariado.feature.volunteerProfile.presentation.ProfileHomeScreen


@Composable
fun MainScreen(
    currentVolunteerId: Int = 1 // Adaptable: puedes pasar el ID del usuario logueado o dejar 1 por defecto
) {
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

            // 1. Grafo de navegación de Discovery Volunteering
            discoveryNavGraph(navController)

            // 2. Grafo de navegación de Application Management
            applicationNavGraph(
                navController = navController,
                onNavigateToConvocatoriaDetail = { id: Long ->
                    navController.navigate(
                        com.example.blockvoluntariado.Main.NavigationDiscoveryVolunteering.DetailRoute(id.toInt())
                    )
                }
            )

            // 3. Grafo de navegación de Participation Management
            participationNavGraph(navController)

            // 4. Grafo de navegación de Volunteer Profile
            composable<VolunteerProfileRoute> {
                ProfileHomeScreen(
                    volunteerId = currentVolunteerId,
                    onNavigateToProfileEdit = { id ->
                        navController.navigate(EditVolunteerProfileRoute(id))
                    },
                    onNavigateToPreferencesEdit = { id ->
                        navController.navigate(EditVolunteerPreferencesRoute(id))
                    }
                )
            }

            composable<EditVolunteerProfileRoute> { backStackEntry ->
                val route = backStackEntry.toRoute<EditVolunteerProfileRoute>()
                DetailProfile(
                    volunteerId = route.volunteerId,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable<EditVolunteerPreferencesRoute> { backStackEntry ->
                val route = backStackEntry.toRoute<EditVolunteerPreferencesRoute>()
                DetailPreference(
                    volunteerId = route.volunteerId,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}