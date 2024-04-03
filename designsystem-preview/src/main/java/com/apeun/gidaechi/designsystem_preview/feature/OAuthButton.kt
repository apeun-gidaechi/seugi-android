package com.apeun.gidaechi.designsystem_preview.feature

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.apeun.gidaechi.designsystem.component.OAuthButton
import com.apeun.gidaechi.designsystem.theme.SeugiTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun OAuthButton() {
    SeugiTheme {
        var testState by remember { mutableStateOf(true) }
        val coroutineScope = rememberCoroutineScope()
        val onClick: () -> Unit = {
            coroutineScope.launch {
                testState = false
                delay(2000)
                testState = true
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OAuthButton(image = com.apeun.gidaechi.designsystem.R.drawable.test_icon, text = "Google로 계속하기", onClick = onClick)
        }
    }
}
