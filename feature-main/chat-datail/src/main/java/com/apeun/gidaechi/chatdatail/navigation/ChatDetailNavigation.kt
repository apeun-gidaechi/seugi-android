package com.apeun.gidaechi.chatdatail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.apeun.gidaechi.chatdatail.ChatDetailScreen

const val CHAT_DETAIL_ROUTE = "chat_detail"

fun NavController.navigateToChatDetail(navOptions: NavOptions? = null) = navigate(CHAT_DETAIL_ROUTE, navOptions)

fun NavGraphBuilder.chatDetailScreen(onNavigationVisibleChange: (Boolean) -> Unit, popBackStack: () -> Unit) {
    composable(CHAT_DETAIL_ROUTE) {
        ChatDetailScreen(
            onNavigationVisibleChange = onNavigationVisibleChange,
            popBackStack = popBackStack,
        )
    }
}
