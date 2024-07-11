package com.apeun.gidaechi.chatseugi.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.apeun.gidaechi.chatseugi.ChatSeugiScreen

const val CHAT_SEUGI_ROUTE = "chat_seugi"

fun NavController.navigateToChatSeugi(navOptions: NavOptions? = null) = navigate(CHAT_SEUGI_ROUTE, navOptions)

fun NavGraphBuilder.chatSeugiScreen(
    onNavigationVisibleChange: (Boolean) -> Unit,
    popBackStack: () -> Unit
) {
    composable(CHAT_SEUGI_ROUTE) {
        ChatSeugiScreen(
            onNavigationVisibleChange = onNavigationVisibleChange,
            popBackStack = popBackStack
        )
    }
}