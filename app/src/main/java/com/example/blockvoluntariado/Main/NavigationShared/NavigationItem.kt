package com.example.blockvoluntariado.Main.NavigationShared

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.blockvoluntariado.core.ui.search


enum class NavigationItem (

    val route: Any,
    val icon: ImageVector,
    val title: String

) {
    DISCOVERY(
        route = HomeRoute,
        icon = search,
        title = "Convocatorias"
    )


}