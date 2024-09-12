package com.seugi.main

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import com.seugi.home.navigation.navigateToHome
import com.seugi.notification.navigation.NOTIFICATION_ROUTE
import com.seugi.notification.navigation.notificationScreen
import com.seugi.notificationcreate.navigation.navigateToNotificationCreate
import com.seugi.notificationcreate.navigation.notificationCreate
import com.seugi.notificationedit.navigation.navigateToNotificationEdit
import com.seugi.notificationedit.navigation.notificationEdit
import com.seugi.profile.navigation.PROFILE_ROUTE
import com.seugi.profile.navigation.profileScreen
import com.seugi.room.navigation.ROOM_ROUTE
import com.seugi.room.navigation.roomScreen
import com.seugi.roomcreate.navigation.navigateToRoomCreate
import com.seugi.roomcreate.navigation.roomCreateScreen
import com.seugi.setting.navigate.navigateToSetting
import com.seugi.setting.navigate.settingScreen
import com.seugi.workspace.navigation.WAITING_JOIN
import com.seugi.workspace.navigation.joinSuccess
import com.seugi.workspace.navigation.navigateToJoinSuccess
import com.seugi.workspace.navigation.navigateToSchoolCode
import com.seugi.workspace.navigation.navigateToSelectingJob
import com.seugi.workspace.navigation.navigateToWaitingJoin
import com.seugi.workspace.navigation.schoolCode
import com.seugi.workspace.navigation.selectingJob
import com.seugi.workspace.navigation.waitingJoin
import com.seugi.workspacecreate.navigation.navigateToWorkspaceCreate
import com.seugi.workspacecreate.navigation.workspaceCreateScreen
import com.seugi.workspacedetail.navigation.navigateToWorkspaceDetail
import com.seugi.workspacedetail.navigation.navigateToWorkspaceMember
import com.seugi.workspacedetail.navigation.workspaceDetailScreen
import com.seugi.workspacedetail.navigation.workspaceMemberScreen

private const val NAVIGATION_ANIM = 400

@Composable
internal fun MainScreen(viewModel: MainViewModel = hiltViewModel(), navHostController: NavHostController = rememberNavController(), mainToOnboarding: () -> Unit, showSnackbar: (text: String) -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()
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

    LaunchedEffect(key1 = true) {
        viewModel.loadWorkspaceId()
    }

    LaunchedEffect(state) {}

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
                navigateToJoinWorkspace = {
                    navHostController.navigateToSelectingJob()
                },
                onNavigationVisibleChange = onNavigationVisibleChange,
                navigateToWorkspaceDetail = { id ->
                    navHostController.navigateToWorkspaceDetail(
                        workspaceId = id,
                    )
                    viewModel.loadLocalWorkspaceId()
                },
                navigateToWorkspaceCreate = {
                    navHostController.navigateToWorkspaceCreate()
                },
            )

            chatScreen(
                workspaceId = state.profile.workspaceId,
                navigateToChatDetail = { chatId ->
                    navHostController.navigateToChatDetail(
                        workspaceId = state.profile.workspaceId,
                        chatRoomId = chatId,
                        isPersonal = true,
                    )
                },
            )
            chatDetailScreen(
                userId = state.userId,
                onNavigationVisibleChange = onNavigationVisibleChange,
                popBackStack = {
                    navHostController.popBackStack()
                },
            )

            roomScreen(
                workspaceId = state.profile.workspaceId,
                userId = state.userId,
                navigateToChatDetail = { chatId, workspaceId ->
                    navHostController.navigateToChatDetail(
                        chatRoomId = chatId,
                        workspaceId = workspaceId,
                        isPersonal = false,
                    )
                },
                navigateToCreateRoom = { workspaceId, userId ->
                    navHostController.navigateToRoomCreate(
                        workspaceId = workspaceId,
                        userId = userId,
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
                        workspaceId = workspaceId,
                        isPersonal = isPersonal,
                    )
                },
            )

            profileScreen(
                workspaceId = state.profile.workspaceId,
                myProfile = state.profile,
                navigateToSetting = navHostController::navigateToSetting
            )

            notificationScreen(
                workspaceId = state.profile.workspaceId,
                userId = state.userId,
                permission = state.permission,
                navigateToNotificationCreate = {
                    navHostController.navigateToNotificationCreate()
                },
                navigateToNotificationEdit = { id, title, content, userId ->
                    navHostController.navigateToNotificationEdit(
                        id = id,
                        title = title,
                        content = content,
                        userId = userId,
                    )
                },
            )

            chatSeugiScreen(
                onNavigationVisibleChange = onNavigationVisibleChange,
                popBackStack = {
                    navHostController.popBackStack()
                },
            )
            schoolCode(
                navigateToJoinSuccess = { schoolCode, workspaceId, workspaceName, workspaceImageUrl, studentCount, teacherCount, role ->
                    navHostController.navigateToJoinSuccess(
                        schoolCode = schoolCode,
                        workspaceId = workspaceId,
                        workspaceName = workspaceName,
                        workspaceImageUrl = workspaceImageUrl,
                        studentCount = studentCount,
                        teacherCount = teacherCount,
                        role = role,
                    )
                },
                popBackStack = { navHostController.popBackStack() },
            )
            joinSuccess(
                navigateToWaiting = {
                    navHostController.navigateToWaitingJoin()
                },
                popBackStack = { navHostController.popBackStack() },
            )
            selectingJob(
                navigateToSelectingRole = { role ->
                    navHostController.navigateToSchoolCode(
                        role = role,
                    )
                },
                popBackStack = { navHostController.popBackStack() },
            )
            waitingJoin(
                joinToHome = {
                    while (navHostController.popBackStack()) {
                    }
                    navHostController.navigateToHome(
                        toRoute = HOME_ROUTE,
                        fromRoute = WAITING_JOIN,
                    )
                },
                popBackStack = { navHostController.popBackStack() },
            )

            notificationCreate(
                workspaceId = state.profile.workspaceId,
                popBackStack = navHostController::popBackStack,
                onNavigationVisibleChange = onNavigationVisibleChange,
            )

            notificationEdit(
                userId = state.userId,
                workspaceId = state.profile.workspaceId,
                permission = state.permission,
                popBackStack = navHostController::popBackStack,
                onNavigationVisibleChange = onNavigationVisibleChange,
            )
            workspaceDetailScreen(
                navigateToJoinWorkspace = {
                    navHostController.navigateToSelectingJob()
                },
                popBackStack = {
                    navHostController.popBackStack()
                },
                navigateToWorkspaceMember = { workspaceId ->
                    navHostController.navigateToWorkspaceMember(
                        workspaceId,
                    )
                },
                navigateToCreateWorkspace = {
                    navHostController.navigateToWorkspaceCreate()
                },
                changeWorkspace = {
                    viewModel.loadLocalWorkspaceId()
                },
            )
            workspaceMemberScreen(
                popBackStack = { navHostController.popBackStack() },
            )
            workspaceCreateScreen(
                popBackStack = {
                    navHostController.popBackStack()
                },
            )

            settingScreen(
                onNavigationVisibleChange = onNavigationVisibleChange,
                navigationToOnboarding = mainToOnboarding,
                popBackStack = navHostController::popBackStack,
                showSnackbar = showSnackbar
            )
        }
    }
}
