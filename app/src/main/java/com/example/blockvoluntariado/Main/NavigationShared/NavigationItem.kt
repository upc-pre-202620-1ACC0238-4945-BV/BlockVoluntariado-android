package com.example.blockvoluntariado.Main.NavigationShared

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.blockvoluntariado.Main.NavigationDiscoveryVolunteering.HomeRoute
import com.example.blockvoluntariado.core.ui.calendar_today
import com.example.blockvoluntariado.core.ui.leaderboard
import com.example.blockvoluntariado.core.ui.person
import com.example.blockvoluntariado.core.ui.search
import com.example.blockvoluntariado.feature.application.navigation.MyApplicationsRoute
import com.example.blockvoluntariado.feature.participationTracking.navigation.ActivitiesRoute

enum class NavigationItem(
    val route: Any,
    val icon: ImageVector,
    val title: String
) {
    DISCOVERY(
        route = HomeRoute,
        icon = search,
        title = "Explorar"
    ),

    APPLICATIONS(
        route = MyApplicationsRoute,
        icon = calendar_today,
        title = "Postulaciones"
    ),

    ACTIVITIES(
        route = ActivitiesRoute,
        icon = leaderboard,
        title = "Actividades"
    ),

    PROFILE(
        route = VolunteerProfileRoute,
        icon = person,
        title = "Perfil"
    )
}