package com.apeun.gidaechi.chatseugi

import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apeun.gidaechi.chatseugi.model.ChatData
import com.apeun.gidaechi.common.utiles.toAmShortString
import com.apeun.gidaechi.common.utiles.toFullFormatString
import com.apeun.gidaechi.designsystem.R
import com.apeun.gidaechi.designsystem.animation.bounceClick
import com.apeun.gidaechi.designsystem.component.DividerType
import com.apeun.gidaechi.designsystem.component.DragState
import com.apeun.gidaechi.designsystem.component.SeugiDivider
import com.apeun.gidaechi.designsystem.component.SeugiIconButton
import com.apeun.gidaechi.designsystem.component.SeugiMemberList
import com.apeun.gidaechi.designsystem.component.SeugiRightSideScaffold
import com.apeun.gidaechi.designsystem.component.SeugiTopBar
import com.apeun.gidaechi.designsystem.component.chat.ChatItemType
import com.apeun.gidaechi.designsystem.component.chat.SeugiChatItem
import com.apeun.gidaechi.designsystem.component.modifier.DropShadowType
import com.apeun.gidaechi.designsystem.component.modifier.dropShadow
import com.apeun.gidaechi.designsystem.component.modifier.`if`
import com.apeun.gidaechi.designsystem.component.textfield.SeugiChatTextField
import com.apeun.gidaechi.designsystem.theme.Black
import com.apeun.gidaechi.designsystem.theme.Gray400
import com.apeun.gidaechi.designsystem.theme.Gray500
import com.apeun.gidaechi.designsystem.theme.Gray600
import com.apeun.gidaechi.designsystem.theme.Gray700
import com.apeun.gidaechi.designsystem.theme.Primary050
import com.apeun.gidaechi.designsystem.theme.Primary100
import com.apeun.gidaechi.designsystem.theme.Primary500
import com.apeun.gidaechi.message.model.message.MessageUserModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
internal fun ChatSeugiScreen(
    viewModel: ChatSeugiViewModel = hiltViewModel(),
    workspace: String = "664bdd0b9dfce726abd30462",
    isPersonal: Boolean = false,
    chatRoomId: String = "665d9ec15e65717b19a62701",
    onNavigationVisibleChange: (Boolean) -> Unit,
    popBackStack: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scrollState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()
    var text by remember { mutableStateOf("") }
    var isSearch by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    val keyboardState by rememberKeyboardOpen()

    val density = LocalDensity.current
    var isOpenSidebar by remember { mutableStateOf(false) }


    LifecycleResumeEffect(key1 = Unit) {
        onNavigationVisibleChange(false)
        onPauseOrDispose {
            onNavigationVisibleChange(true)
        }
    }

    LaunchedEffect(key1 = keyboardState) {
        if (keyboardState.isOpen) {
            scrollState.animateScrollBy(with(density) { -keyboardState.height.toPx() })
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Primary050),
        topBar = {
            SeugiTopBar(
                modifier = Modifier.fillMaxWidth(),
                title = {
                    Text(
                        text = "캣스기",
                        style = MaterialTheme.typography.titleLarge,
                        color = Black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                actions = {},
                backIconCheck = true,
                shadow = true,
                onNavigationIconClick = {
                    popBackStack()
                },
            )
        },
        bottomBar = {
            Column {
                SeugiChatTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 8.dp,
                        )
                        .dropShadow(DropShadowType.EvBlack1),
                    value = text,
                    placeholder = "메세지 보내기",
                    onValueChange = {
                        text = it
                    },
                    onSendClick = {
                        viewModel.sendMessage(text)
                        text = ""
                    },
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(Primary050)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Primary050)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Primary050),
                    contentPadding = PaddingValues(
                        horizontal = 8.dp,
                    ),
                    state = scrollState,
                    reverseLayout = true
                ) {
                    items(state.chatMessage) { data ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        ) {
                            when (data) {
                                is ChatData.AI -> {
                                    SeugiChatItem(
                                        type = ChatItemType.Ai(
                                            isFirst = data.isFirst,
                                            isLast = data.isLast,
                                            message = data.message,
                                            createdAt = data.timestamp.toAmShortString(),
                                            count = null
                                        )
                                    )
                                }

                                is ChatData.User -> {
                                    SeugiChatItem(
                                        modifier = Modifier
                                            .align(Alignment.End),
                                        type = ChatItemType.Me(
                                            isLast = data.isLast,
                                            message = data.message,
                                            createdAt = data.timestamp.toAmShortString(),
                                            count = null
                                        )
                                    )
                                }

                                else -> {}
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
            }
            AnimatedVisibility(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 13.dp),
                visible = text.isNotEmpty(),
                enter = fadeIn(TweenSpec(200)),
                exit = fadeOut(TweenSpec(200))
            ) {
                Row {
                    ChatSeugiExampleText(
                        text = "오늘 급식 뭐야?",
                        onClick = {
                            text = ""
                            viewModel.sendMessage("오늘 급식 뭐야?")
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    ChatSeugiExampleText(
                        text = "8월 행사 알려줘",
                        onClick = {
                            text = ""
                            viewModel.sendMessage("8월 행사 알려줘")
                        }
                    )
                }
            }
        }
    }
}

data class ExKeyboardState(
    val isOpen: Boolean = false,
    val height: Dp = 0.dp,
)

internal fun View.isKeyboardOpen(): Pair<Boolean, Int> {
    val rect = Rect()
    getWindowVisibleDisplayFrame(rect)
    val screenHeight = rootView.height
    val keypadHeight = screenHeight - rect.bottom
    return Pair(keypadHeight > screenHeight * 0.15, screenHeight - rect.bottom)
}

@Composable
internal fun rememberKeyboardOpen(): State<ExKeyboardState> {
    val view = LocalView.current
    val density = LocalDensity.current

    fun Pair<Boolean, Int>.toState() = ExKeyboardState(
        isOpen = first,
        height = with(density) { second.toDp() - 48.dp },
    )

    return produceState(initialValue = view.isKeyboardOpen().toState()) {
        val viewTreeObserver = view.viewTreeObserver
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            value = view.isKeyboardOpen().toState()
        }
        viewTreeObserver.addOnGlobalLayoutListener(listener)

        awaitDispose {
            viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }
}


@Composable
internal fun ChatSeugiExampleText(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .bounceClick(onClick = onClick),
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = Primary100,
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            Text(
                modifier = Modifier.padding(
                    vertical = 8.dp,
                    horizontal = 12.dp
                ),
                text = text,
                color = Gray700,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}