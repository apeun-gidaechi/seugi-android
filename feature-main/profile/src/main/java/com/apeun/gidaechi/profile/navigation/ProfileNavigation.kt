package com.apeun.gidaechi.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.apeun.gidaechi.profile.ProfileScreen

const val PROFILE_ROUTE = "profile"

fun NavController.navigateToProfile(navOptions: NavOptions?) = navigate(PROFILE_ROUTE, navOptions)

fun NavGraphBuilder.profileScreen() {
    composable(PROFILE_ROUTE) {
        ProfileScreen()
    }
}