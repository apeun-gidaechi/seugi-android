package com.seugi.workspace_create.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.seugi.workspace_create.WorkspaceCreateScreen

const val WORKSPACE_CREATE_ROUTE = "WORKSPACE_CREATE_ROUTE"

fun NavController.navigateToWorkspaceCreate(navOptions: NavOptions? = null) {
    navigate(
        route = WORKSPACE_CREATE_ROUTE,
        navOptions = navOptions,
    )
}

fun NavGraphBuilder.workspaceCreateScreen(popBackStack: () -> Unit) {
    composable(
        route = WORKSPACE_CREATE_ROUTE,
    ) {
        WorkspaceCreateScreen(
            popBackStack = popBackStack
        )
    }
}
