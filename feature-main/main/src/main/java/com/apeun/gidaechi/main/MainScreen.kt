package com.apeun.gidaechi.main

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.apeun.gidaechi.chat.navigation.CHAT_ROUTE
import com.apeun.gidaechi.chat.navigation.chatScreen
import com.apeun.gidaechi.chatdatail.navigation.chatDetailScreen
import com.apeun.gidaechi.chatdatail.navigation.navigateToChatDetail
import com.apeun.gidaechi.designsystem.component.BottomNavigationItemType
import com.apeun.gidaechi.designsystem.component.SeugiBottomNavigation
import com.apeun.gidaechi.home.navigation.HOME_ROUTE
import com.apeun.gidaechi.home.navigation.homeScreen

private const val NAVIGATION_ANIM = 400

@Composable
internal fun MainScreen(navHostController: NavHostController = rememberNavController()) {
    var selectItemState: BottomNavigationItemType by remember { mutableStateOf(BottomNavigationItemType.Home) }
    var navigationVisible by remember { mutableStateOf(true) }

    val onNavigationVisibleChange: (Boolean) -> Unit = {
        navigationVisible = it
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (navigationVisible) {
                SeugiBottomNavigation(selected = selectItemState) {
                    selectItemState = it
                    navHostController.navigate(
                        when (it) {
                            is BottomNavigationItemType.Home -> HOME_ROUTE
                            is BottomNavigationItemType.Chat -> CHAT_ROUTE
                            is BottomNavigationItemType.Group -> "route"
                            is BottomNavigationItemType.Notification -> "route"
                            is BottomNavigationItemType.Profile -> "route"
                            else -> "route"
                        },
                    ) {
                        popUpTo(navHostController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        },
    ) {
        NavHost(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            navController = navHostController,
            startDestination = HOME_ROUTE,
            enterTransition = { fadeIn(animationSpec = tween(NAVIGATION_ANIM)) },
            exitTransition = { fadeOut(animationSpec = tween(NAVIGATION_ANIM)) },
            popEnterTransition = { fadeIn(animationSpec = tween(NAVIGATION_ANIM)) },
            popExitTransition = { fadeOut(animationSpec = tween(NAVIGATION_ANIM)) },
        ) {
            // TODO("DELETE DUMMY ROUTE")
            composable("route") {
                Text(text = "hi")
            }

            homeScreen()

            chatScreen(
                navigateToChatDetail = {
                    navHostController.navigateToChatDetail()
                },
            )
            chatDetailScreen(
                onNavigationVisibleChange = onNavigationVisibleChange,
                popBackStack = {
                    navHostController.popBackStack()
                },
            )
        }
    }
}
