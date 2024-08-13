package com.seugi.workspacedetail.feature.workspacedetail

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seugi.designsystem.R
import com.seugi.designsystem.animation.NoInteractionSource
import com.seugi.designsystem.animation.bounceClick
import com.seugi.designsystem.component.ButtonType
import com.seugi.designsystem.component.DividerType
import com.seugi.designsystem.component.SeugiButton
import com.seugi.designsystem.component.SeugiDivider
import com.seugi.designsystem.component.SeugiRoundedCircleImage
import com.seugi.designsystem.component.SeugiTopBar
import com.seugi.designsystem.component.Size
import com.seugi.designsystem.theme.Black
import com.seugi.designsystem.theme.Gray100
import com.seugi.designsystem.theme.Gray400
import com.seugi.designsystem.theme.Gray500
import com.seugi.designsystem.theme.Gray600
import com.seugi.designsystem.theme.SeugiTheme
import com.seugi.designsystem.theme.White
import com.seugi.ui.CollectAsSideEffect
import com.seugi.workspacedetail.feature.workspacedetail.model.WorkspaceDetailSideEffect

data class TestModel(
    val workspaceId: String,
    val workspaceName: String,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkspaceDetailScreen(
    viewModel: WorkspaceDetailViewModel = hiltViewModel(),
    navigateToJoinWorkspace: () -> Unit,
    popBackStack: () -> Unit,
    workspaceId: String,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.loadWorkspace()
        viewModel.changeNowWorkspace(workspaceId)
    }
    viewModel.sideEffect.CollectAsSideEffect {
        when (it) {
            is WorkspaceDetailSideEffect.Error -> {
                Toast.makeText(context, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    SeugiTheme {
        if (showDialog) {
            Dialog(onDismissRequest = { showDialog = false }) {
                Box(
                    modifier = Modifier
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(12.dp),
                        ),
                ) {
                    Column {
                        Column(
                            modifier = Modifier
                                .padding(16.dp),
                        ) {
                            Text(
                                modifier = Modifier.padding(bottom = 4.dp),
                                text = "가입된 학교",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Gray600,
                            )
                            LazyColumn {
                                itemsIndexed(state.myWorkspace) { index, item ->
                                    Row {
                                        Row(
                                            modifier = Modifier
                                                .bounceClick(onClick = {
                                                    viewModel.changeNowWorkspace(
                                                        workspaceId = item?.workspaceId!!,
                                                    )
                                                    showDialog = false
                                                })
                                                .fillMaxWidth()
                                                .height(56.dp)
                                                .background(
                                                    color = Gray100,
                                                    shape = RoundedCornerShape(8.dp),
                                                ),
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            Text(
                                                modifier = Modifier.padding(start = 16.dp),
                                                // null일 수가 없어서 넣었습니다.
                                                text = item?.workspaceName!!,
                                                style = MaterialTheme.typography.titleMedium,
                                            )
                                            Spacer(modifier = Modifier.weight(1f))
                                            Image(
                                                modifier = Modifier
                                                    .padding(end = 20.dp)
                                                    .size(24.dp),
                                                painter = painterResource(id = R.drawable.ic_expand_right_line),
                                                contentDescription = "설정 톱니바퀴",
                                                colorFilter = ColorFilter.tint(Gray500),
                                                contentScale = ContentScale.Crop,
                                            )
                                        }
                                    }
                                    // 마지막 아이템이 아닌 경우에만 Spacer를 추가
                                    if (index < state.myWorkspace.size) {
                                        Log.d("TAG", "아이템 뛰우기: ")
                                        Spacer(modifier = Modifier.height(4.dp))
                                    }
                                }
                            }

                            if (state.waitWorkspace.any { it != null }) {
                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    modifier = Modifier.padding(bottom = 4.dp),
                                    text = "가입 대기 중",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Gray600,
                                )

                                LazyColumn {
                                    itemsIndexed(state.waitWorkspace) { index, item ->
                                        Row {
                                            Row(
                                                modifier = Modifier
                                                    .bounceClick(onClick = {})
                                                    .fillMaxWidth()
                                                    .height(56.dp)
                                                    .background(
                                                        color = Gray100,
                                                        shape = RoundedCornerShape(8.dp),
                                                    ),
                                                verticalAlignment = Alignment.CenterVertically,
                                            ) {
                                                Text(
                                                    modifier = Modifier.padding(start = 16.dp),
                                                    text = item?.workspaceName ?: "",
                                                    style = MaterialTheme.typography.titleMedium,
                                                )
                                                Spacer(modifier = Modifier.weight(1f))
                                                Image(
                                                    modifier = Modifier
                                                        .padding(end = 20.dp)
                                                        .size(24.dp),
                                                    painter = painterResource(id = R.drawable.ic_expand_right_line),
                                                    contentDescription = "설정 톱니바퀴",
                                                    colorFilter = ColorFilter.tint(Gray500),
                                                    contentScale = ContentScale.Crop,
                                                )
                                            }
                                        }
                                        // 마지막 아이템이 아닌 경우에만 Spacer를 추가
                                        if (index < state.waitWorkspace.size) {
                                            Spacer(modifier = Modifier.height(4.dp))
                                        }
                                    }
                                }
                            }
                            Row(
                                modifier = Modifier.padding(16.dp),
                                horizontalArrangement = Arrangement.End,
                            ) {
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    modifier = Modifier
                                        .padding(vertical = 9.dp, horizontal = 12.dp)
                                        .clickable(
                                            interactionSource = NoInteractionSource(),
                                            indication = null,
                                        ) {
                                            // TODO 학교 만들기 네비게이션
                                        },
                                    text = "새 학교 만들기",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Gray600,
                                )
                                Text(
                                    modifier = Modifier
                                        .padding(vertical = 9.dp, horizontal = 12.dp)
                                        .clickable(
                                            interactionSource = NoInteractionSource(),
                                            indication = null,
                                        ) {
                                            navigateToJoinWorkspace()
                                        },
                                    text = "기존 학교 가입",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Black,
                                )
                            }
                        }
                    }
                }
            }
        }

        if (state.loading) {
            Dialog(
                onDismissRequest = { viewModel.setLoading(false) },
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(100.dp)
                        .background(White, shape = RoundedCornerShape(8.dp)),
                ) {
                    CircularProgressIndicator()
                }
            }
        }
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                SeugiTopBar(
                    title = {
                        Text(
                            text = "내 학교",
                            style = MaterialTheme.typography.titleLarge,
                        )
                    },
                    onNavigationIconClick = {
                        popBackStack()
                    },
                    backIconCheck = true,
                )
            },
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
            ) {
                Row(
                    modifier = Modifier
                        .padding(top = 12.dp, bottom = 24.dp)
                        .fillMaxWidth()
                        .height(60.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    SeugiRoundedCircleImage(
                        size = Size.ExtraSmall,
                        image = state.workspaceImage,
                        onClick = {},
                        modifier = Modifier.padding(start = 20.dp),
                    )
                    Column(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 20.dp)
                            .background(Color.White)
                            .fillMaxSize(),
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(start = 4.dp),
                            text = state.nowWorkspace.first,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        SeugiButton(
                            onClick = { showDialog = true },
                            type = ButtonType.Gray,
                            text = "학교 전환",
                        )
                    }
                }
                SeugiDivider(
                    type = DividerType.WIDTH,
                    size = 8.dp,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .size(28.dp),
                        painter = painterResource(id = R.drawable.ic_setting_fill),
                        contentDescription = "설정 톱니바퀴",
                        colorFilter = ColorFilter.tint(Color.Black),
                        contentScale = ContentScale.Crop,
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "일반",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(start = 20.dp),
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .size(24.dp),
                        painter = painterResource(id = R.drawable.ic_expand_right_line),
                        contentDescription = "설정 톱니바퀴",
                        colorFilter = ColorFilter.tint(Gray400),
                        contentScale = ContentScale.Crop,
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "알림 설정",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(start = 20.dp),
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .size(24.dp),
                        painter = painterResource(id = R.drawable.ic_expand_right_line),
                        contentDescription = "설정 톱니바퀴",
                        colorFilter = ColorFilter.tint(Gray400),
                        contentScale = ContentScale.Crop,
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .size(28.dp),
                        painter = painterResource(id = R.drawable.ic_person_fill),
                        contentDescription = "설정 톱니바퀴",
                        colorFilter = ColorFilter.tint(Color.Black),
                        contentScale = ContentScale.Crop,
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "멤버",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(start = 20.dp),
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .size(24.dp),
                        painter = painterResource(id = R.drawable.ic_expand_right_line),
                        contentDescription = "설정 톱니바퀴",
                        colorFilter = ColorFilter.tint(Gray400),
                        contentScale = ContentScale.Crop,
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "멤버 초대",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(start = 20.dp),
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .size(24.dp),
                        painter = painterResource(id = R.drawable.ic_expand_right_line),
                        contentDescription = "설정 톱니바퀴",
                        colorFilter = ColorFilter.tint(Gray400),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        }
    }
}
