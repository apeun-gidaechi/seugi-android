package com.apeun.gidaechi.roomcreate.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.apeun.gidaechi.roomcreate.RoomCreateScreen

const val ROOM_CREATE_ROUTE = "room_create"

fun NavController.navigateToRoomCreate(
    workspaceId: String,
    navOptions: NavOptions? = null
) = navigate(
    route = "${ROOM_CREATE_ROUTE}/${workspaceId}",
    navOptions = navOptions
)

fun NavGraphBuilder.roomCreateScreen(popBackStack: () -> Unit, onNavigationVisibleChange: (Boolean) -> Unit) {
    composable(
        route = "${ROOM_CREATE_ROUTE}/{workspaceId}",
        arguments = listOf(
            navArgument("workspaceId") { NavType.StringType }
        )
    ) {
        RoomCreateScreen(
            popBackStack = popBackStack,
            onNavigationVisibleChange = onNavigationVisibleChange,
            workspaceId = it.arguments?.getString("workspaceId")?: "",
        )
    }
}
