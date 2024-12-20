package com.seugi.start.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.seugi.join.navigation.EMAIL_VERIFICATION_ROUTE
import com.seugi.start.StartScreen

const val START_ROUTE = "start"

fun NavController.navigateToStart(navOptions: NavOptions? = null) = navigate(START_ROUTE) {
    popUpTo(EMAIL_VERIFICATION_ROUTE) {
        inclusive = false
    }
}

fun NavGraphBuilder.startScreen(navigateToEmailSignIn: () -> Unit, navigateToMain: () -> Unit) {
    composable(route = START_ROUTE) {
        StartScreen(
            navigateToEmailSignIn = navigateToEmailSignIn,
            navigateToMain = navigateToMain,
        )
    }
}
