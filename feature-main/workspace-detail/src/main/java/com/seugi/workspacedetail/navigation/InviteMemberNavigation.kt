package com.seugi.workspacedetail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.seugi.workspacedetail.feature.invitemember.InviteMemberScreen

const val INVITE_MEMBER_ROUTE = "INVITE_MEMBER_ROUTE"

fun NavController.navigateToInviteMember(navOptions: NavOptions? = null) {
    navigate(
        route = INVITE_MEMBER_ROUTE,
        navOptions = navOptions,
    )
}

fun NavGraphBuilder.inviteMemberScreen(popBackStack: () -> Unit, workspaceId: String) {
    composable(
        route = INVITE_MEMBER_ROUTE,
    ) {
        InviteMemberScreen(
            workspaceId = workspaceId,
            popBackStack = popBackStack,
        )
    }
}
