package com.seugi.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.seugi.main.MainScreen

const val MAIN_ROUTE = "main"

fun NavController.navigateToMain(toRoute: String, fromRoute: String) {
    this.navigate(toRoute) {
        popUpTo(fromRoute) {
            inclusive = true
        }
    }
}

fun NavGraphBuilder.mainScreen(mainToOnboarding: () -> Unit, showSnackbar: (text: String) -> Unit) {
    composable(route = MAIN_ROUTE) {
        MainScreen(
            mainToOnboarding = mainToOnboarding,
            showSnackbar = showSnackbar,
        )
    }
}
