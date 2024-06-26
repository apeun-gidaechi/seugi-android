package com.apeun.gidaechi.notification.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.apeun.gidaechi.notification.NotificationScreen

const val NOTIFICATION_ROUTE = "notification"

fun NavController.navigateToNotification(navOptions: NavOptions?) = navigate(NOTIFICATION_ROUTE, navOptions)

fun NavGraphBuilder.notificationScreen() {
    composable(NOTIFICATION_ROUTE) {
        NotificationScreen()
    }
}