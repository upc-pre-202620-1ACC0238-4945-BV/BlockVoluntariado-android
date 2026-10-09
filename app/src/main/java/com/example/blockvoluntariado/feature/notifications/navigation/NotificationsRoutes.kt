package com.example.blockvoluntariado.feature.notifications.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.blockvoluntariado.feature.notifications.presentation.inbox.NotificationsInboxScreen
import com.example.blockvoluntariado.feature.notifications.presentation.preferences.NotificationPreferencesScreen
import kotlinx.serialization.Serializable

@Serializable
data object NotificationsInboxRoute

@Serializable
data object NotificationPreferencesRoute

fun NavGraphBuilder.notificationsNavGraph(
    navController: NavController
) {
    composable<NotificationsInboxRoute> {
        NotificationsInboxScreen(
            onNavigateBack = { navController.popBackStack() },
            onNavigateToPreferences = { navController.navigate(NotificationPreferencesRoute) }
        )
    }

    composable<NotificationPreferencesRoute> {
        NotificationPreferencesScreen(
            onNavigateBack = { navController.popBackStack() }
        )
    }
}
