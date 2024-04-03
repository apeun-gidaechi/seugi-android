package com.apeun.gidaechi.designsystem_preview.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import app.rive.runtime.kotlin.core.Rive
import com.apeun.gidaechi.designsystem.animation.NoInteractionSource
import com.apeun.gidaechi.designsystem.component.ButtonType
import com.apeun.gidaechi.designsystem.component.SeugiButton
import com.apeun.gidaechi.designsystem.component.SeugiFullWidthButton
import com.apeun.gidaechi.designsystem.theme.SeugiTheme
import com.apeun.gidaechi.designsystem.theme.White
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Button() {
    val context = LocalContext.current
    Rive.init(context)
    SeugiTheme {
        var testState by remember { mutableStateOf(true) }
        var loadingState by remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()
        val onClick: () -> Unit = {
            coroutineScope.launch {
                testState = false
                loadingState = true
                delay(2000)
                loadingState = false
                testState = true
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .background(White)
                .verticalScroll(rememberScrollState()),
        ) {
            SeugiFullWidthButton(
                onClick = onClick,
                enabled = testState,
                type = ButtonType.Primary,
                text = "시작하기",
                isLoading = loadingState,
                interactionSource = NoInteractionSource(),
            )
            Spacer(modifier = Modifier.height(10.dp))
            SeugiFullWidthButton(
                onClick = onClick,
                enabled = testState,
                type = ButtonType.Black,
                text = "시작하기",
                isLoading = loadingState,
            )
            Spacer(modifier = Modifier.height(10.dp))
            SeugiFullWidthButton(
                onClick = onClick,
                enabled = testState,
                type = ButtonType.Red,
                text = "시작하기",
                isLoading = loadingState,
            )
            Spacer(modifier = Modifier.height(10.dp))
            SeugiFullWidthButton(
                onClick = onClick,
                enabled = testState,
                type = ButtonType.Transparent,
                text = "시작하기",
                isLoading = loadingState,
            )
            Spacer(modifier = Modifier.height(10.dp))
            SeugiFullWidthButton(
                onClick = onClick,
                enabled = testState,
                type = ButtonType.Shadow,
                text = "시작하기",
                isLoading = loadingState,
            )
            Spacer(modifier = Modifier.height(10.dp))
            SeugiFullWidthButton(
                onClick = onClick,
                enabled = testState,
                type = ButtonType.Gray,
                text = "시작하기",
                isLoading = loadingState,
            )
            Spacer(modifier = Modifier.height(20.dp))
            SeugiButton(
                onClick = onClick,
                enabled = testState,
                type = ButtonType.Primary,
                text = "시작하기",
                isLoading = loadingState,
                interactionSource = NoInteractionSource(),
            )
            Spacer(modifier = Modifier.height(10.dp))
            SeugiButton(
                onClick = onClick,
                enabled = testState,
                type = ButtonType.Black,
                text = "시작하기",
                isLoading = loadingState,
            )
            Spacer(modifier = Modifier.height(10.dp))
            SeugiButton(
                onClick = onClick,
                enabled = testState,
                type = ButtonType.Red,
                text = "시작하기",
                isLoading = loadingState,
            )
            Spacer(modifier = Modifier.height(10.dp))
            SeugiButton(
                onClick = onClick,
                enabled = testState,
                type = ButtonType.Transparent,
                text = "시작하기",
                isLoading = loadingState,
            )
            Spacer(modifier = Modifier.height(10.dp))
            SeugiButton(
                onClick = onClick,
                enabled = testState,
                type = ButtonType.Shadow,
                text = "시작하기",
                isLoading = loadingState,
            )
            Spacer(modifier = Modifier.height(10.dp))
            SeugiButton(
                onClick = onClick,
                enabled = testState,
                type = ButtonType.Gray,
                text = "시작하기",
                isLoading = loadingState,
            )
        }
    }
}