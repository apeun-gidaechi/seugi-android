package com.apeun.gidaechi.navigation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.apeun.gidaechi.StartScreen

const val START_ROUTE = "start"

fun NavController.navigateToStart(navOptions: NavOptions? = null) = navigate(START_ROUTE, navOptions)

fun NavGraphBuilder.startScreen(navigateToEmailSignIn: () -> Unit, navigateToOAuthSignIn: () -> Unit) {
    composable(route = START_ROUTE) {
        Log.d("TAG", "$navigateToEmailSignIn: ")
        StartScreen(navigateToEmailSignIn = navigateToEmailSignIn, navigateToOAuthSignIn = navigateToOAuthSignIn)
    }
}