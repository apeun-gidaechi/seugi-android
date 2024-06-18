package com.apeun.gidaechi.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.apeun.gidaechi.screen.JoinSuccessScreen

const val JOIN_SUCCESS = "joinSuccess"

fun NavController.navigateToJoinSuccess(navOptions: NavOptions? = null) = navigate(
    JOIN_SUCCESS,
    navOptions,
)

fun NavGraphBuilder.joinSuccess(navigateToSelectingJob: () -> Unit, popBackStack: () -> Unit) {
    composable(route = JOIN_SUCCESS) {
        JoinSuccessScreen(navigateToSelectingJob = navigateToSelectingJob, popBackStack = popBackStack)
    }
}
