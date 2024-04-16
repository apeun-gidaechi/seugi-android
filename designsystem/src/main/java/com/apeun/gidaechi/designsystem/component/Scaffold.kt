package com.apeun.gidaechi.designsystem.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.apeun.gidaechi.designsystem.animation.NoInteractionSource
import com.apeun.gidaechi.designsystem.theme.Black
import com.apeun.gidaechi.designsystem.theme.White
import kotlin.math.roundToInt

enum class DragState {
    START,
    END,
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SeugiRightSideScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    sideBar: @Composable BoxScope.() -> Unit = {},
    state: AnchoredDraggableState<DragState>,
    startPadding: Dp = 0.dp,
    onSideBarClose: () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    content: @Composable (PaddingValues) -> Unit,
) {
    val sizeModifier = if (state.currentValue == DragState.START || state.targetValue == DragState.START) Modifier.fillMaxSize() else Modifier.size(0.dp)

    Box(
        modifier = modifier,
    ) {
        Scaffold(
            modifier,
            topBar,
            bottomBar,
            snackbarHost,
            floatingActionButton,
            floatingActionButtonPosition,
            containerColor, contentColor,
            contentWindowInsets,
            content,
        )
        Box(
            modifier = Modifier
                .then(sizeModifier)
                .anchoredDraggable(state, orientation = Orientation.Horizontal)
                .background(Black.copy(alpha = 0.3f)),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(startPadding)
                    .clickable(
                        interactionSource = NoInteractionSource(),
                        indication = null,
                        onClick = onSideBarClose,
                    ),
            )
            Box(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            x = state
                                .requireOffset()
                                .roundToInt(),
                            y = 0,
                        )
                    }
                    .fillMaxSize()
                    .padding(
                        start = startPadding,
                    )
                    .background(White),
            ) {
                sideBar()
            }
        }
    }
}
