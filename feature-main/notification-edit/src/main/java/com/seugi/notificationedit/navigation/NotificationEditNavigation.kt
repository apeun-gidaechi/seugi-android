package com.seugi.notificationedit.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.seugi.data.core.model.WorkspacePermissionModel
import com.seugi.notificationedit.NotificationEditScreen

const val NOTIFICATION_EDIT_ROUTE = "notification_create"

fun NavController.navigateToNotificationEdit(id: Long, title: String, content: String, userId: Long, navOptions: NavOptions? = null) = this.navigate(
    route = "${NOTIFICATION_EDIT_ROUTE}/$id/$title/$content/$userId",
    navOptions = navOptions,
)

fun NavGraphBuilder.notificationEdit(userId: Long, workspaceId: String, permission: WorkspacePermissionModel, popBackStack: () -> Unit) {
    composable(
        route = "${NOTIFICATION_EDIT_ROUTE}/{id}/{title}/{content}/{userId}",
        arguments = listOf(
            navArgument("id") { type = NavType.LongType },
            navArgument("title") { type = NavType.StringType },
            navArgument("content") { type = NavType.StringType },
            navArgument("userId") { type = NavType.LongType },
        ),
    ) {
        val id = it.arguments?.getLong("id") ?: 0
        val title = it.arguments?.getString("title") ?: ""
        val content = it.arguments?.getString("content") ?: ""
        val writerId = it.arguments?.getLong("userId") ?: 0
        NotificationEditScreen(
            id = id,
            userId = userId,
            writerId = writerId,
            title = title,
            content = content,
            popBackStack = popBackStack,
            workspaceId = workspaceId,
            permission = permission,
        )
    }
}
