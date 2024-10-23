package com.seugi.meal.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.seugi.meal.MealScreen

const val MEAL_ROUTE = "meal"

fun NavController.navigateToMeal(navOptions: NavOptions? = null) = this.navigate(MEAL_ROUTE)

fun NavGraphBuilder.mealScreen(
    workspaceId: String,
    popBackStack: () -> Unit
) {
    composable(MEAL_ROUTE) {
        MealScreen(
            workspaceId = workspaceId,
            popBackStack = popBackStack
        )
    }
}