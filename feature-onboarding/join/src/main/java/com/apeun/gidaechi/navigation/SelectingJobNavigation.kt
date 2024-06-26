package com.apeun.gidaechi.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.apeun.gidaechi.SelectingJobScreen

const val SELECTING_JOB = "selectingJob"

fun NavController.navigateToSelectingJob(navOptions: NavOptions? = null, workspaceId: String, schoolCode: String) = navigate(
    "$SELECTING_JOB/$workspaceId/$schoolCode",
    navOptions,
)

fun NavGraphBuilder.selectingJob(navigateToWaitingJoin: () -> Unit, popBackStack: () -> Unit) {
    composable(
        route = "$SELECTING_JOB/{workspaceId}/{schoolCode}",
        arguments = listOf(
            navArgument("workspaceId") { NavType.StringType },
            navArgument("schoolCode") { NavType.StringType },
        ),
    ) {
        SelectingJobScreen(
            navigateToWaitingJoin = navigateToWaitingJoin,
            popBackStack = popBackStack,
            workspaceId = it.arguments?.getString("workspaceId") ?: "",
            schoolCode = it.arguments?.getString("schoolCode") ?: "",
        )
    }
}
