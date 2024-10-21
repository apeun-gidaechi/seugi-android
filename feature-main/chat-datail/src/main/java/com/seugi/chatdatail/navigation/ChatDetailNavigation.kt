package com.seugi.chatdatail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.seugi.chatdatail.ChatDetailScreen

const val CHAT_DETAIL_ROUTE = "chat_detail"

fun NavController.navigateToChatDetail(
    workspaceId: String = "664bdd0b9dfce726abd30462",
    isPersonal: Boolean = false,
    chatRoomId: String = "66698a18d3f6963445f6f84f",
    navOptions: NavOptions? = null,
) = navigate(
    "$CHAT_DETAIL_ROUTE/$workspaceId/$isPersonal/$chatRoomId",
    navOptions,
)

fun NavGraphBuilder.chatDetailScreen(userId: Long, navigateToChatDetail: (workspaceId: String, chatRoomId: String) -> Unit, popBackStack: () -> Unit) {
    composable(
        route = "$CHAT_DETAIL_ROUTE/{workspaceId}/{isPersonal}/{chatRoomId}",
        arguments = listOf(
            navArgument("workspaceId") { NavType.StringType },
            navArgument("isPersonal") { NavType.BoolType },
            navArgument("chatRoomId") { NavType.StringType },
        ),
    ) {
        ChatDetailScreen(
            userId = userId,
            workspaceId = it.arguments?.getString("workspaceId") ?: "",
            isPersonal = it.arguments?.getString("isPersonal") == "true",
            chatRoomId = it.arguments?.getString("chatRoomId") ?: "",
            navigateToChatDetail = navigateToChatDetail,
            popBackStack = popBackStack,
        )
    }
}
