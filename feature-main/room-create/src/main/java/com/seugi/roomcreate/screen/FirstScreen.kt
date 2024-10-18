package com.seugi.roomcreate.screen

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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.seugi.designsystem.R
import com.seugi.designsystem.component.SeugiIconButton
import com.seugi.designsystem.component.SeugiMemberList
import com.seugi.designsystem.component.SeugiTopBar
import com.seugi.designsystem.component.modifier.verticalScrollbar
import com.seugi.designsystem.theme.SeugiTheme
import com.seugi.roomcreate.model.RoomCreateUiState
import com.seugi.ui.component.SeugiMemberListLoading
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
internal fun FirstScreen(state: RoomCreateUiState, updateChecked: (userId: Int) -> Unit, popBackStack: () -> Unit, nextScreen: () -> Unit) {
    val selectScrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    var beforeItemSize by remember { mutableStateOf(0) }
    LaunchedEffect(key1 = state.checkedMemberState) {
        if (beforeItemSize < state.checkedMemberState.size) {
            coroutineScope.launch {
                delay(50)
                selectScrollState.scrollTo(Int.MAX_VALUE)
            }
        }
        beforeItemSize = state.checkedMemberState.size
    }

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
                                onClick = nextScreen,
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
                    state.checkedMemberState.fastForEach {
                        SelectMemberCard(
                            name = it.name,
                            onClick = {
                                updateChecked(it.id)
                            },
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }
            }
            LazyColumn(
                modifier = Modifier.background(SeugiTheme.colors.white),
            ) {
                if (state.isLoading) {
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
                    items = state.userItem,
                    key = { it.id },
                ) { item -> // 필터링 추가
                    Box(modifier = Modifier.height(72.dp)) {
                        SeugiMemberList(
                            modifier = Modifier.align(Alignment.Center),
                            userName = item.name,
                            userProfile = item.memberProfile?.ifEmpty { null },
                            checked = item.checked,
                            onCheckedChangeListener = {
                                updateChecked(item.id)
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
