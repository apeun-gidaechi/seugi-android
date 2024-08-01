package com.seugi.workspace.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.seugi.workspace.feature.schoolcode.SchoolScreen

const val SCHOOL_CODE = "schoolCode"

fun NavController.navigateToSchoolCode(navOptions: NavOptions? = null, role: String) = navigate(
    "$SCHOOL_CODE/$role",
    navOptions,
)

fun NavGraphBuilder.schoolCode(
    navigateToJoinSuccess: (
        schoolCode: String,
        workspaceId: String,
        workspaceName: String,
        workspaceImageUrl: String,
        studentCount: Int,
        teacherCount: Int,
    ) -> Unit,
    popBackStack: () -> Unit,
) {
    composable(
        route = "$SCHOOL_CODE/{role}",
        arguments = listOf(
            navArgument("role") { NavType.StringType },
        )) {
        SchoolScreen(
            navigateToJoinSuccess = navigateToJoinSuccess,
            popBackStack = popBackStack,
        )
    }
}
