package com.seugi.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.seugi.data.workspace.model.WorkspaceModel
import com.seugi.home.HomeScreen

const val HOME_ROUTE = "home"

fun NavController.navigateToHome(navOptions: NavOptions? = null, workspaceId: String) = navigate(HOME_ROUTE, navOptions)

fun NavController.navigateToHome(toRoute: String, fromRoute: String) {
    this.navigate(toRoute) {
        popUpTo(fromRoute) {
            inclusive = true
        }
    }
}

fun NavGraphBuilder.homeScreen(
    workspace: WorkspaceModel,
    notJoinWorkspace: Boolean,
    navigateToChatSeugi: () -> Unit,
    navigateToJoinWorkspace: () -> Unit,
    navigateToTimetable: () -> Unit,
    navigateToWorkspaceDetail: (String) -> Unit,
    navigateToWorkspaceCreate: () -> Unit,
    navigateToTask: () -> Unit,
) {
    composable(HOME_ROUTE) {
        HomeScreen(
            workspace = workspace,
            notJoinWorkspace = notJoinWorkspace,
            navigateToChatSeugi = navigateToChatSeugi,
            navigateToJoinWorkspace = navigateToJoinWorkspace,
            navigateToTimetable = navigateToTimetable,
            navigateToWorkspaceDetail = navigateToWorkspaceDetail,
            navigateToWorkspaceCreate = navigateToWorkspaceCreate,
            navigateToTask = navigateToTask,
        )
    }
}
