package com.apeun.gidaechi.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.apeun.gidaechi.EmailVerificationScreen


const val EMAIL_VERIFICATION_ROUTE = "emailVerification"

fun NavController.navigateToEmailVerification(navOptions: NavOptions? = null) = navigate(
    EMAIL_VERIFICATION_ROUTE, navOptions)

fun NavGraphBuilder.emailVerificationScreen(navHostController: NavHostController) {
    composable(route = EMAIL_VERIFICATION_ROUTE) {
        EmailVerificationScreen(navHostController)
    }
}
