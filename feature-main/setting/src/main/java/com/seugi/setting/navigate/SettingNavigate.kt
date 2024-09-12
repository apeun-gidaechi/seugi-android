package com.seugi.setting.navigate

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.seugi.setting.SettingScreen

const val SETTING_ROUTE = "setting"

fun NavController.navigateToSetting(navOptions: NavOptions? = null) =
    this.navigate(
        route = SETTING_ROUTE,
        navOptions = navOptions
    )

fun NavGraphBuilder.settingScreen(
    onNavigationVisibleChange: (Boolean) -> Unit,
    navigationToOnboarding: () -> Unit,
    popBackStack: () -> Unit,
    showSnackbar: (text: String) -> Unit,
) {
    composable(SETTING_ROUTE) {
        SettingScreen(
            onNavigationVisibleChange = onNavigationVisibleChange,
            navigationToOnboarding = navigationToOnboarding,
            popBackStack = popBackStack,
            showSnackbar = showSnackbar
        )
    }
}