package com.seugi.workspace.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.seugi.workspace.feature.selectingcode.SelectingJobScreen

const val SELECTING_JOB = "selectingJob"

fun NavController.navigateToSelectingJob(navOptions: NavOptions? = null) = navigate(
    SELECTING_JOB,
    navOptions,
)

fun NavGraphBuilder.selectingJob(navigateToSelectingRole: (role: String) -> Unit, popBackStack: () -> Unit) {
    composable(
        route = SELECTING_JOB
    ) {
        SelectingJobScreen(
            navigateToSelectingRole = navigateToSelectingRole,
            popBackStack = popBackStack,
        )
    }
}
