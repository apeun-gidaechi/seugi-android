package com.seugi.notification.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.seugi.notification.NotificationScreen

const val NOTIFICATION_ROUTE = "notification"

fun NavController.navigateToNotification(navOptions: NavOptions?) = navigate(NOTIFICATION_ROUTE, navOptions)

fun NavGraphBuilder.notificationScreen(
    workspaceId: String
) {
    composable(NOTIFICATION_ROUTE) {
        NotificationScreen(
            workspaceId = workspaceId
        )
    }
}
