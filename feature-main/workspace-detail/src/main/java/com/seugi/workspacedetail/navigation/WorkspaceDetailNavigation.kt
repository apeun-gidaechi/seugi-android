package com.seugi.workspacedetail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.seugi.workspacedetail.feature.workspacedetail.WorkspaceDetailScreen

const val WORKSPACE_DETAIL_ROUTE = "WORKSPACE_DETAIL_ROUTE"

fun NavController.navigateToWorkspaceDetail(workspaceId: String, navOptions: NavOptions? = null) {
    navigate(
        route = "$WORKSPACE_DETAIL_ROUTE/$workspaceId",
        navOptions = navOptions,
    )
}

fun NavGraphBuilder.workspaceDetailScreen(navigateToJoinWorkspace: () -> Unit, popBackStack: () -> Unit, navigateToWorkspaceMember: (String) -> Unit) {
    composable(
        route = "$WORKSPACE_DETAIL_ROUTE/{workspaceId}",
        arguments = listOf(
            navArgument("workspaceId") { NavType.StringType },
        ),
    ) {
        WorkspaceDetailScreen(
            workspaceId = it.arguments?.getString("workspaceId") ?: "",
            navigateToJoinWorkspace = navigateToJoinWorkspace,
            popBackStack = popBackStack,
            navigateToWorkspaceMember = navigateToWorkspaceMember,
        )
    }
}
