package com.apeun.gidaechi.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.apeun.gidaechi.main.MainScreen

const val MAIN_ROUTE = "main"

fun NavController.navigateToMain(navOptions: NavOptions? = null) = navigate(MAIN_ROUTE, navOptions)

fun NavGraphBuilder.mainScreen(mainToOnboarding: () -> Unit) {
    composable(route = MAIN_ROUTE) {
        MainScreen(mainToOnboarding = mainToOnboarding)
    }
}
