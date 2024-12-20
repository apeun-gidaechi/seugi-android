package com.seugi.notification.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.seugi.data.core.model.WorkspacePermissionModel
import com.seugi.notification.NotificationScreen

const val NOTIFICATION_ROUTE = "notification"

fun NavController.navigateToNotification(navOptions: NavOptions?) = navigate(NOTIFICATION_ROUTE, navOptions)

fun NavGraphBuilder.notificationScreen(
    workspaceId: String,
    userId: Long,
    permission: WorkspacePermissionModel,
    navigateToNotificationCreate: () -> Unit,
    navigateToNotificationEdit: (id: Long, title: String, content: String, userId: Long) -> Unit,
) {
    composable(NOTIFICATION_ROUTE) {
        NotificationScreen(
            workspaceId = workspaceId,
            userId = userId,
            permission = permission,
            navigateToNotificationCreate = navigateToNotificationCreate,
            navigateToNotificationEdit = navigateToNotificationEdit,
        )
    }
}
