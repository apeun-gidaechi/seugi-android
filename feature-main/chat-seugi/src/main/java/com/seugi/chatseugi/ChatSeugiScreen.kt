package com.seugi.chatseugi

import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seugi.chatseugi.model.ChatData
import com.seugi.common.utiles.toAmShortString
import com.seugi.designsystem.R
import com.seugi.designsystem.animation.AlphaIndication
import com.seugi.designsystem.animation.bounceClick
import com.seugi.designsystem.component.GradientPrimary
import com.seugi.designsystem.component.LoadingDotsIndicator
import com.seugi.designsystem.component.SeugiTopBar
import com.seugi.designsystem.component.chat.CHAT_SHAPE
import com.seugi.designsystem.component.chat.ChatItemType
import com.seugi.designsystem.component.chat.SeugiChatItem
import com.seugi.designsystem.component.modifier.DropShadowType
import com.seugi.designsystem.component.modifier.dropShadow
import com.seugi.designsystem.component.textfield.SeugiChatTextField
import com.seugi.designsystem.theme.SeugiTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
internal fun ChatSeugiScreen(
    viewModel: ChatSeugiViewModel = hiltViewModel(),
    workspace: String = "664bdd0b9dfce726abd30462",
    isPersonal: Boolean = false,
    chatRoomId: String = "665d9ec15e65717b19a62701",
    popBackStack: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scrollState = rememberLazyListState()

    var text by remember { mutableStateOf("") }
    val keyboardState by rememberKeyboardOpen()

    val density = LocalDensity.current

    LaunchedEffect(key1 = keyboardState) {
        if (keyboardState.isOpen) {
            scrollState.animateScrollBy(with(density) { -keyboardState.height.toPx() })
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(SeugiTheme.colors.primary050),
        topBar = {
            SeugiTopBar(
                modifier = Modifier.fillMaxWidth(),
                title = {
                    Text(
                        text = "캣스기",
                        style = SeugiTheme.typography.subtitle1,
                        color = SeugiTheme.colors.black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                actions = {},
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
                        if (state.isLoading) return@SeugiChatTextField
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
                .background(SeugiTheme.colors.primary050),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(SeugiTheme.colors.primary050),
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SeugiTheme.colors.primary050),
                    contentPadding = PaddingValues(
                        horizontal = 8.dp,
                    ),
                    state = scrollState,
                    reverseLayout = true,
                ) {
                    item {
                        if (state.isLoading) {
                            SeugiChatItemAiLoading(
                                isFirst = true,
                                isLast = true,
                                createdAt = "",
                            )
                        }
                    }
                    items(state.chatMessage) { data ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                        ) {
                            when (data) {
                                is ChatData.AI -> {
                                    SeugiChatItem(
                                        type = ChatItemType.Ai(
                                            isFirst = data.isFirst,
                                            isLast = data.isLast,
                                            message = data.message,
                                            createdAt = data.timestamp.toAmShortString(),
                                            count = null,
                                        ),
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
                                            count = null,
                                        ),
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
                visible = text.isNotEmpty() || state.chatMessage.size == 0,
                enter = fadeIn(TweenSpec(200)),
                exit = fadeOut(TweenSpec(200)),
            ) {
                Row {
                    ChatSeugiExampleText(
                        text = "오늘 급식 뭐야?",
                        onClick = {
                            if (state.isLoading) {
                                text = "오늘 급식 뭐야?"
                            } else {
                                text = ""
                                viewModel.sendMessage("오늘 급식 뭐야?")
                            }
                        },
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    ChatSeugiExampleText(
                        text = "날씨 어떄?",
                        onClick = {
                            if (state.isLoading) {
                                text = "오늘 날씨 어떄?"
                            } else {
                                text = ""
                                viewModel.sendMessage("오늘 날씨 어떄?")
                            }
                        },
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
internal fun ChatSeugiExampleText(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .bounceClick(onClick = onClick),
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = SeugiTheme.colors.primary100,
                    shape = RoundedCornerShape(12.dp),
                ),
        ) {
            Text(
                modifier = Modifier.padding(
                    vertical = 8.dp,
                    horizontal = 12.dp,
                ),
                text = text,
                color = SeugiTheme.colors.gray700,
                style = SeugiTheme.typography.body1,
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SeugiChatItemAiLoading(modifier: Modifier = Modifier, isFirst: Boolean, isLast: Boolean, createdAt: String) {
    val chatShape = RoundedCornerShape(
        topStart = 0.dp,
        topEnd = CHAT_SHAPE,
        bottomStart = CHAT_SHAPE,
        bottomEnd = CHAT_SHAPE,
    )
    Row(
        modifier = modifier,
    ) {
        if (isFirst) {
            Image(
                modifier = Modifier.size(32.dp),
                painter = painterResource(id = R.drawable.ic_appicon_round),
                contentDescription = "앱 둥근 로고",
            )
        } else {
            Spacer(modifier = Modifier.width(32.dp))
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            if (isFirst) {
                Text(
                    text = "캣스기",
                    style = SeugiTheme.typography.body1,
                    color = SeugiTheme.colors.gray600,
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
            Row(
                verticalAlignment = Alignment.Bottom,
            ) {
                Box(
                    modifier = Modifier
                        .weight(
                            weight = 1f,
                            fill = false,
                        )
                        .dropShadow(
                            type = DropShadowType.EvBlack1,
                        )
                        .background(
                            color = SeugiTheme.colors.white,
                            shape = chatShape,
                        )
                        .border(
                            width = (1.5).dp,
                            brush = GradientPrimary,
                            shape = chatShape,
                        )
                        .clip(chatShape)
                        .combinedClickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = AlphaIndication,
                            onClick = {},
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Box(
                        modifier = Modifier.padding(12.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        LoadingDotsIndicator()
                    }
                }
                if (isLast) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(
                        verticalArrangement = Arrangement.Bottom,
                    ) {
                        Text(
                            text = createdAt,
                            color = SeugiTheme.colors.gray600,
                            style = SeugiTheme.typography.caption2,
                        )
                    }
                }
            }
        }
    }
}
