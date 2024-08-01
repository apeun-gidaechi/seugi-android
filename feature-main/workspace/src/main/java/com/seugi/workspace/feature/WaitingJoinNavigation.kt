package com.seugi.workspace.feature

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val WAITING_JOIN = "waitingJoin"

fun NavController.navigateToWaitingJoin(navOptions: NavOptions? = null) = navigate(
    WAITING_JOIN,
    navOptions,
)

fun NavGraphBuilder.waitingJoin(popBackStack: () -> Unit) {
    composable(route = WAITING_JOIN) {
        com.seugi.workspace.feature.WaitingJoinScreen(popBackStack = popBackStack)
    }
}
