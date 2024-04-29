package com.apeun.gidaechi.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.apeun.gidaechi.StartScreen

const val START_ROUTE = "start"

fun NavController.navigateToLogin(navOptions: NavOptions? = null) = navigate(START_ROUTE, navOptions)

fun NavGraphBuilder.startScreen(navHostController: NavHostController) {
    composable(route = START_ROUTE) {
        StartScreen(navHostController)
    }
}
