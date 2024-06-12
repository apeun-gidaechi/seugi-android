package com.apeun.gidaechi.chatdatail.navigation

import androidx.navigation.NavArgument
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.apeun.gidaechi.chatdatail.ChatDetailScreen

const val CHAT_DETAIL_ROUTE = "chat_detail"

fun NavController.navigateToChatDetail(workspace: String = "664bdd0b9dfce726abd30462", isPersonal: Boolean = false, chatRoomId: String = "665d9ec15e65717b19a62701", navOptions: NavOptions? = null) = navigate("${CHAT_DETAIL_ROUTE}/${workspace}/${isPersonal}/${chatRoomId}", navOptions)

fun NavGraphBuilder.chatDetailScreen(
    onNavigationVisibleChange: (Boolean) -> Unit,
    popBackStack: () -> Unit
) {
    composable(
        route = "${CHAT_DETAIL_ROUTE}/{workspace}/{isPersonal}/{chatRoomId}",
        arguments = listOf(
            navArgument("workspace") { NavType.StringType},
            navArgument("isPersonal") { NavType.BoolType},
            navArgument("chatRoomId") { NavType.StringType},
        )
    ) {
        ChatDetailScreen(
            workspace = it.arguments?.getString("workspace")?: "",
            isPersonal = it.arguments?.getString("isPersonal") == "true",
            chatRoomId = it.arguments?.getString("chatRoomId")?: "",
            onNavigationVisibleChange = onNavigationVisibleChange,
            popBackStack = popBackStack,
        )
    }
}
