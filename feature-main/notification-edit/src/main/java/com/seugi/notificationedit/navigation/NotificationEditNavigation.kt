package com.seugi.notificationedit.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.seugi.notificationedit.NotificationEditScreen

const val NOTIFICATION_EDIT_ROUTE = "notification_create"

fun NavController.navigateToNotificationEdit(id: Long, navOptions: NavOptions? = null) = this.navigate(
    route = "${NOTIFICATION_EDIT_ROUTE}/${id}",
    navOptions = navOptions
)

fun NavGraphBuilder.notificationEdit(
    onNavigationVisibleChange: (visible: Boolean) -> Unit,
    popBackStack: () -> Unit
) {
    composable(
        route = "${NOTIFICATION_EDIT_ROUTE}/{id}",
        arguments = listOf(
            navArgument("id") { type = NavType.LongType}
        )
    ) {
        val id = it.arguments?.getLong("id")?: 0
        NotificationEditScreen(
            id = id,
            popBackStack = popBackStack,
            onNavigationVisibleChange = onNavigationVisibleChange
        )
    }
}