package com.apeun.gidaechi.roomcreate

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apeun.gidaechi.roomcreate.screen.FirstScreen

@Composable
internal fun RoomCreateScreen(
    viewModel: RoomCreateViewModel = hiltViewModel(),
    popBackStack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var nowScreen by remember { mutableStateOf(1) }
    LaunchedEffect(key1 = true) {
        viewModel.loadUser()
    }

    AnimatedVisibility(visible = nowScreen == 1) {
        FirstScreen(
            state = state,
            updateChecked = {
                viewModel.updateChecked(it)
            },
            popBackStack = popBackStack,
            nextScreen = {
                nowScreen = 2
            }
        )
    }
}