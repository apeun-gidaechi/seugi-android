package com.seugi.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seugi.data.core.model.ProfileModel
import com.seugi.designsystem.R
import com.seugi.designsystem.animation.bounceClick
import com.seugi.designsystem.component.AvatarType
import com.seugi.designsystem.component.DividerType
import com.seugi.designsystem.component.LoadingDotsIndicator
import com.seugi.designsystem.component.SeugiAvatar
import com.seugi.designsystem.component.SeugiButton
import com.seugi.designsystem.component.SeugiDivider
import com.seugi.designsystem.component.SeugiIconButton
import com.seugi.designsystem.component.SeugiTopBar
import com.seugi.designsystem.theme.SeugiTheme
import com.seugi.setting.model.SettingSideEffect
import com.seugi.ui.CollectAsSideEffect

@Composable
internal fun SettingScreen(
    viewModel: SettingViewModel = hiltViewModel(),
    profileModel: ProfileModel,
    onNavigationVisibleChange: (Boolean) -> Unit,
    navigationToOnboarding: () -> Unit,
    popBackStack: () -> Unit,
    showSnackbar: (text: String) -> Unit
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    viewModel.sideEffect.CollectAsSideEffect {
        when (it) {
            is SettingSideEffect.SuccessLogout -> {
                navigationToOnboarding()
            }
            is SettingSideEffect.SuccessWithdraw -> {
                navigationToOnboarding()
            }
            is SettingSideEffect.FailedWithdraw -> {
                showSnackbar("회원탈퇴에 실패했습니다.")
            }
        }
    }

    LaunchedEffect(true) {
        onNavigationVisibleChange(false)
    }

    LifecycleResumeEffect {
        onNavigationVisibleChange(false)
        onPauseOrDispose {
            onNavigationVisibleChange(true)
        }
    }
    Box {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(SeugiTheme.colors.white),
            topBar = {
                SeugiTopBar(
                    title = {
                        Text(
                            text = "설정",
                            style = SeugiTheme.typography.subtitle1,
                            color = SeugiTheme.colors.black
                        )
                    },
                    onNavigationIconClick = popBackStack
                )
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(SeugiTheme.colors.white),
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(vertical = 8.dp)
                                .size(64.dp)
                                .bounceClick(
                                    onClick = {

                                    }
                                )
                        ) {
                            SeugiAvatar(
                                modifier = Modifier.align(Alignment.Center),
                                type = AvatarType.ExtraLarge,
                                image = profileModel.member.picture.ifEmpty { null }
                            )
                            Image(
                                modifier = Modifier
                                    .size(24.dp)
                                    .align(Alignment.BottomEnd),
                                painter = painterResource(id = R.drawable.ic_add_fill),
                                contentDescription = null
                            )
                        }
                    }
                }

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .bounceClick(
                                    onClick = {

                                    }
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = profileModel.nick,
                                style = SeugiTheme.typography.subtitle2,
                                color = SeugiTheme.colors.black
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Image(
                                modifier = Modifier.size(20.dp),
                                painter = painterResource(id = R.drawable.ic_write_line),
                                contentDescription = "닉네임 수정하기",
                                colorFilter = ColorFilter.tint(SeugiTheme.colors.gray500)
                            )
                        }
                    }
                }

                item {
                    SettingCard(
                        title = "로그아웃",
                        onClickDetail = viewModel::logout
                    )
                }
                item {
                    SettingCard(
                        title = "회원 탈퇴",
                        titleColor = SeugiTheme.colors.red500,
                        onClickDetail = viewModel::withdraw
                    )
                }
                item {
                    SeugiDivider(
                        type = DividerType.WIDTH,
                        size = 8.dp,
                        color = SeugiTheme.colors.gray100
                    )
                }

                item {
                    SettingCard(
                        title = "개인정보 처리 방침",
                        onClickDetail = {}
                    )
                }

                item {
                    SettingCard(
                        title = "서비스 운영 정책",
                        onClickDetail = {}
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(150.dp))
                }
            }
        }

        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(SeugiTheme.colors.black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                LoadingDotsIndicator()
            }
        }
    }
}

@Composable
private fun SettingCard(
    modifier: Modifier = Modifier,
    title: String,
    titleColor: Color = SeugiTheme.colors.black,
    onClickDetail: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .bounceClick(onClickDetail),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = title,
            style = SeugiTheme.typography.subtitle2,
            color = titleColor
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .size(24.dp),
            painter = painterResource(id = R.drawable.ic_expand_right_line),
            contentDescription = null,
            colorFilter = ColorFilter.tint(SeugiTheme.colors.gray400)
        )
        Spacer(modifier = Modifier.width(20.dp))
    }
}