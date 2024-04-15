package com.apeun.gidaechi.roomcreate

import android.util.Log
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apeun.gidaechi.designsystem.R
import com.apeun.gidaechi.designsystem.component.SeugiIconButton
import com.apeun.gidaechi.designsystem.component.SeugiMemberList
import com.apeun.gidaechi.designsystem.component.SeugiTopBar
import com.apeun.gidaechi.designsystem.theme.Black
import com.apeun.gidaechi.designsystem.theme.Gray100
import com.apeun.gidaechi.designsystem.theme.Gray300
import com.apeun.gidaechi.designsystem.theme.Gray600

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
internal fun RoomCreateScreen(
    viewModel: RoomCreateViewModel = hiltViewModel(),
    popBackStack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        viewModel.loadUser()
    }

    LaunchedEffect(key1 = state) {
        Log.d("TAG", "RoomCreateScreen: ${state.checkedMemberState}")
    }

    Scaffold(
        topBar = {
            SeugiTopBar(
                title = {
                    Text(
                        text = "멤버 선택",
                        style = MaterialTheme.typography.titleLarge,
                        color = Black
                    )
                },
                actions = {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .clickable {

                            }
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(
                                    horizontal = 12.dp,
                                    vertical = 9.dp
                                ),
                            text = "완료",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Black
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                },
                backIconCheck = true,
                onNavigationIconClick = popBackStack
            )
        }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .padding(paddingValue)
        ) {
            LazyColumn {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(
                                min = 44.dp
                            )
                            .padding(horizontal = 20.dp)
                            .border(
                                width = 1.dp,
                                color = Gray300,
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        FlowRow(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(4.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
                        ) {
                            state.checkedMemberState.forEach {
                                SelectMemberCard(
                                    name = it.name,
                                    onClick = {
                                        viewModel.updateChecked(it.id)
                                    }
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                            }
                        }
                    }
                }
                items(state.userItem) { item ->
                    SeugiMemberList(
                        userName = item.name,
                        userProfile = item.memberProfile,
                        checked = item.checked,
                        onCheckedChangeListener = {
                            viewModel.updateChecked(item.id)
                        }
                    )
                }
            }
        }
    }
}

@Composable
internal fun SelectMemberCard(
    name: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.background(
            color = Gray100,
            shape = RoundedCornerShape(12.dp)
        )
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = 12.dp,
                    vertical = 9.dp
                )
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium,
                color = Gray600,
            )
            Spacer(modifier = Modifier.width(4.dp))
            SeugiIconButton(
                resId = R.drawable.ic_close_line,
                size = 16.dp,
                onClick = onClick
            )
        }
    }
}