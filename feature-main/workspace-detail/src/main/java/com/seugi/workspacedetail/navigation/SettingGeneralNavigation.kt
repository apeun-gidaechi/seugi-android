package com.seugi.workspacedetail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.seugi.workspacedetail.feature.settinggeneral.SettingGeneralScreen

const val WORKSPACE_SETTING_GENERAL_ROUTE = "workspace_setting_general"

fun NavController.navigateToWorkspaceSettingGeneral(navOptions: NavOptions? = null) =
    this.navigate(WORKSPACE_SETTING_GENERAL_ROUTE, navOptions)

fun NavGraphBuilder.workspaceSettingGeneralScreen(
    popBackStack: () -> Unit
) {
    composable(WORKSPACE_SETTING_GENERAL_ROUTE) {
        SettingGeneralScreen(
            popBackStack = popBackStack
        )
    }
}