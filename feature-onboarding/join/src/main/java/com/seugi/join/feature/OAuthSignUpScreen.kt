package com.seugi.join.feature

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.animation.bounceClick
import com.seugi.designsystem.component.ButtonType
import com.seugi.designsystem.component.SeugiFullWidthButton
import com.seugi.designsystem.component.SeugiTopBar
import com.seugi.designsystem.component.textfield.SeugiTextField
import com.seugi.designsystem.theme.SeugiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun OAuthSignUpScreen(popBackStack: () -> Unit, navigateToEmailSignUp: () -> Unit) {
    var text by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }

    SeugiTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                SeugiTopBar(
                    title = { Text(text = "회원가입", style = SeugiTheme.typography.subtitle1) },
                    onNavigationIconClick = popBackStack,
                )
            },
            containerColor = SeugiTheme.colors.white,
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(top = 6.dp)
                    .padding(horizontal = 20.dp),
            ) {
                Column {
                    Row(
                        modifier = Modifier.padding(start = 4.dp),
                    ) {
                        Text(text = "이름", style = SeugiTheme.typography.subtitle2)
                        Text(
                            text = " *",
                            style = SeugiTheme.typography.subtitle2,
                            color = SeugiTheme.colors.red500,
                        )
                    }
                    SeugiTextField(
                        value = text,
                        onValueChange = { text = it },
                        onClickDelete = { text = "" },
                        placeholder = "이름을 입력해 주세요",
                        modifier = Modifier.padding(vertical = 4.dp),
                    )
                    if (error) {
                        Text(
                            text = "이름을 입력해 주세요",
                            style = SeugiTheme.typography.body1,
                            color = SeugiTheme.colors.red500,
                            modifier = Modifier.padding(top = 4.dp),
                        )
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier.fillMaxHeight(),
                ) {
                    Text(
                        text = "이미 계정이 있으신가요?",
                        style = MaterialTheme.typography.bodySmall,
                        color = SeugiTheme.colors.primary500,
                        modifier = Modifier.bounceClick({
                            navigateToEmailSignUp()
                        }),
                    )
                    SeugiFullWidthButton(
                        onClick = {},
                        type = ButtonType.Primary,
                        text = "계속하기",
                        modifier = Modifier.padding(vertical = 16.dp),
                    )
                }
            }
        }
    }
}
