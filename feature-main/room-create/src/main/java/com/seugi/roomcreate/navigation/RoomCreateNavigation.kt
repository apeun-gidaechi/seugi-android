package com.seugi.roomcreate.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.seugi.roomcreate.RoomCreateScreen

const val ROOM_CREATE_ROUTE = "room_create"

fun NavController.navigateToRoomCreate(workspaceId: String, userId: Int, navOptions: NavOptions? = null) = navigate(
    route = "$ROOM_CREATE_ROUTE/$workspaceId/$userId",
    navOptions = navOptions,
)

fun NavGraphBuilder.roomCreateScreen(popBackStack: () -> Unit, navigateToChatDetail: (chatId: String, workspaceId: String, isPersonal: Boolean) -> Unit) {
    composable(
        route = "$ROOM_CREATE_ROUTE/{workspaceId}/{userId}",
        arguments = listOf(
            navArgument("workspaceId") { NavType.StringType },
            navArgument("userId") { type = NavType.IntType },
        ),
    ) {
        RoomCreateScreen(
            popBackStack = popBackStack,
            workspaceId = it.arguments?.getString("workspaceId") ?: "",
            navigateToChatDetail = navigateToChatDetail,
            userId = it.arguments?.getInt("userId") ?: 0,
        )
    }
}
