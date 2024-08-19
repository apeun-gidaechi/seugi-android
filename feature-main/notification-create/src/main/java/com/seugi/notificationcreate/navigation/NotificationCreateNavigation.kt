package com.seugi.notificationcreate.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.seugi.notificationcreate.NotificationCreateScreen

const val NOTIFICATION_CREATE_ROUTE = "notification_create"

fun NavController.navigateToNotificationCreate(navOptions: NavOptions? = null) = this.navigate(
    route = NOTIFICATION_CREATE_ROUTE,
    navOptions = navOptions
)

fun NavGraphBuilder.notificationCreate(
    onNavigationVisibleChange: (visible: Boolean) -> Unit,
    popBackStack: () -> Unit
) {
    composable(NOTIFICATION_CREATE_ROUTE) {
        NotificationCreateScreen(
            popBackStack = popBackStack,
            onNavigationVisibleChange = onNavigationVisibleChange
        )
    }
}