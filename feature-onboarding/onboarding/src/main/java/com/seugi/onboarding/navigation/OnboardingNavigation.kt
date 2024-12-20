package com.seugi.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.seugi.onboarding.OnboardingScreen

const val ONBOARDING_ROUTE = "onboarding"

fun NavController.navigateToOnboarding(navOptions: NavOptions? = null) = navigate(ONBOARDING_ROUTE, navOptions)

fun NavGraphBuilder.onboardingScreen(onboardingToMain: () -> Unit) {
    composable(route = ONBOARDING_ROUTE) {
        OnboardingScreen(onboardingToMain = onboardingToMain)
    }
}
