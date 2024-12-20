package com.seugi.main

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.seugi.assignment.navigation.assignmentScreen
import com.seugi.assignment.navigation.navigateToAssignment
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
import com.seugi.meal.navigation.mealScreen
import com.seugi.meal.navigation.navigateToMeal
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
import com.seugi.task.create.navigation.navigateToTaskCreate
import com.seugi.task.create.navigation.taskCreateScreen
import com.seugi.timetable.navigation.navigateToTimetable
import com.seugi.timetable.navigation.timetableScreen
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
import com.seugi.workspacedetail.navigation.inviteMemberScreen
import com.seugi.workspacedetail.navigation.navigateToInviteMember
import com.seugi.workspacedetail.navigation.navigateToWorkspaceDetail
import com.seugi.workspacedetail.navigation.navigateToWorkspaceMember
import com.seugi.workspacedetail.navigation.navigateToWorkspaceSettingAlarm
import com.seugi.workspacedetail.navigation.navigateToWorkspaceSettingGeneral
import com.seugi.workspacedetail.navigation.workspaceDetailScreen
import com.seugi.workspacedetail.navigation.workspaceMemberScreen
import com.seugi.workspacedetail.navigation.workspaceSettingAlarmScreen
import com.seugi.workspacedetail.navigation.workspaceSettingGeneralScreen
import kotlinx.coroutines.delay

private const val NAVIGATION_ANIM = 400
private const val LOAD_WORKSPACE_DELAY = 10000L

@Composable
internal fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navHostController: NavHostController = rememberNavController(),
    mainToOnboarding: () -> Unit,
    showSnackbar: (text: String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val nowRoute = navHostController.currentBackStackEntryAsState().value?.destination?.route

    val selectItemState: BottomNavigationItemType =
        when (nowRoute) {
            HOME_ROUTE -> BottomNavigationItemType.Home
            CHAT_ROUTE -> BottomNavigationItemType.Chat
            ROOM_ROUTE -> BottomNavigationItemType.Group
            NOTIFICATION_ROUTE -> BottomNavigationItemType.Notification
            PROFILE_ROUTE -> BottomNavigationItemType.Profile
            else -> BottomNavigationItemType.Home
        }

    LaunchedEffect(key1 = true) {
        viewModel.loadWorkspace()
    }

    LaunchedEffect(key1 = state.notJoinWorkspace) {
        while (state.notJoinWorkspace) {
            delay(LOAD_WORKSPACE_DELAY)
            viewModel.loadWorkspace()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (nowRoute in listOf(HOME_ROUTE, CHAT_ROUTE, ROOM_ROUTE, NOTIFICATION_ROUTE, PROFILE_ROUTE)) {
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
                workspace = state.workspace,
                notJoinWorkspace = state.notJoinWorkspace,
                navigateToChatSeugi = {
                    navHostController.navigateToChatSeugi()
                },
                navigateToJoinWorkspace = {
                    navHostController.navigateToSelectingJob()
                },
                navigateToTimetable = navHostController::navigateToTimetable,
                navigateToWorkspaceDetail = { id ->
                    navHostController.navigateToWorkspaceDetail()
                    viewModel.loadLocalWorkspace()
                },
                navigateToWorkspaceCreate = {
                    navHostController.navigateToWorkspaceCreate()
                },
                navigateToTask = {
                    navHostController.navigateToAssignment()
                },
                navigateToMeal = navHostController::navigateToMeal,
            )

            chatScreen(
                userId = state.userId,
                workspaceId = state.workspace.workspaceId,
                navigateToChatDetail = { chatId ->
                    navHostController.navigateToChatDetail(
                        workspaceId = state.profile.workspaceId,
                        chatRoomId = chatId,
                        isPersonal = true,
                    )
                },
                navigateToCreateRoom = { workspaceId, userId ->
                    navHostController.navigateToRoomCreate(
                        workspaceId = workspaceId,
                        userId = userId,
                    )
                },
            )
            chatDetailScreen(
                userId = state.userId,
                navigateToChatDetail = { workspaceId, chatRoomId ->
                    navHostController.navigateToChatDetail(
                        workspaceId = workspaceId,
                        chatRoomId = chatRoomId,
                        isPersonal = true,
                    )
                },
                popBackStack = {
                    navHostController.popBackStack()
                },
            )

            roomScreen(
                workspaceId = state.workspace.workspaceId,
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
                navigateToChatDetail = { chatId, workspaceId, isPersonal ->
                    navHostController.popBackStack()
                    navHostController.navigateToChatDetail(
                        chatRoomId = chatId,
                        workspaceId = workspaceId,
                        isPersonal = isPersonal,
                    )
                },
            )

            profileScreen(
                workspaceId = state.workspace.workspaceId,
                myProfile = state.profile,
                showSnackbar = showSnackbar,
                navigateToSetting = navHostController::navigateToSetting,
                changeProfileData = viewModel::setProfileModel,
            )

            notificationScreen(
                workspaceId = state.workspace.workspaceId,
                userId = state.userId,
                permission = state.profile.permission,
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
                workspaceId = state.workspace.workspaceId,
                popBackStack = navHostController::popBackStack,
            )

            notificationEdit(
                userId = state.userId,
                workspaceId = state.workspace.workspaceId,
                permission = state.profile.permission,
                popBackStack = navHostController::popBackStack,
            )
            workspaceDetailScreen(
                workspace = state.workspace,
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
                    viewModel.loadWorkspace()
                },
                navigateToInviteMember = navHostController::navigateToInviteMember,
                myRole = state.profile.permission,
                navigateToSettingGeneral = navHostController::navigateToWorkspaceSettingGeneral,
                navigateToSettingAlarm = navHostController::navigateToWorkspaceSettingAlarm,
            )
            workspaceMemberScreen(
                showSnackbar = showSnackbar,
                navigateToPersonalChat = { chatRoomId, workspaceId ->
                    navHostController.navigateToChatDetail(
                        chatRoomId = chatRoomId,
                        workspaceId = workspaceId,
                        isPersonal = true,
                    )
                },
                popBackStack = { navHostController.popBackStack() },
            )
            workspaceCreateScreen(
                popBackStack = {
                    navHostController.popBackStack()
                },
            )

            workspaceSettingGeneralScreen(
                popBackStack = {
                    navHostController.popBackStack()
                },
            )

            workspaceSettingAlarmScreen(
                workspaceId = state.workspace.workspaceId,
                popBackStack = navHostController::popBackStack,
            )

            settingScreen(
                profileModel = state.profile,
                navigationToOnboarding = mainToOnboarding,
                popBackStack = navHostController::popBackStack,
                showSnackbar = showSnackbar,
                reloadProfile = viewModel::loadLocalWorkspace,
            )

            timetableScreen(
                popBackStack = navHostController::popBackStack,
                workspaceId = state.workspace.workspaceId,
            )

            inviteMemberScreen(
                popBackStack = navHostController::popBackStack,
                workspaceId = state.profile.workspaceId,
            )

            assignmentScreen(
                popBackStack = navHostController::popBackStack,
                workspaceId = state.workspace.workspaceId,
                profile = state.profile,
                navigateToTaskCreate = navHostController::navigateToTaskCreate,
            )

            taskCreateScreen(
                popBackStack = navHostController::popBackStack,
                workspaceId = state.workspace.workspaceId,
            )

            mealScreen(
                workspaceId = state.workspace.workspaceId,
                popBackStack = navHostController::popBackStack,
            )
        }
    }
}
