package com.apeun.gidaechi.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.apeun.gidaechi.SchoolScreen

const val SCHOOL_CODE = "schoolCode"

fun NavController.navigateToSchoolCode(navOptions: NavOptions? = null) = navigate(
    SCHOOL_CODE, navOptions)

fun NavGraphBuilder.schoolCode(navHostController: NavHostController) {
    composable(route = SCHOOL_CODE) {
        SchoolScreen(navHostController)
    }
}
