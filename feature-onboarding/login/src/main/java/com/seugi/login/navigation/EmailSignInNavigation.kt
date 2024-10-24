package com.seugi.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.seugi.login.EmailSignInScreen

const val EMAIL_SIGN_IN = "emailSignIn"

fun NavController.navigateToEmailSignIn(navOptions: NavOptions? = null) = navigate(EMAIL_SIGN_IN, navOptions)

fun NavGraphBuilder.emailSignInScreen(navigateToOAuthSignUp: () -> Unit, popBackStack: () -> Unit, onboardingToMain: () -> Unit) {
    composable(route = EMAIL_SIGN_IN) {
        EmailSignInScreen(
            navigateToEmailSignUp = navigateToOAuthSignUp,
            popBackStack = popBackStack,
            onboardingToMain = onboardingToMain,
        )
    }
}
