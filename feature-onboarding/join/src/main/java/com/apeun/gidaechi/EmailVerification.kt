package com.apeun.gidaechi

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.apeun.gidaechi.designsystem.component.ButtonType
import com.apeun.gidaechi.designsystem.component.SeugiButton
import com.apeun.gidaechi.designsystem.component.SeugiDialog
import com.apeun.gidaechi.designsystem.component.SeugiFullWidthButton
import com.apeun.gidaechi.designsystem.component.SeugiTopBar
import com.apeun.gidaechi.designsystem.component.textfield.SeugiCodeTextField
import com.apeun.gidaechi.designsystem.theme.Gray600
import com.apeun.gidaechi.designsystem.theme.Red500
import com.apeun.gidaechi.designsystem.theme.SeugiTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailVerificationScreen(navigateToSchoolCode: () -> Unit, popBackStack: () -> Unit) {
    var timeLeft by remember { mutableStateOf(0) }

    val minutes = timeLeft / 60
    val seconds = timeLeft % 60
    val formattedTime = "%d분 %02d초 남음".format(minutes, seconds)

    var verificationClick by remember {
        mutableStateOf(false)
    }

    var dialogState by remember {
        mutableStateOf(false)
    }
    SeugiTheme {
        LaunchedEffect(key1 = timeLeft) {
            while (timeLeft > 0) {
                delay(1000L)
                timeLeft--
            }
            verificationClick = false
        }

        var verificationCode by remember {
            mutableStateOf(TextFieldValue())
        }

        val focusRequester = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current

        SeugiTheme {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    SeugiTopBar(
                        title = {
                            Text(
                                text = "이메일 인증",
                                style = MaterialTheme.typography.titleLarge,
                            )
                        },
                        onNavigationIconClick = { popBackStack() },
                        backIconCheck = true,
                    )
                },
            ) {
                if (dialogState) {
                    SeugiDialog(
                        title = "인증코드를 전송했어요",
                        content = "이메일 함을 확인해 보세요",
                        onDismissRequest = {
                            dialogState = false
                        },
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(it)
                        .focusRequester(focusRequester)
                        .padding(horizontal = 16.dp),
                ) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Row(
                            modifier = Modifier.padding(start = 4.dp),
                        ) {
                            Text(text = "인증코드", style = MaterialTheme.typography.titleMedium)
                            Text(
                                text = " *",
                                style = MaterialTheme.typography.titleMedium,
                                color = Red500,
                            )
                        }
                        SeugiCodeTextField(
                            modifier = Modifier.padding(vertical = 4.dp),
                            value = verificationCode,
                            limit = 6,
                            onValueChange = { newInput ->
                                val newValue = if (newInput.text.isNotBlank()) {
                                    newInput.text
                                        .replace(" ", "")
                                        .replace(",", "")
                                } else {
                                    newInput.text
                                }
                                if (newValue.length > 6) {
                                    focusManager.clearFocus(true)
                                    return@SeugiCodeTextField
                                }
                                verificationCode = verificationCode.copy(
                                    text = newValue,
                                    selection = TextRange(newValue.length),
                                )
                            },
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            horizontalArrangement = Arrangement.End,
                        ) {
                            if (!verificationClick) {
                                SeugiButton(
                                    onClick = {
                                        verificationClick = true
                                        timeLeft = 300
                                        dialogState = true
                                    },
                                    type = ButtonType.Primary,
                                    text = "인증 코드 전송",
                                )
                            } else {
                                Text(
                                    text = "$formattedTime",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Gray600,
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))

                    SeugiFullWidthButton(
                        enabled = if (verificationCode.text.length == 6) true else false,
                        onClick = {
                            navigateToSchoolCode()
                        },
                        type = ButtonType.Primary,
                        text = "확인",
                        modifier = Modifier.padding(vertical = 16.dp),
                    )
                }
            }
        }
    }
}
