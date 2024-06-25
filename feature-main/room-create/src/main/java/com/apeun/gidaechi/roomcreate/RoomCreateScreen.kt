package com.apeun.gidaechi.roomcreate

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apeun.gidaechi.roomcreate.model.RoomCreateSideEffect
import com.apeun.gidaechi.roomcreate.screen.FirstScreen
import com.apeun.gidaechi.roomcreate.screen.SecondScreen
import com.apeun.gidaechi.ui.CollectAsSideEffect

@Composable
internal fun RoomCreateScreen(
    viewModel: RoomCreateViewModel = hiltViewModel(),
    workspaceId: String,
    popBackStack: () -> Unit,
    onNavigationVisibleChange: (Boolean) -> Unit,
    navigateToChatDetail: (chatId: String, workspaceId: String, isPersonal: Boolean) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var nowScreen by remember { mutableStateOf(1) }

    viewModel.sideEffect.CollectAsSideEffect {
        when (it) {
            is RoomCreateSideEffect.SuccessCreateRoom -> {
                navigateToChatDetail(it.chatRoomUid, workspaceId, it.isPersonal)
            }
        }
    }

    LaunchedEffect(key1 = true) {
        onNavigationVisibleChange(false)
        viewModel.loadUser(workspaceId)
    }

    LifecycleResumeEffect(key1 = Unit) {
        onNavigationVisibleChange(false)
        onPauseOrDispose {
            onNavigationVisibleChange(true)
        }
    }

    AnimatedVisibility(visible = nowScreen == 1) {
        FirstScreen(
            state = state,
            updateChecked = {
                viewModel.updateChecked(it)
            },
            popBackStack = popBackStack,
            nextScreen = {
                if (state.checkedMemberState.size == 0) {
                    return@FirstScreen
                }
                if (state.checkedMemberState.size == 1) {
                    viewModel.createRoom(
                        workspaceId = workspaceId,
                    )
                    return@FirstScreen
                }
                nowScreen = 2
            },
        )
    }

    AnimatedVisibility(visible = nowScreen == 2) {
        val memberCount = state.checkedMemberState.size - 1
        SecondScreen(
            placeholder = "${state.checkedMemberState[0].name} ${if (memberCount > 0) "외 ${memberCount}명" else ""}",
            onNameSuccess = {
                viewModel.createRoom(
                    workspaceId = workspaceId,
                    roomName = it,
                )
            },
            popBackStack = {
                nowScreen = 1
            },
        )
    }
}
