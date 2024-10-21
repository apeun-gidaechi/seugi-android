package com.seugi.workspacedetail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.seugi.data.workspace.model.WorkspaceModel
import com.seugi.workspacedetail.feature.workspacedetail.WorkspaceDetailScreen

const val WORKSPACE_DETAIL_ROUTE = "WORKSPACE_DETAIL_ROUTE"

fun NavController.navigateToWorkspaceDetail(navOptions: NavOptions? = null) {
    navigate(
        route = WORKSPACE_DETAIL_ROUTE,
        navOptions = navOptions,
    )
}

fun NavGraphBuilder.workspaceDetailScreen(
    workspace: WorkspaceModel,
    navigateToJoinWorkspace: () -> Unit,
    popBackStack: () -> Unit,
    navigateToWorkspaceMember: (String) -> Unit,
    navigateToCreateWorkspace: () -> Unit,
    changeWorkspace: () -> Unit,
    navigateToInviteMember: () -> Unit,
    myRole: String,
) {
    composable(
        route = WORKSPACE_DETAIL_ROUTE,
    ) {
        WorkspaceDetailScreen(
            workspace = workspace,
            navigateToJoinWorkspace = navigateToJoinWorkspace,
            popBackStack = popBackStack,
            navigateToWorkspaceMember = navigateToWorkspaceMember,
            navigateToCreateWorkspace = navigateToCreateWorkspace,
            changeWorkspace = changeWorkspace,
            navigateToInviteMember = navigateToInviteMember,
            myRole = myRole,
        )
    }
}
