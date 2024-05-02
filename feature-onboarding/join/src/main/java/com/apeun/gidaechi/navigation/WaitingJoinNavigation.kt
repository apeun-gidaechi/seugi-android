package com.apeun.gidaechi.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.apeun.gidaechi.WaitingJoinScreen

const val WAITING_JOIN = "waitingJoin"

fun NavController.navigateToWaitingJoin(navOptions: NavOptions? = null) = navigate(
    WAITING_JOIN,
    navOptions,
)

fun NavGraphBuilder.waitingJoin(navHostController: NavHostController) {
    composable(route = WAITING_JOIN) {
        WaitingJoinScreen(navHostController)
    }
}
