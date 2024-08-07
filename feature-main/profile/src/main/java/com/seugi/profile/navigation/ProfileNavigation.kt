package com.seugi.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.seugi.profile.ProfileScreen

const val PROFILE_ROUTE = "profile"

fun NavController.navigateToProfile(navOptions: NavOptions?) = navigate(PROFILE_ROUTE, navOptions)

fun NavGraphBuilder.profileScreen(workspaceId: String) {
    composable(PROFILE_ROUTE) {
        ProfileScreen(
            workspaceId = workspaceId
        )
    }
}
