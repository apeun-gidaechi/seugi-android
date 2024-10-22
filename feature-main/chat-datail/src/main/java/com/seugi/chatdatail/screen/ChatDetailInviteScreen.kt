package com.seugi.chatdatail.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.seugi.chatdatail.model.ChatDetailUiState
import com.seugi.data.core.model.ProfileModel
import com.seugi.data.core.model.UserModel
import com.seugi.designsystem.R
import com.seugi.designsystem.component.SeugiIconButton
import com.seugi.designsystem.component.SeugiMemberList
import com.seugi.designsystem.component.SeugiTopBar
import com.seugi.designsystem.component.modifier.verticalScrollbar
import com.seugi.designsystem.theme.SeugiTheme
import com.seugi.ui.component.SeugiMemberListLoading
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun ChatDetailInviteScreen(
    state: ChatDetailUiState,
    users: ImmutableList<UserModel>,
    popBackStack: () -> Unit,
    nextScreen: (ImmutableList<ProfileModel>) -> Unit
) {
    val selectScrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    var checkedMemberState: ImmutableList<ProfileModel> by remember { mutableStateOf(persistentListOf()) }
    var beforeItemSize by remember { mutableStateOf(0) }
    LaunchedEffect(key1 = checkedMemberState) {
        if (beforeItemSize < checkedMemberState.size) {
            coroutineScope.launch {
                delay(50)
                selectScrollState.scrollTo(Int.MAX_VALUE)
            }
        }
        beforeItemSize = checkedMemberState.size
    }

    BackHandler(
        enabled = true,
        onBack = popBackStack
    )

    val roomJoinUserIdList = users.map { it.id }.toImmutableList()

    val roomNotJoinWorkspaceUsers = state.workspaceUsers.filter {
        it.member.id !in roomJoinUserIdList
    }.toImmutableList()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(SeugiTheme.colors.white),
        topBar = {
            SeugiTopBar(
                title = {
                    Text(
                        text = "멤버 선택",
                        style = SeugiTheme.typography.subtitle1,
                        color = SeugiTheme.colors.black,
                    )
                },
                actions = {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .clickable(
                                role = Role.Button,
                                onClick = {
                                    if (checkedMemberState.isEmpty()) {
                                        return@clickable
                                    }
                                    nextScreen(checkedMemberState)
                                },
                            ),
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(
                                    horizontal = 12.dp,
                                    vertical = 9.dp,
                                ),
                            text = "완료",
                            style = SeugiTheme.typography.body2,
                            color = SeugiTheme.colors.black,
                        )
                    }
                },
                onNavigationIconClick = popBackStack,
            )
        },
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SeugiTheme.colors.white)
                .padding(paddingValue),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(
                        min = 44.dp,
                        max = 118.dp,
                    )
                    .padding(horizontal = 20.dp)
                    .border(
                        width = 1.dp,
                        color = SeugiTheme.colors.gray300,
                        shape = RoundedCornerShape(12.dp),
                    ),
            ) {
                FlowRow(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(4.dp)
                        .verticalScroll(selectScrollState)
                        .verticalScrollbar(
                            scrollState = selectScrollState,
                            scrollBarWidth = 4.dp,
                            scrollBarColor = SeugiTheme.colors.gray300,
                            cornerRadius = 0.dp,
                        ),
                    horizontalArrangement = Arrangement.Start,
                    verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
                ) {
                    checkedMemberState.fastForEach {
                        SelectMemberCard(
                            name = it.nameAndNick,
                            onClick = {
                                checkedMemberState = checkedMemberState
                                    .toMutableList()
                                    .apply {
                                        remove(it)
                                    }
                                    .toImmutableList()
                            },
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }
            }
            LazyColumn(
                modifier = Modifier.background(SeugiTheme.colors.white),
            ) {
                if (state.workspaceUsers.isEmpty()) {
                    items(3) {
                        Box(
                            modifier = Modifier.height(72.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            SeugiMemberListLoading()
                        }
                    }
                }

                items(
                    items = roomNotJoinWorkspaceUsers,
                    key = { it.member.id },
                ) { item -> // 필터링 추가
                    Box(modifier = Modifier.height(72.dp)) {
                        SeugiMemberList(
                            modifier = Modifier.align(Alignment.Center),
                            userName = item.nameAndNick,
                            userProfile = item.member.picture.ifEmpty { null },
                            checked = item in checkedMemberState,
                            onCheckedChangeListener = {
                                checkedMemberState = checkedMemberState
                                    .toMutableList()
                                    .apply {
                                        if (it) {
                                            add(item)
                                        } else {
                                            remove(item)
                                        }
                                    }
                                    .toImmutableList()
                            },
                        )
                    }
                }
            }
        }
    }
}

@Composable
internal fun SelectMemberCard(name: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier.background(
            color = SeugiTheme.colors.gray100,
            shape = RoundedCornerShape(12.dp),
        ),
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp),
        ) {
            Text(
                text = name,
                style = SeugiTheme.typography.body2,
                color = SeugiTheme.colors.gray600,
            )
            Spacer(modifier = Modifier.width(4.dp))
            SeugiIconButton(
                resId = R.drawable.ic_close_line,
                size = 16.dp,
                onClick = onClick,
            )
        }
    }
}
