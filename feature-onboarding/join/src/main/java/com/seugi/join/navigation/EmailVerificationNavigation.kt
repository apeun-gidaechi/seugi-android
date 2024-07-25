package com.seugi.join.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.seugi.join.feature.emailverification.EmailVerificationScreen

const val EMAIL_VERIFICATION_ROUTE = "emailVerification"

fun NavController.navigateToEmailVerification(navOptions: NavOptions? = null, name: String, email: String, password: String) =
    navigate("$EMAIL_VERIFICATION_ROUTE/$name/$email/$password", navOptions)

fun NavGraphBuilder.emailVerificationScreen(navigateToStart: () -> Unit, popBackStack: () -> Unit) {
    composable(
        route = "$EMAIL_VERIFICATION_ROUTE/{name}/{email}/{password}",
        arguments = listOf(
            navArgument("name") { NavType.StringType },
            navArgument("email") { NavType.StringType },
            navArgument("password") { NavType.StringType },
        ),
    ) {
        EmailVerificationScreen(
            navigateToStart = navigateToStart,
            popBackStack = popBackStack,
            name = it.arguments?.getString("name") ?: "",
            email = it.arguments?.getString("email") ?: "",
            password = it.arguments?.getString("password") ?: "",
        )
    }
}
