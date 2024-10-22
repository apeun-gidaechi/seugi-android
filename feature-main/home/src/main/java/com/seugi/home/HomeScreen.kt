package com.seugi.home

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowInsetsController
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seugi.data.workspace.model.WorkspaceModel
import com.seugi.designsystem.R
import com.seugi.designsystem.animation.bounceClick
import com.seugi.designsystem.component.SeugiDialog
import com.seugi.designsystem.component.SeugiTopBar
import com.seugi.designsystem.component.modifier.DropShadowType
import com.seugi.designsystem.component.modifier.dropShadow
import com.seugi.designsystem.component.modifier.`if`
import com.seugi.designsystem.theme.SeugiTheme
import com.seugi.home.card.CatSeugiCard
import com.seugi.home.card.MealCard
import com.seugi.home.card.ScheduleCard
import com.seugi.home.card.SchoolCard
import com.seugi.home.card.TaskCard
import com.seugi.home.card.TimeScheduleCard

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class,
)
@Composable
internal fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    notJoinWorkspace: Boolean,
    workspace: WorkspaceModel,
    navigateToChatSeugi: () -> Unit,
    navigateToJoinWorkspace: () -> Unit,
    navigateToTimetable: () -> Unit,
    navigateToWorkspaceDetail: (String) -> Unit,
    navigateToWorkspaceCreate: () -> Unit,
    navigateToTask: () -> Unit,
) {
    val view = LocalView.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    var isRefreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            if (workspace.workspaceId.isEmpty()) {
                return@rememberPullRefreshState
            }
            viewModel.load(workspace)
        },
    )

    val changeNavColorWhite = SeugiTheme.colors.white
    LifecycleResumeEffect(Unit) {
        onPauseOrDispose {
            if (!view.isInEditMode) {
                val window = (view.context as Activity).window
                changeNavigationColor(window, changeNavColorWhite, false)
            }
        }
    }

    val changeNavColor = SeugiTheme.colors.primary050
    LaunchedEffect(key1 = workspace) {
        if (workspace.workspaceId.isEmpty()) {
            return@LaunchedEffect
        }
        viewModel.load(workspace)
        if (!view.isInEditMode) {
            val window = (view.context as Activity).window
            changeNavigationColor(window, changeNavColor, false)
        }
    }

    LaunchedEffect(key1 = notJoinWorkspace) {
        if (notJoinWorkspace) {
            viewModel.setStateNotJoin()
        }
    }

    if (notJoinWorkspace) {
        SeugiDialog(
            title = "학교 등록하기",
            content = "학교를 등록한 뒤 스기를 사용할 수 있어요",
            leftText = "새 학교 만들기",
            rightText = "기존 학교 가입",
            onLeftRequest = {
                navigateToWorkspaceCreate()
            },
            onRightRequest = {
                navigateToJoinWorkspace()
            },
            onDismissRequest = {},
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState),
    ) {
        LazyColumn(
            modifier = Modifier
                .background(SeugiTheme.colors.primary050)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item {
                SeugiTopBar(
                    title = {
                        Text(
                            text = "홈",
                            style = SeugiTheme.typography.subtitle1,
                            color = SeugiTheme.colors.black,
                        )
                    },
                    containerColors = Color.Transparent,
                )
            }

            item {
                SchoolCard(
                    workspaceId = state.nowWorkspaceId,
                    uiState = state.schoolState,
                    navigateToWorkspaceDetail = navigateToWorkspaceDetail,
                )
            }

            item {
                TimeScheduleCard(
                    uiState = state.timeScheduleState,
                    onClickDetail = navigateToTimetable,
                )
            }

            item {
                MealCard(
                    uiState = state.mealState,
                    onClickDetail = {},
                )
            }

            item {
                CatSeugiCard(
                    uiState = state.catSeugiState,
                    navigateToChatSeugi = navigateToChatSeugi,
                )
            }

            item {
                ScheduleCard(uiState = state.schoolScheduleState)
            }

            item {
                TaskCard(
                    uiState = state.taskState,
                    navigateToTask = navigateToTask
                )
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            refreshing = isRefreshing,
            state = pullRefreshState,
        )
    }
}

private fun changeNavigationColor(window: Window, backgroundColor: Color, isDark: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.statusBarColor = backgroundColor.toArgb()
        window.insetsController?.setSystemBarsAppearance(
            if (isDark) 0 else WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
        )
    } else {
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility =
            if (isDark) 0 else View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}

