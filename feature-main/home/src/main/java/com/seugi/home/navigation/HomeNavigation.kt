package com.seugi.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.seugi.home.HomeScreen

const val HOME_ROUTE = "home"

fun NavController.navigateToHome(navOptions: NavOptions?) = navigate(HOME_ROUTE, navOptions)

fun NavGraphBuilder.homeScreen(navigateToChatSeugi: () -> Unit, navigateToJoinWorkspace: () -> Unit, onNavigationVisibleChange: (Boolean) -> Unit) {
    composable(HOME_ROUTE) {
        HomeScreen(
            navigateToChatSeugi = navigateToChatSeugi,
            navigateToJoinWorkspace = navigateToJoinWorkspace,
            onNavigationVisibleChange = onNavigationVisibleChange
        )
    }
}
