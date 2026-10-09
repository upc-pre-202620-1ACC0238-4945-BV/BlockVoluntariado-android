package com.example.blockvoluntariado

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.blockvoluntariado.Main.NavigationShared.MainScreen
import com.example.blockvoluntariado.core.ui.theme.BlockVoluntariadoTheme
import com.example.blockvoluntariado.feature.authOnboarding.navigation.AuthNavGraphRoute
import com.example.blockvoluntariado.feature.authOnboarding.navigation.MainAppRoute
import com.example.blockvoluntariado.feature.authOnboarding.navigation.authNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BlockVoluntariadoTheme {
                val rootNavController = rememberNavController()

                NavHost(
                    navController = rootNavController,
                    startDestination = AuthNavGraphRoute
                ) {
                    authNavGraph(
                        navController = rootNavController,
                        onAuthSuccess = {
                            rootNavController.navigate(MainAppRoute) {
                                popUpTo(AuthNavGraphRoute) { inclusive = true }
                            }
                        }
                    )

                    composable<MainAppRoute> {
                        MainScreen()
                    }
                }
            }
        }
    }
}
