package com.seugi.workspace.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.seugi.workspace.feature.joinsuccess.JoinSuccessScreen

const val JOIN_SUCCESS = "joinSuccess"

fun NavController.navigateToJoinSuccess(
    navOptions: NavOptions? = null,
    schoolCode: String,
    workspaceId: String,
    workspaceName: String,
    workspaceImageUrl: String,
    studentCount: Int,
    teacherCount: Int,
    role: String
) {
    val encodedWorkspaceImageUrl = Uri.encode(workspaceImageUrl)
    navigate(
        "$JOIN_SUCCESS/$schoolCode/$workspaceId/$workspaceName/$encodedWorkspaceImageUrl/$studentCount/$teacherCount/$role",
        navOptions,
    )
}

fun NavGraphBuilder.joinSuccess(navigateToWaiting: () -> Unit, popBackStack: () -> Unit) {
    composable(
        route = "$JOIN_SUCCESS/{schoolCode}/{workspaceId}/{workspaceName}/{workspaceImageUrl}/{studentCount}/{teacherCount}/{role}",
        arguments = listOf(
            navArgument("schoolCode") { NavType.StringType },
            navArgument("workspaceId") { NavType.StringType },
            navArgument("workspaceName") { NavType.StringType },
            navArgument("workspaceImageUrl") { NavType.StringType },
            navArgument("studentCount") { NavType.IntType },
            navArgument("teacherCount") { NavType.IntType },
            navArgument("role") { NavType.StringType },
        ),
    ) {
        JoinSuccessScreen(
            navigateToWaiting = navigateToWaiting,
            popBackStack = popBackStack,
            schoolCode = it.arguments?.getString("schoolCode") ?: "",
            workspaceId = it.arguments?.getString("workspaceId") ?: "",
            workspaceName = it.arguments?.getString("workspaceName") ?: "",
            workspaceImageUrl = it.arguments?.getString("workspaceImageUrl") ?: "",
            // TODO 이거 뭔 버근지 찾아보기
            studentCount = it.arguments?.getString("studentCount")?.toInt() ?: 0,
            teacherCount = it.arguments?.getString("teacherCount")?.toInt() ?: 0,
            role = it.arguments?.getString("role") ?: "",
        )
    }
}