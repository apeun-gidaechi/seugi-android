package com.apeun.gidaechi.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.apeun.gidaechi.JoinSuccessScreen

const val JOIN_SUCCESS = "joinSuccess"

fun NavController.navigateToJoinSuccess(
    navOptions: NavOptions? = null,
    schoolCode: String,
    workspaceId: String,
    workspaceName: String,
    workspaceImageUrl: String,
    studentCount: Int,
    teacherCount: Int,
) {
    val encodedWorkspaceImageUrl = Uri.encode(workspaceImageUrl)
    navigate(
        "$JOIN_SUCCESS/$schoolCode/$workspaceId/$workspaceName/$encodedWorkspaceImageUrl/$studentCount/$teacherCount",
        navOptions,
    )
}

fun NavGraphBuilder.joinSuccess(navigateToSelectingJob: (workspaceId: String, workspaceCode: String) -> Unit, popBackStack: () -> Unit) {
    composable(
        route = "$JOIN_SUCCESS/{schoolCode}/{workspaceId}/{workspaceName}/{workspaceImageUrl}/{studentCount}/{teacherCount}",
        arguments = listOf(
            navArgument("schoolCode") { NavType.StringType },
            navArgument("workspaceId") { NavType.StringType },
            navArgument("workspaceName") { NavType.StringType },
            navArgument("workspaceImageUrl") { NavType.StringType },
            navArgument("studentCount") { NavType.IntType },
            navArgument("teacherCount") { NavType.IntType },
        ),
    ) {
        JoinSuccessScreen(
            navigateToSelectingJob = navigateToSelectingJob,
            popBackStack = popBackStack,
            schoolCode = it.arguments?.getString("schoolCode") ?: "",
            workspaceId = it.arguments?.getString("workspaceId") ?: "",
            workspaceName = it.arguments?.getString("workspaceName") ?: "",
            workspaceImageUrl = it.arguments?.getString("workspaceImageUrl") ?: "",
            studentCount = it.arguments?.getInt("studentCount") ?: 0,
            teacherCount = it.arguments?.getInt("teacherCount") ?: 0,
        )
    }
}
