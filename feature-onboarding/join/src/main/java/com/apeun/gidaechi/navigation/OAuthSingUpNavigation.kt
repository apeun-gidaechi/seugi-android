package com.apeun.gidaechi.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.apeun.gidaechi.SchoolScreen


const val OAUTH_SIGN_UP = "oauthSignUp"

fun NavController.navigateToOAuthSignUp(navOptions: NavOptions? = null) = navigate(
    OAUTH_SIGN_UP, navOptions)

fun NavGraphBuilder.oauthSignUp(navHostController: NavHostController) {
    composable(route = OAUTH_SIGN_UP) {
        SchoolScreen(navHostController)
    }
}
