package com.seugi.room.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.seugi.room.RoomScreen

const val ROOM_ROUTE = "room"

fun NavController.navigateToRoom(navOptions: NavOptions?) = navigate(ROOM_ROUTE, navOptions)

fun NavGraphBuilder.roomScreen(
    workspaceId: String,
    userId: Long,
    navigateToChatDetail: (roomId: String, workspaceId: String) -> Unit,
    navigateToCreateRoom: (workspaceId: String, userId: Long) -> Unit,
) {
    composable(ROOM_ROUTE) {
        RoomScreen(
            workspaceId = workspaceId,
            userId = userId,
            navigateToChatDetail = navigateToChatDetail,
            navigateToCreateRoom = navigateToCreateRoom,
        )
    }
}
