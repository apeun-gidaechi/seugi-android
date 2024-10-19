package com.seugi.workspacedetail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.seugi.data.workspace.model.WorkspaceModel
import com.seugi.workspacedetail.feature.invitemember.InviteMemberScreen
import com.seugi.workspacedetail.feature.workspacedetail.WorkspaceDetailScreen

const val INVITE_MEMBER_ROUTE = "INVITE_MEMBER_ROUTE"

fun NavController.navigateToInviteMember(navOptions: NavOptions? = null) {
    navigate(
        route = INVITE_MEMBER_ROUTE,
        navOptions = navOptions,
    )
}

fun NavGraphBuilder.inviteMemberScreen(
    popBackStack: () -> Unit,
) {
    composable(
        route = INVITE_MEMBER_ROUTE,
    ) {
        InviteMemberScreen(
            popBackStack = popBackStack
        )
    }
}