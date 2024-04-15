package com.apeun.gidaechi.roomcreate.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.apeun.gidaechi.roomcreate.RoomCreateScreen

const val ROOM_CREATE_ROUTE = "room_create"

fun NavController.navigateToRoomCreate(navOptions: NavOptions?) = navigate(ROOM_CREATE_ROUTE, navOptions)

fun NavGraphBuilder.roomCreateScreen(popBackStack: () -> Unit) {
    composable(ROOM_CREATE_ROUTE) {
        RoomCreateScreen(
            popBackStack = popBackStack,
        )
    }
}
