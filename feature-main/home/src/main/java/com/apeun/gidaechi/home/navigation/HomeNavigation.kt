package com.apeun.gidaechi.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.apeun.gidaechi.home.HomeScreen

const val HOME_ROUTE = "home"

fun NavController.navigateToHome(navOptions: NavOptions?) = navigate(HOME_ROUTE, navOptions)

fun NavGraphBuilder.homeScreen() {
    composable(HOME_ROUTE) {
        HomeScreen()
    }
}
