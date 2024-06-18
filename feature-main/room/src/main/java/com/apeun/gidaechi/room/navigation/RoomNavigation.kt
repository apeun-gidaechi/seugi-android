package com.apeun.gidaechi.room.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.apeun.gidaechi.room.RoomScreen

const val ROOM_ROUTE = "room"

fun NavController.navigateToRoom(navOptions: NavOptions?) = navigate(ROOM_ROUTE, navOptions)

fun NavGraphBuilder.roomScreen(navigateToChatDetail: (String) -> Unit, navigateToCreateRoom: () -> Unit) {
    composable(ROOM_ROUTE) {
        RoomScreen(
            navigateToChatDetail = navigateToChatDetail,
            navigateToCreateRoom = navigateToCreateRoom,
        )
    }
}
