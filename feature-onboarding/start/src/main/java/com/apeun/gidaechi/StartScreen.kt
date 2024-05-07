package com.apeun.gidaechi

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.apeun.gidaechi.designsystem.component.ButtonType
import com.apeun.gidaechi.designsystem.component.SeugiFullWidthButton
import com.apeun.gidaechi.designsystem.component.SeugiOAuthButton
import com.apeun.gidaechi.designsystem.theme.Gradient
import com.apeun.gidaechi.designsystem.theme.SeugiTheme
import com.apeun.gidaechi.start.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun StartScreen(navigateToEmailSignIn: () -> Unit, navigateToOAuthSignIn: () -> Unit) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    SeugiTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(Gradient)),
        ) {
            Spacer(modifier = Modifier.weight(1f))
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
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier.padding(start = 24.dp),
            ) {
                Text(text = "스기", style = MaterialTheme.typography.displayLarge, color = White)
                Text(
                    text = "학생, 선생님 모두 함께하는\n스마트 스쿨 플랫폼",
                    style = MaterialTheme.typography.titleMedium,
                    color = White,
                )
            }
            Spacer(modifier = Modifier.weight(1f))

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
            Spacer(modifier = Modifier.weight(1f))
            SeugiFullWidthButton(
                onClick = { showBottomSheet = true },
                type = ButtonType.Transparent,
                text = "시작하기",
                modifier = Modifier.padding(horizontal = 20.dp),
            )
            Spacer(modifier = Modifier.weight(1f))

            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState,
                    containerColor = White,
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
                                image = R.drawable.ic_menu,
                                text = "Google로 계속하기",
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
