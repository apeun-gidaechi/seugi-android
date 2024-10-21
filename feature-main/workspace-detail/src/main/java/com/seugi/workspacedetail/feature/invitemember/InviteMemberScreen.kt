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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seugi.designsystem.component.SeugiDialog
import com.seugi.designsystem.R
import com.seugi.workspacedetail.feature.invitemember.model.DialogModel

enum class InviteDialogType{
    CODE,
    ADD,
    CANCEL,
    CLOSE
}

@Composable
fun InviteMemberScreen(
    popBackStack: () -> Unit,
    viewModel: InviteMemberViewModel = hiltViewModel()
) {

    val tabItems: ImmutableList<String> = persistentListOf("선생님", "학생")
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val state by viewModel.state.collectAsStateWithLifecycle()
    var dialogType by remember { mutableStateOf(InviteDialogType.CLOSE) }

    var dialogData by remember { mutableStateOf(DialogModel()) }
    val clipboardManager = LocalClipboardManager.current
    val waitMEmber = state.student + state.teacher

    LaunchedEffect(key1 = true) {
        viewModel.getWaitMembers(
            workspaceId = "669e339593e10f4f59f8c583",
            role = WorkspacePermissionModel.STUDENT.name
        )
        viewModel.getWaitMembers(
            workspaceId = "669e339593e10f4f59f8c583",
            role = WorkspacePermissionModel.TEACHER.name
        )
        viewModel.getWorkspaceCode(
            workspaceId = "669e339593e10f4f59f8c583"
        )
    }

    LaunchedEffect(key1 = dialogType) {
        if (dialogType == InviteDialogType.ADD){
            dialogData = DialogModel(
                title = "가입을 수락하시겠습니까?",
                lText = "취소",
                rText = "수락",
                onClick = {
                    viewModel.checkedMember(
                        workspaceId = "669e339593e10f4f59f8c583",
                        teacherIds = state.teacher.filter { it.checked }.map { it.id },
                        studentIds = state.student.filter { it.checked }.map { it.id },
                        feature = "수락"
                    )
                    dialogType = InviteDialogType.CLOSE
                }
            )
        }else if(dialogType == InviteDialogType.CANCEL){
            dialogData = DialogModel(
                title = "가입을 거절하시겠습니까?",
                lText = "취소",
                rText = "거절",
                onClick = {
                    viewModel.checkedMember(
                        workspaceId = "669e339593e10f4f59f8c583",
                        teacherIds = state.teacher.filter { it.checked }.map { it.id },
                        studentIds = state.student.filter { it.checked }.map { it.id },
                        feature = "거절"
                    )
                    dialogType = InviteDialogType.CLOSE
                }
            )
        }else if(dialogType == InviteDialogType.CODE){
            dialogData = DialogModel(
                title = "초대코드는 ${state.workspaceCode} 입니다",
                lText = "닫기",
                rText = "복사",
                onClick = {
                    clipboardManager.setText(AnnotatedString(state.workspaceCode))
                    dialogType = InviteDialogType.CLOSE
                },
                icon = R.drawable.ic_copy_line
            )
        }
    }



    if (dialogType != InviteDialogType.CLOSE){
        // TODO 나중에 dialog 수정하기
        SeugiDialog(
            title = dialogData.title,
            onDismissRequest = {dialogType = InviteDialogType.CLOSE},
            rightText = dialogData.rText,
            leftText = dialogData.lText,
            onRightRequest = dialogData.onClick,
            onLeftRequest = {dialogType = InviteDialogType.CLOSE},
            rButtonColor = if (dialogType == InviteDialogType.CANCEL) SeugiTheme.colors.red200 else SeugiTheme.colors.primary500,
            rTextColor = if (dialogType == InviteDialogType.CANCEL) SeugiTheme.colors.red500 else SeugiTheme.colors.white,
            icon = dialogData.icon
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
                        onClick = {
                            dialogType = InviteDialogType.CODE
                        }
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                        .padding(horizontal = 20.dp)
                ) {
                    Text(
                        text = "${waitMEmber.size}명으로부터 가입 요청이 왔어요",
                        style = SeugiTheme.typography.subtitle2,
                        modifier = Modifier
                            .padding(start = 4.dp)
                    )
                    BoxWithConstraints(
                        modifier = Modifier.padding(top = 12.dp)
                    ) {
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
                    val members = if (selectedTabIndex == 0) state.teacher else state.student
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
                    onClick = {
                        dialogType = InviteDialogType.CANCEL
                    },
                    type = ButtonType.Red,
                    text = "거절",
                    enabled = if (waitMEmber.filter { it.checked }.isEmpty()) false else true
                )
                SeugiButton(
                    modifier = Modifier
                        .weight(2f)
                        .height(45.dp),
                    onClick = {
                        dialogType = InviteDialogType.ADD
                    },
                    type = ButtonType.Primary,
                    text = "수락",
                    enabled = if (waitMEmber.filter { it.checked }.isEmpty()) false else true
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