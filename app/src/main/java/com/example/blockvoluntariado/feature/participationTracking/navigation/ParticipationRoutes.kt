package com.example.blockvoluntariado.feature.participationTracking.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.blockvoluntariado.feature.participationTracking.presentation.activities.MisActividadesScreen
import com.example.blockvoluntariado.feature.participationTracking.presentation.attendance.AsistenciaScreen
import kotlinx.serialization.Serializable

@Serializable
data object ActivitiesRoute

@Serializable
data class AttendanceRoute(val actividadId: Long)

fun NavGraphBuilder.participationNavGraph(
    navController: NavController
) {
    composable<ActivitiesRoute> {
        MisActividadesScreen(
            onNavigateToAttendance = { actividadId ->
                navController.navigate(AttendanceRoute(actividadId))
            }
        )
    }

    composable<AttendanceRoute> {
        AsistenciaScreen(
            onNavigateBack = {
                navController.popBackStack()
            }
        )
    }
}
