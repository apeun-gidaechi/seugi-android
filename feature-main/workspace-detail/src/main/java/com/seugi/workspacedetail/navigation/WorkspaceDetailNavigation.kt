package com.seugi.workspacedetail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.seugi.workspacedetail.WorkspaceDetailScreen

const val WORKSPACE_DETAIL_ROUTE = "WORKSPACE_DETAIL_ROUTE"

fun NavController.navigateToWorkspaceDetail(navOptions: NavOptions? = null) = navigate(WORKSPACE_DETAIL_ROUTE, navOptions)

fun NavGraphBuilder.workspaceDetailScreen(navigateToJoinWorkspace: () -> Unit, popBackStack: () -> Unit) {
    composable(WORKSPACE_DETAIL_ROUTE) {
        WorkspaceDetailScreen(
            navigateToJoinWorkspace = navigateToJoinWorkspace,
            popBackStack = popBackStack
        )
    }
}
