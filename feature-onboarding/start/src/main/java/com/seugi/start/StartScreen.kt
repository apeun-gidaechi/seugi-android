package com.seugi.start

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.component.ButtonType
import com.seugi.designsystem.component.GradientPrimary
import com.seugi.designsystem.component.SeugiFullWidthButton
import com.seugi.designsystem.component.SeugiOAuthButton
import com.seugi.designsystem.theme.SeugiTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun StartScreen(navigateToEmailSignIn: () -> Unit, navigateToOAuthSignIn: () -> Unit) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var visibleCloud1 by remember { mutableStateOf(false) }
    var visibleText by remember { mutableStateOf(false) }
    var visibleCloud2 by remember { mutableStateOf(false) }
    var visibleButton by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visibleCloud1 = true
        delay(500)
        visibleText = true
        visibleCloud2 = true
        delay(500)
        visibleButton = true
    }

    SeugiTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = GradientPrimary,
                ),
        ) {
            Spacer(modifier = Modifier.height(70.dp))

            // 구름1 애니메이션
            AnimatedVisibility(
                visible = visibleCloud1,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_cloud1),
                        contentDescription = "",
                    )
                }
            }


            // 텍스트 애니메이션
            AnimatedVisibility(
                visible = visibleText,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn()
            ) {
                Column(
                    modifier = Modifier.padding(start = 24.dp),
                ) {
                    Text(text = "스기", style = SeugiTheme.typography.display1, color = SeugiTheme.colors.white)
                    Text(
                        text = "학생, 선생님 모두 함께하는\n스마트 스쿨 플랫폼",
                        style = SeugiTheme.typography.subtitle2,
                        color = SeugiTheme.colors.white,
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 구름2 애니메이션
            AnimatedVisibility(
                visible = visibleCloud2,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_cloud2),
                        contentDescription = "",
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 버튼 애니메이션
            AnimatedVisibility(
                visible = visibleButton,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 20.dp),
                    verticalArrangement = Arrangement.Bottom,
                ) {
                    SeugiFullWidthButton(
                        onClick = { showBottomSheet = true },
                        type = ButtonType.Transparent,
                        text = "시작하기",
                        modifier = Modifier
                            .padding(horizontal = 20.dp),
                    )
                }
            }

            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState,
                    containerColor = SeugiTheme.colors.white,
                    dragHandle = null,
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp),
                    ) {
                        Column {
                            SeugiFullWidthButton(
                                onClick = {
                                    navigateToEmailSignIn()

                                    showBottomSheet = false
                                },
                                type = ButtonType.Black,
                                text = "이메일로 계속하기",
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            // TODO: google icon
                            SeugiOAuthButton(
                                image = R.drawable.ic_google,
                                text = "Google로 계속하기",
                                onClick = {
                                    navigateToOAuthSignIn()
                                    showBottomSheet = false
                                },
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            SeugiOAuthButton(
                                image = R.drawable.ic_apple,
                                text = "Apple로로 계속하기",
                                onClick = {
                                    navigateToOAuthSignIn()
                                    showBottomSheet = false
                                },
                            )
                        }
                        Spacer(modifier = Modifier.height(50.dp))
                    }
                }
            }
        }
    }
}
