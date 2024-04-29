package com.apeun.gidaechi.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val OAUTH_SIGN_UP = "oauthSignUp"

fun NavController.navigateToOAutSignUp(navOptions: NavOptions? = null) = navigate(
    OAUTH_SIGN_UP, navOptions)

fun NavGraphBuilder.oauthSignUp() {
    composable(route = OAUTH_SIGN_UP) {
        com.apeun.gidaechi.login.OAuthSignUpScreen()
    }
}
