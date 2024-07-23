package com.seugi.join.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.seugi.join.OAuthSignUpScreen

const val OAUTH_SIGN_UP = "oauthSignUp"

fun NavController.navigateToOAuthSignUp(navOptions: NavOptions? = null) = navigate(
    OAUTH_SIGN_UP,
    navOptions,
)

fun NavGraphBuilder.oauthSignUp(popBackStack: () -> Unit, navigateToEmailSignUp: () -> Unit) {
    composable(route = OAUTH_SIGN_UP) {
        OAuthSignUpScreen(popBackStack = popBackStack, navigateToEmailSignUp = navigateToEmailSignUp)
    }
}
