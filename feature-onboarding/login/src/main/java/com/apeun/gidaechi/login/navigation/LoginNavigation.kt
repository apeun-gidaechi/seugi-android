package com.apeun.gidaechi.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.apeun.gidaechi.login.LoginScreen

const val LOGIN_ROUTE = "login"

fun NavController.navigateToLogin(navOptions: NavOptions? = null) = navigate(LOGIN_ROUTE, navOptions)

fun NavGraphBuilder.loginScreen() {
    composable(route = LOGIN_ROUTE) {
        LoginScreen()
    }
}
