package com.seugi.assignment.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.seugi.assignment.AssignmentScreen
import com.seugi.data.core.model.ProfileModel

const val ASSIGNMENT_ROUTE = "task"

fun NavController.navigateToAssignment(navOptions: NavOptions? = null) = this.navigate(ASSIGNMENT_ROUTE, navOptions)

fun NavGraphBuilder.assignmentScreen(popBackStack: () -> Unit, workspaceId: String, profile: ProfileModel, navigateToTaskCreate: () -> Unit) {
    composable(ASSIGNMENT_ROUTE) {
        AssignmentScreen(
            popBackStack = popBackStack,
            workspaceId = workspaceId,
            profile = profile,
            navigateToTaskCreate = navigateToTaskCreate,
        )
    }
}
