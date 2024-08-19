package com.seugi.workspacedetail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.seugi.workspacedetail.feature.workspacemember.WorkspaceMemberScreen

const val WORKSPACE_MEMBER_ROUTE = "WORKSPACE_MEMBER_ROUTE"

fun NavController.navigateToWorkspaceMember(workspaceId: String, navOptions: NavOptions? = null) {
    navigate(
        route = "$WORKSPACE_MEMBER_ROUTE/$workspaceId",
        navOptions = navOptions,
    )
}

fun NavGraphBuilder.workspaceMemberScreen(popBackStack: () -> Unit) {
    composable(
        route = "$WORKSPACE_MEMBER_ROUTE/{workspaceId}",
        arguments = listOf(
            navArgument("workspaceId") { NavType.StringType },
        ),
    ) {
        WorkspaceMemberScreen(
            workspaceId = it.arguments?.getString("workspaceId") ?: "",
            popBackStack = popBackStack,
        )
    }
}
