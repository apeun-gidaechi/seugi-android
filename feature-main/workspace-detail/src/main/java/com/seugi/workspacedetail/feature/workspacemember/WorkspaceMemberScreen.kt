package com.seugi.workspacedetail.feature.workspacemember

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
<<<<<<< HEAD
import com.seugi.data.core.model.ProfileModel
=======
>>>>>>> origin/feature/204-create-member-filter
import com.seugi.data.core.model.WorkspacePermissionModel
import com.seugi.data.core.model.isAdmin
import com.seugi.designsystem.R
import com.seugi.designsystem.animation.bounceClick
import com.seugi.designsystem.component.SeugiMemberList
import com.seugi.designsystem.component.SeugiSegmentedButton
import com.seugi.designsystem.component.SeugiSegmentedButtonLayout
import com.seugi.designsystem.component.SeugiSmallDropDown
import com.seugi.designsystem.component.SeugiTopBar
import com.seugi.designsystem.theme.SeugiTheme
import com.seugi.ui.CollectAsSideEffect
import com.seugi.ui.component.OtherProfileBottomSheet
import com.seugi.workspacedetail.feature.workspacemember.model.WorkspaceMemberSideEffect
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkspaceMemberScreen(
    viewModel: WorkspaceMemberViewModel = hiltViewModel(),
    workspaceId: String,
    navigateToPersonalChat: (chatRoomUid: String, workspaceId: String) -> Unit,
    showSnackbar: (text: String) -> Unit,
    popBackStack: () -> Unit,
) {
    var selectedItem by remember { mutableStateOf("전체") }
    var isExpanded by remember { mutableStateOf(false) }
    var selectedMember by remember { mutableStateOf<ProfileModel?>(null) }
    var isShowBottomSheet by remember { mutableStateOf(false) }

    val icon = if (isExpanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        viewModel.getAllMember(workspaceId = workspaceId)
    }
<<<<<<< HEAD

    viewModel.sideEffect.CollectAsSideEffect {
        when (it) {
            is WorkspaceMemberSideEffect.SuccessCreateRoom -> {
                navigateToPersonalChat(it.roomUid, workspaceId)
            }
            is WorkspaceMemberSideEffect.FailedCreateRoom -> {
                showSnackbar("채팅방 이동에 실패했습니다.")
            }
        }
    }

    if (isShowBottomSheet) {
        OtherProfileBottomSheet(
            profile = selectedMember?.member?.picture,
            name = selectedMember?.member?.name ?: "",
            status = selectedMember?.status ?: "",
            spot = selectedMember?.spot ?: "",
            belong = selectedMember?.belong ?: "",
            phone = selectedMember?.phone ?: "",
            wire = selectedMember?.wire ?: "",
            location = selectedMember?.location ?: "",
            onClickChat = {
                viewModel.createChatRoom(
                    workspaceId = workspaceId,
                    targetUserId = selectedMember?.member?.id?.toLong() ?: 0L,
                )
            },
            onDismissRequest = {
                isShowBottomSheet = false
            },
        )
    }

=======
>>>>>>> origin/feature/204-create-member-filter
    val tabItems: ImmutableList<String> = persistentListOf("선생님", "학생")
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(SeugiTheme.colors.white),
        topBar = {
            SeugiTopBar(

                title = {
                    Text(text = "멤버", style = SeugiTheme.typography.subtitle1)
                },
                onNavigationIconClick = {
                    popBackStack()
                },
                actions = {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.ic_search,
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .bounceClick({ Log.d("TAG", "검색: ") })
                            .padding(end = 16.dp)
                            .size(28.dp),
                    )
                },
            )
        },
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .background(SeugiTheme.colors.white),
        ) {
            Spacer(modifier = Modifier.height(6.dp))
            BoxWithConstraints(
                modifier = Modifier
                    .height(48.dp)
                    .padding(horizontal = 20.dp),
            ) {
                val itemWidth = maxWidth / tabItems.size
                SeugiSegmentedButtonLayout(
                    modifier = Modifier
                        .height(48.dp)
                        .fillMaxWidth(),
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
            Spacer(modifier = Modifier.height(16.dp))
            SeugiSmallDropDown(
                item = persistentListOf(),
                onItemSelected = { selectedItem = it },
                isExpanded = isExpanded,
                selectedItem = selectedItem,
                icon = icon,
                onExpandedChanged = { isExpanded = it },
                modifier = Modifier.padding(start = 24.dp),
            )
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(SeugiTheme.colors.white)
                    .padding(horizontal = 4.dp),
            ) {
                val student = state.member.filter { it ->
                    if (selectedTabIndex == 1) {
                        it.permission == WorkspacePermissionModel.STUDENT
                    } else {
                        it.permission != WorkspacePermissionModel.STUDENT
                    }
                }

                items(items = student, key = { it.member.id }) { user ->
                    SeugiMemberList(
                        userName = user.member.name,
                        userProfile = user.member.picture.ifEmpty { null },
<<<<<<< HEAD
                        onClick = {
                            selectedMember = user
                            isShowBottomSheet = true
                        },
=======
                        onClick = {},
>>>>>>> origin/feature/204-create-member-filter
                        isCrown = user.permission.isAdmin(),
                        crownColor = when (user.permission) {
                            WorkspacePermissionModel.ADMIN -> SeugiTheme.colors.orange500
                            WorkspacePermissionModel.MIDDLE_ADMIN -> SeugiTheme.colors.yellow500
                            else -> SeugiTheme.colors.black
                        },
                    )
                }
            }
        }
    }
}
