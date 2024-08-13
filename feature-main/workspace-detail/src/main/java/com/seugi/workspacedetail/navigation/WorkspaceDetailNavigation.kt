package com.seugi.workspacedetail.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.seugi.workspacedetail.WorkspaceDetailScreen

const val WORKSPACE_DETAIL_ROUTE = "WORKSPACE_DETAIL_ROUTE"

fun NavController.navigateToWorkspaceDetail(
    workspaceName: String,
    workspaceId: String,
    navOptions: NavOptions? = null
) {
    navigate(
        route = "$WORKSPACE_DETAIL_ROUTE/$workspaceName/$workspaceId",
        navOptions = navOptions
    )
}

fun NavGraphBuilder.workspaceDetailScreen( 
    navigateToJoinWorkspace: () -> Unit,
    popBackStack: () -> Unit
) {
    composable(
        route = "$WORKSPACE_DETAIL_ROUTE/{workspaceName}/{workspaceId}",
        arguments = listOf(
            navArgument("workspaceName") { NavType.StringType },
            navArgument("workspaceId") { NavType.StringType }
        )
    ) {
        WorkspaceDetailScreen(
            workspaceName = it.arguments?.getString("workspaceName") ?: "",
            workspaceId = it.arguments?.getString("workspaceId") ?: "",
            navigateToJoinWorkspace = navigateToJoinWorkspace,
            popBackStack = popBackStack
        )
    }
}
