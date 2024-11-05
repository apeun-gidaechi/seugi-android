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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
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
import com.seugi.data.core.model.ProfileModel
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
import com.seugi.designsystem.theme.SeugiTheme
import com.seugi.profile.model.ProfileSideEffect
import com.seugi.ui.CollectAsSideEffect
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    workspaceId: String,
    myProfile: ProfileModel,
    showSnackbar: (text: String) -> Unit,
    changeProfileData: (ProfileModel) -> Unit,
    navigateToSetting: () -> Unit,
) {
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

    viewModel.sideEffect.CollectAsSideEffect {
        when (it) {
            is ProfileSideEffect.FailedChange -> {
                showSnackbar(it.throwable.message ?: "")
            }
        }
    }

    if (isShowDialog) {
        ModalBottomSheet(
            onDismissRequest = { dialogDismissRequest() },
            sheetState = modalBottomSheetState,
            dragHandle = { BottomSheetDefaults.DragHandle() },
            containerColor = SeugiTheme.colors.white,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SeugiTheme.colors.white)
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
                        style = SeugiTheme.typography.subtitle2,
                        color = SeugiTheme.colors.black,
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
                        val changeData = with(myProfile) {
                            myProfile.copy(
                                status = if (editTextTarget == "status") editText else status,
                                member = member,
                                workspaceId = workspaceId,
                                nick = if (editTextTarget == "nick") editText else nick,
                                spot = if (editTextTarget == "spot") editText else spot,
                                belong = if (editTextTarget == "belong") editText else belong,
                                phone = if (editTextTarget == "phone") editText else phone,
                                wire = if (editTextTarget == "wire") editText else wire,
                                location = if (editTextTarget == "location") editText else location,
                            )
                        }
                        viewModel.updateState(changeData)

                        changeProfileData(changeData)

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
            .background(SeugiTheme.colors.white),
    ) {
        SeugiTopBar(
            title = {
                Text(
                    text = "내 프로필",
                    style = SeugiTheme.typography.subtitle1,
                    color = SeugiTheme.colors.black,
                )
            },
        )
        LazyColumn {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(modifier = Modifier.width(16.dp))
                    SeugiAvatar(
                        type = AvatarType.Medium,
                        image = myProfile.member.picture.ifEmpty { null },
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "${myProfile.member.name} ${if (myProfile.nick.isEmpty()) "" else "(${myProfile.nick})"}",
                        style = SeugiTheme.typography.subtitle2,
                        color = SeugiTheme.colors.black,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .size(32.dp)
                            .bounceClick(navigateToSetting),
                        painter = painterResource(id = drawable.ic_setting_fill),
                        contentDescription = "설정 톱니바퀴",
                        colorFilter = ColorFilter.tint(SeugiTheme.colors.gray500),
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
                Spacer(modifier = Modifier.height(16.dp))
                SeugiDivider(
                    size = 8.dp,
                    type = DividerType.WIDTH,
                )
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
                ProfileCard(
                    title = "상태메세지",
                    content = myProfile.status,
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
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
                ProfileCard(
                    title = "닉네임",
                    content = myProfile.nick,
                    onClickEdit = {
                        editTextTarget = "nick"
                        isShowDialog = true
                    },
                )
                Spacer(modifier = Modifier.height(8.dp))
                SeugiDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    type = DividerType.WIDTH,
                )
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
                ProfileCard(
                    title = "직위",
                    content = myProfile.spot,
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
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
                ProfileCard(
                    title = "소속",
                    content = myProfile.belong,
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
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                ProfileCard(
                    title = "휴대전화번호",
                    content = myProfile.phone,
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
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
                ProfileCard(
                    title = "유선전화번호",
                    content = myProfile.wire,
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
            item {
                Spacer(modifier = Modifier.height(8.dp))
                ProfileCard(
                    title = "근무 위치",
                    content = myProfile.location,
                    onClickEdit = {
                        editTextTarget = "location"
                        isShowDialog = true
                    },
                )
                Spacer(modifier = Modifier.height(8.dp))
                SeugiDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    type = DividerType.WIDTH,
                )
            }
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
internal fun ProfileCard(title: String, content: String, onClickEdit: () -> Unit) {
    Column(
        modifier = Modifier.bounceClick(onClick = onClickEdit)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = title,
                style = SeugiTheme.typography.body1,
                color = SeugiTheme.colors.gray500,
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = drawable.ic_write_line),
                contentDescription = "수정하기 아이콘",
                colorFilter = ColorFilter.tint(SeugiTheme.colors.gray500),
            )
            Spacer(modifier = Modifier.width(20.dp))
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 20.dp),
                text = content,
                style = SeugiTheme.typography.subtitle2,
                color = SeugiTheme.colors.black,
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
    "nick" -> Pair("닉네임", "을")
    "location" -> Pair("근무 위치", "를")
    else -> Pair("", "")
}
