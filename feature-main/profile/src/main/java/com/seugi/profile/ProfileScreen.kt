package com.seugi.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seugi.designsystem.R.drawable
import com.seugi.designsystem.animation.bounceClick
import com.seugi.designsystem.component.AvatarType
import com.seugi.designsystem.component.ButtonType
import com.seugi.designsystem.component.DividerType
import com.seugi.designsystem.component.SeugiAvatar
import com.seugi.designsystem.component.SeugiDivider
import com.seugi.designsystem.component.SeugiFullWidthButton
import com.seugi.designsystem.component.SeugiTopBar
import com.seugi.designsystem.component.textfield.SeugiTextField
import com.seugi.designsystem.theme.Black
import com.seugi.designsystem.theme.Gray500
import com.seugi.designsystem.theme.White
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel(), workspaceId: String) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    var isShowDialog by remember { mutableStateOf(false) }
    var editTextTarget by remember { mutableStateOf("") }
    var editText by remember { mutableStateOf("") }
    val editTextString by remember { derivedStateOf { getTargetTextToString(editTextTarget) } }
    val modalBottomSheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    val dialogDismissRequest: () -> Unit = {
        coroutineScope.launch {
            isShowDialog = false
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.load(
            workspaceId = workspaceId,
        )
    }

    if (isShowDialog) {
        ModalBottomSheet(
            onDismissRequest = { dialogDismissRequest() },
            sheetState = modalBottomSheetState,
            dragHandle = { BottomSheetDefaults.DragHandle() },
            containerColor = White,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White)
                    .safeGesturesPadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 20.dp,
                        ),
                ) {
                    Text(
                        text = "${editTextString.first} 수정",
                        style = MaterialTheme.typography.titleMedium,
                        color = Black,
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    SeugiTextField(
                        value = editText,
                        onValueChange = {
                            editText = it
                        },
                        placeholder = "${editTextString.first}${editTextString.second} 입력해주세요",
                        onClickDelete = {
                            editText = ""
                        },
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
                SeugiFullWidthButton(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    onClick = {
                        viewModel.updateState(
                            target = editTextTarget,
                            text = editText,
                        )
                        editText = ""
                        editTextTarget = ""
                        dialogDismissRequest()
                    },
                    type = ButtonType.Primary,
                    text = "저장",
                )
                Spacer(modifier = Modifier.imePadding())
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White),
    ) {
        SeugiTopBar(
            title = {
                Text(
                    text = "내 프로필",
                    style = MaterialTheme.typography.titleLarge,
                    color = Black,
                )
            },
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            SeugiAvatar(type = AvatarType.Medium)
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = state.profileInfo.member.name,
                style = MaterialTheme.typography.titleMedium,
                color = Black,
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .size(32.dp),
                painter = painterResource(id = drawable.ic_setting_fill),
                contentDescription = "설정 톱니바퀴",
                colorFilter = ColorFilter.tint(Gray500),
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        SeugiDivider(
            size = 8.dp,
            type = DividerType.WIDTH,
        )

        Spacer(modifier = Modifier.height(8.dp))
        ProfileCard(
            title = "상태메세지",
            content = state.profileInfo.status,
            onClickEdit = {
                editTextTarget = "status"
                isShowDialog = true
            },
        )
        Spacer(modifier = Modifier.height(8.dp))
        SeugiDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            type = DividerType.WIDTH,
        )

        Spacer(modifier = Modifier.height(8.dp))
        ProfileCard(
            title = "직위",
            content = state.profileInfo.spot,
            onClickEdit = {
                editTextTarget = "spot"
                isShowDialog = true
            },
        )
        Spacer(modifier = Modifier.height(8.dp))
        SeugiDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            type = DividerType.WIDTH,
        )

        Spacer(modifier = Modifier.height(8.dp))
        ProfileCard(
            title = "소속",
            content = state.profileInfo.belong,
            onClickEdit = {
                editTextTarget = "belong"
                isShowDialog = true
            },
        )
        Spacer(modifier = Modifier.height(8.dp))
        SeugiDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            type = DividerType.WIDTH,
        )

        Spacer(modifier = Modifier.height(8.dp))
        ProfileCard(
            title = "휴대전화번호",
            content = state.profileInfo.phone,
            onClickEdit = {
                editTextTarget = "phone"
                isShowDialog = true
            },
        )
        Spacer(modifier = Modifier.height(8.dp))
        SeugiDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            type = DividerType.WIDTH,
        )

        Spacer(modifier = Modifier.height(8.dp))
        ProfileCard(
            title = "유선전화번호",
            content = state.profileInfo.wire,
            onClickEdit = {
                editTextTarget = "wire"
                isShowDialog = true
            },
        )
        Spacer(modifier = Modifier.height(8.dp))
        SeugiDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            type = DividerType.WIDTH,
        )
    }
}

@Composable
internal fun ProfileCard(title: String, content: String, onClickEdit: () -> Unit) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = Gray500,
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = drawable.ic_write_line),
                contentDescription = "수정하기 아이콘",
                colorFilter = ColorFilter.tint(Gray500),
            )
            Spacer(modifier = Modifier.width(20.dp))
        }
        Box(
            modifier = Modifier
                .bounceClick(
                    onClick = onClickEdit,
                )
                .fillMaxWidth()
                .height(56.dp),
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 20.dp),
                text = content,
                style = MaterialTheme.typography.titleMedium,
                color = Black,
            )
        }
    }
}

private fun getTargetTextToString(text: String): Pair<String, String> = when (text) {
    "status" -> Pair("상태메세지", "를")
    "spot" -> Pair("직위", "를")
    "belong" -> Pair("소속", "을")
    "phone" -> Pair("휴대전화번호", "를")
    "wire" -> Pair("유선전화번호", "를")
    else -> Pair("", "")
}
