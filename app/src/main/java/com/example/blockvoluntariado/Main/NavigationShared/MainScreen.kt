
package com.example.blockvoluntariado.Main.NavigationShared

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute

import com.example.blockvoluntariado.Main.NavigationDiscoveryVolunteering.discoveryNavGraph
import com.example.blockvoluntariado.Main.NavigationDiscoveryVolunteering.DetailRoute

import com.example.blockvoluntariado.feature.application.navigation.applicationNavGraph

import com.example.blockvoluntariado.feature.participationTracking.navigation.participationNavGraph

import com.example.blockvoluntariado.feature.gamificationFeedback.navigation.GamificationRoute
import com.example.blockvoluntariado.feature.gamificationFeedback.navigation.gamificationNavGraph

import com.example.blockvoluntariado.feature.notifications.navigation.NotificationsInboxRoute
import com.example.blockvoluntariado.feature.notifications.navigation.notificationsNavGraph

import com.example.blockvoluntariado.feature.volunteerProfile.presentation.DetailPreference
import com.example.blockvoluntariado.feature.volunteerProfile.presentation.DetailProfile
import com.example.blockvoluntariado.feature.volunteerProfile.presentation.ProfileHomeScreen

import com.example.blockvoluntariado.extras.MainExtraTopBar
import com.example.blockvoluntariado.extras.rememberBackgroundMusicState


@Composable
fun MainScreen(
    currentVolunteerId: Int = 1,
    onLogout: () -> Unit,
    sessionActions: SessionActionsViewModel = hiltViewModel()
) {

    // Controlador principal de navegación
    val navController = rememberNavController()

    // Control de la música
    val music = rememberBackgroundMusicState()

    // Obtener el ID del voluntario autenticado
    val actualVolunteerId by
    sessionActions.currentVolunteerId.collectAsStateWithLifecycle()

    // Verificar si se está cerrando la sesión
    val isSigningOut by
    sessionActions.isSigningOut.collectAsStateWithLifecycle()

    // Obtener posibles errores de cierre de sesión
    val logoutError by
    sessionActions.error.collectAsStateWithLifecycle()

    // Detectar la pantalla actual
    val navBackStackEntry by
    navController.currentBackStackEntryAsState()

    val currentDestination = navBackStackEntry?.destination

    // Comprobar si estamos en una pestaña principal
    val onMainTab = NavigationItem.entries.any { item ->

        currentDestination?.route?.contains(
            item.route::class.simpleName.orEmpty()
        ) == true
    }

    // Mostrar Atrás en pantallas secundarias
    val showBack = !onMainTab &&
            navController.previousBackStackEntry != null


    // Estructura principal de la aplicación
    Scaffold(

        // Barra superior: Atrás y Música
        topBar = {

            MainExtraTopBar(
                showBack = showBack,

                onBack = {
                    navController.popBackStack()
                },

                isMusicPlaying = music.isPlaying,

                onToggleMusic = {
                    music.toggle()
                }
            )
        },

        // Barra inferior de navegación
        bottomBar = {

            MainNavigationBar(
                navController = navController
            )
        }

    ) { innerPadding ->

        NavHost(
            navController = navController,

            startDestination = NavigationItem.entries.first().route,

            modifier = Modifier.padding(innerPadding)
        ) {

            // =========================================
            // 1. DISCOVERY VOLUNTEERING
            // =========================================

            discoveryNavGraph(
                navController
            )


            // =========================================
            // 2. APPLICATION MANAGEMENT
            // =========================================

            applicationNavGraph(

                navController = navController,

                onNavigateToConvocatoriaDetail = { id: Long ->

                    navController.navigate(
                        DetailRoute(id.toInt())
                    )
                }
            )


            // =========================================
            // 3. PARTICIPATION MANAGEMENT
            // =========================================

            participationNavGraph(
                navController
            )


            // =========================================
            // 4. GAMIFICATION AND FEEDBACK
            // =========================================

            gamificationNavGraph(
                navController
            )


            // =========================================
            // 5. COMMUNICATION AND NOTIFICATIONS
            // =========================================

            notificationsNavGraph(
                navController
            )


            // =========================================
            // 6. VOLUNTEER PROFILE
            // =========================================

            composable<VolunteerProfileRoute> {

                ProfileHomeScreen(

                    // ID de la cuenta actual
                    volunteerId = if (actualVolunteerId > 0) {
                        actualVolunteerId
                    } else {
                        currentVolunteerId
                    },

                    // Cerrar sesión
                    onLogout = {

                        sessionActions.signOut {

                            // Detener la música
                            music.stop()

                            // Volver al Login
                            onLogout()
                        }
                    },

                    isSigningOut = isSigningOut,

                    logoutError = logoutError,

                    // Editar perfil
                    onNavigateToProfileEdit = { id ->

                        navController.navigate(
                            EditVolunteerProfileRoute(id)
                        )
                    },

                    // Editar preferencias
                    onNavigateToPreferencesEdit = { id ->

                        navController.navigate(
                            EditVolunteerPreferencesRoute(id)
                        )
                    },

                    // Ver certificados
                    onNavigateToCertificates = {

                        navController.navigate(
                            GamificationRoute
                        )
                    },

                    // Ver notificaciones
                    onNavigateToNotifications = {

                        navController.navigate(
                            NotificationsInboxRoute
                        )
                    }
                )
            }


            // =========================================
            // 7. EDIT VOLUNTEER PROFILE
            // =========================================

            composable<EditVolunteerProfileRoute> { backStackEntry ->

                val route = backStackEntry
                    .toRoute<EditVolunteerProfileRoute>()

                DetailProfile(

                    volunteerId = route.volunteerId,

                    onBack = {
                        navController.popBackStack()
                    }
                )
            }


            // =========================================
            // 8. EDIT VOLUNTEER PREFERENCES
            // =========================================

            composable<EditVolunteerPreferencesRoute> { backStackEntry ->

                val route = backStackEntry
                    .toRoute<EditVolunteerPreferencesRoute>()

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
