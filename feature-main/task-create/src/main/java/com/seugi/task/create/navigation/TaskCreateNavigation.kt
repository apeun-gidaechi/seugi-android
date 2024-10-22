package com.seugi.task.create.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.seugi.task.create.TaskCreateScreen

const val TASK_CREATE_ROUTE = "task_create"

fun NavController.navigateToTaskCreate(navOptions: NavOptions? = null) = this.navigate(TASK_CREATE_ROUTE, navOptions)

fun NavGraphBuilder.taskCreateScreen(
    popBackStack: () -> Unit,
    workspaceId: String,
) {
    composable(TASK_CREATE_ROUTE) {
        TaskCreateScreen(
            popBackStack = popBackStack,
            workspaceId = workspaceId,
        )
    }
}