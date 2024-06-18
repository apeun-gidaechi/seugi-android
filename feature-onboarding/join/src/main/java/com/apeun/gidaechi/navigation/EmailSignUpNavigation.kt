package com.apeun.gidaechi.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.apeun.gidaechi.screen.EmailSignUpScreen

const val EMAIL_SIGN_UP_ROUTE = "emailSignUp"

fun NavController.navigateToEmailSignUp(navOptions: NavOptions? = null) = navigate(EMAIL_SIGN_UP_ROUTE, navOptions)

fun NavGraphBuilder.emailSignUpScreen(navigateToEmailVerification: () -> Unit, popBackStack: () -> Unit) {
    composable(route = EMAIL_SIGN_UP_ROUTE) {
        EmailSignUpScreen(navigateToEmailVerification = navigateToEmailVerification, popBackStack = popBackStack)
    }
}