@Composable
internal fun HomeCard(modifier: Modifier = Modifier, text: String, image: Painter, colorFilter: ColorFilter? = null, content: @Composable () -> Unit) {
    Column(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .animateContentSize()
            .dropShadow(DropShadowType.EvBlack1)
            .background(
                color = SeugiTheme.colors.white,
                shape = RoundedCornerShape(12.dp),
            ),
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.padding(horizontal = 12.dp),
        ) {
            Spacer(modifier = Modifier.width(4.dp))
            Box(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .size(32.dp)
                    .background(
                        color = SeugiTheme.colors.gray100,
                        shape = RoundedCornerShape(8.dp),
                    ),
            ) {
                Image(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center)
                        .`if`(text == "캣스기") {
                            Modifier.background(SeugiTheme.colors.white)
                        },
                    painter = image,
                    contentDescription = "",
                    colorFilter = colorFilter,
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = text,
                style = SeugiTheme.typography.subtitle2,
                color = SeugiTheme.colors.black,
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier.padding(horizontal = 12.dp),
        ) {
            content()
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
internal fun HomeCard(modifier: Modifier = Modifier, text: String, onClickDetail: () -> Unit, image: Painter, colorFilter: ColorFilter? = null, content: @Composable () -> Unit) {
    Column(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .animateContentSize()
            .dropShadow(DropShadowType.EvBlack1)
            .background(
                color = SeugiTheme.colors.white,
                shape = RoundedCornerShape(12.dp),
            ),
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .bounceClick(
                    onClick = onClickDetail,
                    enabled = true,
                ),
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .size(32.dp)
                    .background(
                        color = SeugiTheme.colors.gray100,
                        shape = RoundedCornerShape(8.dp),
                    ),
            ) {
                Image(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center),
                    painter = image,
                    contentDescription = "",
                    colorFilter = colorFilter,
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = text,
                style = SeugiTheme.typography.subtitle2,
                color = SeugiTheme.colors.black,
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterVertically),
                painter = painterResource(id = R.drawable.ic_expand_right_line),
                contentDescription = "상세보기",
                colorFilter = ColorFilter.tint(SeugiTheme.colors.gray500),
            )
            Spacer(modifier = Modifier.width(12.dp))
        }
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier.padding(horizontal = 12.dp),
        ) {
            content()
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
internal fun HomeSubjectCard(modifier: Modifier, index: Int, selectIndex: Int, subject: String) {
    val isNowSelected = index == selectIndex
    Column(
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .align(Alignment.Center),
                text = (index + 1).toString(),
                style = SeugiTheme.typography.body1,
                color = if (isNowSelected) SeugiTheme.colors.primary500 else SeugiTheme.colors.primary300,
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(34.dp),
        ) {
            Text(
                modifier = Modifier
                    .padding(
                        vertical = 8.dp,
                        horizontal = (8.64).dp,
                    )
                    .align(Alignment.Center),
                text = subject,
                style = SeugiTheme.typography.body1,
                color = if (isNowSelected) SeugiTheme.colors.white else if (index > selectIndex) SeugiTheme.colors.primary300 else SeugiTheme.colors.primary200,
            )
        }
    }
}

@Composable
internal fun HomeCalendarCard(modifier: Modifier = Modifier, date: String, content: String, dDay: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = date,
            style = SeugiTheme.typography.body1,
            color = SeugiTheme.colors.primary500,
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            modifier = Modifier.weight(1f),
            text = content,
            style = SeugiTheme.typography.body2,
            color = SeugiTheme.colors.black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = dDay,
            style = SeugiTheme.typography.body2,
            color = SeugiTheme.colors.gray600,
        )
    }
}

@Composable
internal fun HomeNotFoundText(text: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 12.dp),
            text = text,
            style = SeugiTheme.typography.body2,
            color = SeugiTheme.colors.gray600,
        )
    }
}

@Composable
internal fun HomeSchoolSelectCard(text: String, isSelect: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier.bounceClick(
            onClick = onClick,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = SeugiTheme.colors.primary050,
                    shape = RoundedCornerShape(8.dp),
                ),
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
            ) {
                Text(
                    text = text,
                    color = SeugiTheme.colors.black,
                    style = SeugiTheme.typography.subtitle2,
                )
                Spacer(modifier = Modifier.weight(1f))
                if (isSelect) {
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.ic_setting_fill),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(SeugiTheme.colors.gray500),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_expand_right_line),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(SeugiTheme.colors.gray500),
                )
            }
        }
    }
}

@Composable
internal fun HomeErrorCard(modifier: Modifier = Modifier, text: String, imageVector: ImageVector) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Image(
            modifier = Modifier.size(64.dp),
            imageVector = imageVector,
            contentDescription = null,
        )
        Text(
            text = text,
            style = SeugiTheme.typography.subtitle2,
            color = SeugiTheme.colors.black,
        )
    }
}
