package com.apeun.gidaechi.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.apeun.gidaechi.SelectingJobScreen

const val SELECTING_JOB = "selectingJob"

fun NavController.navigateToSelectingJob(navOptions: NavOptions? = null) = navigate(
    SELECTING_JOB, navOptions)

fun NavGraphBuilder.selectingJob(navHostController: NavHostController) {
    composable(route = SELECTING_JOB) {
        SelectingJobScreen(navHostController)
    }
}
