package com.seugi.timetable.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.seugi.timetable.TimetableScreen

const val TIMETABLE_ROUTE = "timetable"

fun NavController.navigateToTimetable(navOptions: NavOptions? = null) {
    navigate(
        route = TIMETABLE_ROUTE,
        navOptions = navOptions,
    )
}

fun NavGraphBuilder.timetableScreen(popBackStack: () -> Unit, workspaceId: String) {
    composable(
        route = TIMETABLE_ROUTE,
    ) {
        TimetableScreen(
            workspaceId = workspaceId,
            popBackStack = popBackStack,
        )
    }
}
