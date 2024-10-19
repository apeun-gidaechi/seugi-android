package com.seugi.workspacedetail.feature.invitemember

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.hilt.navigation.compose.hiltViewModel
import com.seugi.data.core.model.WorkspacePermissionModel
import com.seugi.designsystem.animation.bounceClick
import com.seugi.designsystem.component.ButtonType
import com.seugi.designsystem.component.SeugiButton
import com.seugi.designsystem.component.SeugiMemberList
import com.seugi.designsystem.component.SeugiSegmentedButton
import com.seugi.designsystem.component.SeugiSegmentedButtonLayout
import com.seugi.designsystem.component.SeugiTopBar
import com.seugi.designsystem.theme.SeugiTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import androidx.compose.foundation.lazy.items


@Composable
fun InviteMemberScreen(
    popBackStack: () -> Unit,
    viewModel: InviteMemberViewModel = hiltViewModel()
) {

    val tabItems: ImmutableList<String> = persistentListOf("선생님", "학생")
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var checked by remember { mutableStateOf(true) }
    val state = viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.getWaitMembers(
            workspaceId = "669e339593e10f4f59f8c583",
            role = WorkspacePermissionModel.STUDENT.name
        )
        viewModel.getWaitMembers(
            workspaceId = "669e339593e10f4f59f8c583",
            role = WorkspacePermissionModel.TEACHER.name
        )
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(SeugiTheme.colors.white),
        topBar = {
            SeugiTopBar(
                title = {
                    Text(text = "멤버 초대", style = SeugiTheme.typography.subtitle1)
                },
                onNavigationIconClick = {
                    popBackStack()
                }
            )
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp)
                        .padding(horizontal = 20.dp)
                ) {
                    Text(
                        text = "학교코드로 멤버를 초대할 수 있어요",
                        style = SeugiTheme.typography.subtitle2,
                        modifier = Modifier
                            .padding(bottom = 12.dp, start = 4.dp)
                    )
                    SelectMemberCard(
                        text = "학생코드 확인",
                        onClick = {}
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                        .padding(horizontal = 20.dp)
                ) {
                    Text(
                        text = "5명으로부터 가입 요청이 왔어요",
                        style = SeugiTheme.typography.subtitle2,
                        modifier = Modifier
                            .padding(start = 4.dp)
                    )
                    BoxWithConstraints {
                        val itemWidth = maxWidth / tabItems.size
                        SeugiSegmentedButtonLayout(
                            containerColor = SeugiTheme.colors.gray100,
                            shape = RoundedCornerShape(12.dp),
                            indicatorShape = RoundedCornerShape(8.dp),
                            selectedIndex = selectedTabIndex,
                        ) {
                            tabItems.fastForEachIndexed { index, text ->
                                SeugiSegmentedButton(
                                    modifier = Modifier
                                        .width(itemWidth),
                                    text = text,
                                    selected = index == selectedTabIndex,
                                ) {
                                    selectedTabIndex = index
                                }
                            }
                        }
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(top = 12.dp)
                ) {
                    val members = if (selectedTabIndex == 0) state.value.teacher else state.value.student
                    Log.d("TAG", "$members: ")
                    items(items = members, key = {it.id}) { member ->
                        SeugiMemberList(
                            modifier = Modifier.padding(horizontal = 4.dp),
                            userName = member.name,
                            userProfile = member.memberProfile,
                            checked = member.checked,
                            onCheckedChangeListener = {
                                viewModel.updateChecked(
                                    role = selectedTabIndex,
                                    memberId = member.id
                                )
                            },
                        )
                    }
                    item {
                        Spacer(Modifier.height(80.dp))
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                MaterialTheme.colorScheme.surface.copy(alpha = 0f),
                                MaterialTheme.colorScheme.surface,
                            ),
                        ),
                    ),
            )
            Row(
                modifier = Modifier
                    .navigationBarsPadding()
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(vertical = 16.dp)
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SeugiButton(
                    modifier = Modifier
                        .weight(1f)
                        .height(45.dp),
                    onClick = {},
                    type = ButtonType.Red,
                    text = "거절"
                )
                SeugiButton(
                    modifier = Modifier
                        .weight(2f)
                        .height(45.dp),
                    onClick = {},
                    type = ButtonType.Primary,
                    text = "수락"
                )
            }
        }
    }
}


@Composable
internal fun SelectMemberCard(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .background(
                color = SeugiTheme.colors.gray100,
                shape = RoundedCornerShape(12.dp)
            )
            .bounceClick(
                onClick = onClick
            )
    ) {
        Text(
            text = text,
            style = SeugiTheme.typography.body2,
            color = SeugiTheme.colors.gray600,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .padding(horizontal = 12.dp)
        )
    }
}