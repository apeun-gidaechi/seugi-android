package com.seugi.join.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.seugi.join.feature.WaitingJoinScreen

const val WAITING_JOIN = "waitingJoin"

fun NavController.navigateToWaitingJoin(navOptions: NavOptions? = null) = navigate(
    WAITING_JOIN,
    navOptions,
)

fun NavGraphBuilder.waitingJoin(popBackStack: () -> Unit) {
    composable(route = WAITING_JOIN) {
        WaitingJoinScreen(popBackStack = popBackStack)
    }
}
