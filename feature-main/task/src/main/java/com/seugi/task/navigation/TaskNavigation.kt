package com.seugi.task.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.seugi.task.TaskScreen

const val TASK_ROUTE = "task"

fun NavController.navigateToTask(navOptions: NavOptions? = null) = this.navigate(TASK_ROUTE, navOptions)

fun NavGraphBuilder.taskScreen(
    popBackStack: () -> Unit,
    workspaceId: String,
) {
    composable(TASK_ROUTE) {
        TaskScreen(
            popBackStack = popBackStack,
            workspaceId = workspaceId,
        )
    }
}