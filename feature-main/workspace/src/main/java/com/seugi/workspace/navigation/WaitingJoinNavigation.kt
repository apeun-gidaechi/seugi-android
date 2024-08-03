package com.seugi.workspace.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.seugi.workspace.feature.WaitingJoinScreen

const val WAITING_JOIN = "waitingJoin"

fun NavController.navigateToWaitingJoin(navOptions: NavOptions? = null) = navigate(
    WAITING_JOIN,
    navOptions,
)

fun NavGraphBuilder.waitingJoin(joinToHome:() -> Unit, popBackStack: () -> Unit) {
    composable(route = WAITING_JOIN) {
        WaitingJoinScreen(
            joinToHome = joinToHome,
            popBackStack = popBackStack)
    }
}
