package com.apeun.gidaechi.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.apeun.gidaechi.JoinSuccessScreen

const val JOIN_SUCCESS = "joinSuccess"

fun NavController.navigateToJoinSuccess(navOptions: NavOptions? = null) = navigate(
    JOIN_SUCCESS,
    navOptions,
)

fun NavGraphBuilder.joinSuccess(navHostController: NavHostController) {
    composable(route = JOIN_SUCCESS) {
        JoinSuccessScreen(navHostController)
    }
}
