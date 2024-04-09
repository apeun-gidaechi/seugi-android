package com.apeun.gidaechi.main

import android.util.Log
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
import com.apeun.gidaechi.designsystem.component.BottomNavigationItemType
import com.apeun.gidaechi.designsystem.component.SeugiBottomNavigation

private const val NAVIGATION_ANIM = 400

@Composable
internal fun MainScreen(navHostController: NavHostController = rememberNavController()) {
    var selectItemState: BottomNavigationItemType by remember { mutableStateOf(BottomNavigationItemType.Home) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            SeugiBottomNavigation(selected = selectItemState) {
                selectItemState = it
                navHostController.navigate(
                    when (it) {
                        is BottomNavigationItemType.Home -> "route"
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
        },
    ) {
        NavHost(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            navController = navHostController,
            startDestination = "route",
            enterTransition = { fadeIn(animationSpec = tween(NAVIGATION_ANIM)) },
            exitTransition = { fadeOut(animationSpec = tween(NAVIGATION_ANIM)) },
            popEnterTransition = { fadeIn(animationSpec = tween(NAVIGATION_ANIM)) },
            popExitTransition = { fadeOut(animationSpec = tween(NAVIGATION_ANIM)) },
        ) {
            // TODO("DELETE DUMMY ROUTE")
            composable("route") {
                Text(text = "hi")
            }

            chatScreen(
                navigateToChatDetail = {
                    Log.d("TAG", "MainScreen: $it")
                },
            )
        }
    }
}
