package com.example.blockvoluntariado.feature.authOnboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.blockvoluntariado.feature.authOnboarding.presentation.login.LoginScreen
import com.example.blockvoluntariado.feature.authOnboarding.presentation.onboarding.OnboardingScreen
import com.example.blockvoluntariado.feature.authOnboarding.presentation.recovery.PasswordRecoveryScreen
import com.example.blockvoluntariado.feature.authOnboarding.presentation.register.RegisterScreen
import com.example.blockvoluntariado.feature.authOnboarding.presentation.splash.SplashScreen

fun NavGraphBuilder.authNavGraph(
    navController: NavController,
    onAuthSuccess: () -> Unit
) {
    navigation<AuthNavGraphRoute>(startDestination = SplashRoute) {
        composable<SplashRoute> {
            SplashScreen(
                onNavigateToMainApp = onAuthSuccess,
                onNavigateToOnboarding = {
                    navController.navigate(OnboardingRoute) {
                        popUpTo(SplashRoute) { inclusive = true }
                    }
                }
            )
        }

        composable<OnboardingRoute> {
            OnboardingScreen(
                onNavigateToLogin = {
                    navController.navigate(LoginRoute)
                },
                onNavigateToRegister = {
                    navController.navigate(RegisterRoute)
                }
            )
        }

        composable<LoginRoute> {
            LoginScreen(
                onLoginSuccess = onAuthSuccess,
                onNavigateToRegister = {
                    navController.navigate(RegisterRoute)
                },
                onNavigateToForgotPassword = {
                    navController.navigate(PasswordRecoveryRoute)
                }
            )
        }

        composable<RegisterRoute> {
            RegisterScreen(
                onRegisterSuccess = onAuthSuccess,
                onNavigateToLogin = {
                    navController.navigate(LoginRoute) {
                        popUpTo(RegisterRoute) { inclusive = true }
                    }
                }
            )
        }

        composable<PasswordRecoveryRoute> {
            PasswordRecoveryScreen(
                onNavigateBackToLogin = {
                    navController.popBackStack()
                }
            )
        }
    }
}
