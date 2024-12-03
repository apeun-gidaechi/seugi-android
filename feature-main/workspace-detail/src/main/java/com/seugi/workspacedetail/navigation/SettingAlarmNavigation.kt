package com.seugi.workspacedetail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.seugi.workspacedetail.feature.settingalarm.SettingAlarmScreen

const val WORKSPACE_SETTING_ALARM_ROUTE = "workspace_setting_alarm_route"

fun NavController.navigateToWorkspaceSettingAlarm(navOptions: NavOptions? = null) =
    navigate(WORKSPACE_SETTING_ALARM_ROUTE, navOptions)

fun NavGraphBuilder.workspaceSettingAlarmScreen(
    workspaceId: String,
    popBackStack: () -> Unit
) {
    composable(WORKSPACE_SETTING_ALARM_ROUTE) {
        SettingAlarmScreen(
            workspaceId = workspaceId,
            popBackStack = popBackStack
        )
    }
}