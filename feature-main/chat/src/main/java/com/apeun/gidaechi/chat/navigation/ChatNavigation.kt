package com.apeun.gidaechi.chat.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.apeun.gidaechi.chat.ChatScreen

const val CHAT_ROUTE = "chat"

fun NavController.navigateToChat(navOptions: NavOptions?) = navigate(CHAT_ROUTE, navOptions)

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.chatScreen(navigateToChatDetail: (chatID: String, workspaceId: String) -> Unit) {
    composable(CHAT_ROUTE) {
        ChatScreen(
            navigateToChatDetail = navigateToChatDetail,
        )
    }
}
