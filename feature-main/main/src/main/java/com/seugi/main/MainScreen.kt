package com.seugi.main

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.seugi.chat.navigation.CHAT_ROUTE
import com.seugi.chat.navigation.chatScreen
import com.seugi.chatdatail.navigation.chatDetailScreen
import com.seugi.chatdatail.navigation.navigateToChatDetail
import com.seugi.chatseugi.navigation.chatSeugiScreen
import com.seugi.chatseugi.navigation.navigateToChatSeugi
import com.seugi.designsystem.component.BottomNavigationItemType
import com.seugi.designsystem.component.SeugiBottomNavigation
import com.seugi.home.navigation.HOME_ROUTE
import com.seugi.home.navigation.homeScreen
import com.seugi.notification.navigation.NOTIFICATION_ROUTE
import com.seugi.notification.navigation.notificationScreen
import com.seugi.profile.navigation.PROFILE_ROUTE
import com.seugi.profile.navigation.profileScreen
import com.seugi.room.navigation.ROOM_ROUTE
import com.seugi.room.navigation.roomScreen
import com.seugi.roomcreate.navigation.navigateToRoomCreate
import com.seugi.roomcreate.navigation.roomCreateScreen

private const val NAVIGATION_ANIM = 400

@Composable
internal fun MainScreen(navHostController: NavHostController = rememberNavController(), mainToOnboarding: () -> Unit) {
    val backstackEntry by navHostController.currentBackStackEntryAsState()
    val selectItemState: BottomNavigationItemType by remember {
        derivedStateOf {
            when (backstackEntry?.destination?.route) {
                HOME_ROUTE -> BottomNavigationItemType.Home
                CHAT_ROUTE -> BottomNavigationItemType.Chat
                ROOM_ROUTE -> BottomNavigationItemType.Group
                NOTIFICATION_ROUTE -> BottomNavigationItemType.Notification
                PROFILE_ROUTE -> BottomNavigationItemType.Profile
                else -> BottomNavigationItemType.Home
            }
        }
    }
    var navigationVisible by remember { mutableStateOf(true) }
    val onNavigationVisibleChange: (Boolean) -> Unit = {
        navigationVisible = it
    }

    BackHandler {
        mainToOnboarding()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (navigationVisible) {
                SeugiBottomNavigation(selected = selectItemState) {
                    navHostController.navigate(
                        when (it) {
                            is BottomNavigationItemType.Home -> HOME_ROUTE
                            is BottomNavigationItemType.Chat -> CHAT_ROUTE
                            is BottomNavigationItemType.Group -> ROOM_ROUTE
                            is BottomNavigationItemType.Notification -> NOTIFICATION_ROUTE
                            is BottomNavigationItemType.Profile -> PROFILE_ROUTE
                            else -> HOME_ROUTE
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
            homeScreen(
                navigateToChatSeugi = {
                    navHostController.navigateToChatSeugi()
                },
            )

            chatScreen(
                navigateToChatDetail = { chatId, workspaceId ->
                    navHostController.navigateToChatDetail(
                        chatRoomId = chatId,
                        workspace = workspaceId,
                        isPersonal = true,
                    )
                },
            )
            chatDetailScreen(
                onNavigationVisibleChange = onNavigationVisibleChange,
                popBackStack = {
                    navHostController.popBackStack()
                },
            )

            roomScreen(
                navigateToChatDetail = { chatId, workspaceId ->
                    navHostController.navigateToChatDetail(
                        chatRoomId = chatId,
                        workspace = workspaceId,
                        isPersonal = false,
                    )
                },
                navigateToCreateRoom = { workspaceId ->
                    navHostController.navigateToRoomCreate(
                        workspaceId = workspaceId,
                    )
                },
            )

            roomCreateScreen(
                popBackStack = {
                    navHostController.popBackStack()
                },
                onNavigationVisibleChange = onNavigationVisibleChange,
                navigateToChatDetail = { chatId, workspaceId, isPersonal ->
                    navHostController.navigateToChatDetail(
                        chatRoomId = chatId,
                        workspace = workspaceId,
                        isPersonal = isPersonal,
                    )
                },
            )

            profileScreen()

            notificationScreen()

            chatSeugiScreen(
                onNavigationVisibleChange = onNavigationVisibleChange,
                popBackStack = {
                    navHostController.popBackStack()
                },
            )
        }
    }
}