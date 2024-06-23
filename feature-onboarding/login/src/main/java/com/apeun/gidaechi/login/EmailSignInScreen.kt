package com.apeun.gidaechi.login

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.apeun.gidaechi.designsystem.animation.bounceClick
import com.apeun.gidaechi.designsystem.component.ButtonType
import com.apeun.gidaechi.designsystem.component.SeugiDialog
import com.apeun.gidaechi.designsystem.component.SeugiFullWidthButton
import com.apeun.gidaechi.designsystem.component.SeugiTopBar
import com.apeun.gidaechi.designsystem.component.textfield.SeugiPasswordTextField
import com.apeun.gidaechi.designsystem.component.textfield.SeugiTextField
import com.apeun.gidaechi.designsystem.theme.Gray600
import com.apeun.gidaechi.designsystem.theme.Primary500
import com.apeun.gidaechi.designsystem.theme.Red500
import com.apeun.gidaechi.designsystem.theme.SeugiTheme
import com.apeun.gidaechi.login.model.EmailSignInSideEffect
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EmailSignInScreen(
    navigateToEmailSignUp: () -> Unit,
    popBackStack: () -> Unit,
    onboardingToMain: () -> Unit,
    signUpToSchoolJoin:() -> Unit,
    viewModel: EmailSignInViewModel = hiltViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()

    val state by viewModel.state.collectAsState()
    var emailValue by remember { mutableStateOf("") }
    var pwValue by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf(false) }
    var pwError by remember { mutableStateOf(false) }
    var failedLogin by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        viewModel.emailSignInSideEffect.collectLatest { value ->
            when (value) {
                // Handle events
                is EmailSignInSideEffect.SuccessLogin -> {
                    signUpToSchoolJoin()

                }

                is EmailSignInSideEffect.FailedLogin -> {
                    failedLogin = true
                }
            }
        }
    }

    SeugiTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                SeugiTopBar(
                    title = { Text(text = "로그인", style = MaterialTheme.typography.titleLarge) },
                    onNavigationIconClick = popBackStack,
                    backIconCheck = true,
                )
            },
        ) {
            if (failedLogin) {
                SeugiDialog(
                    title = "로그인 실패",
                    content = "아이디 또는 비밀번호를 다시 입력해 주세요",
                    onDismissRequest = {
                        failedLogin = false
                    },
                )
            }
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                ) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.padding(start = 4.dp),
                    ) {
                        Text(text = "이메일", style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = " *",
                            style = MaterialTheme.typography.titleMedium,
                            color = Red500,
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    SeugiTextField(
                        value = emailValue,
                        onValueChange = { emailValue = it },
                        onClickDelete = { emailValue = "" },
                        placeholder = "이메일을 입력해 주세요",
                    )
                    if (emailError) {
                        Text(
                            text = "이메일을 입력해 주세요",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Red500,
                            modifier = Modifier.padding(start = 4.dp),
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                ) {
                    Row(
                        modifier = Modifier.padding(start = 4.dp),
                    ) {
                        Text(text = "비밀번호", style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = " *",
                            style = MaterialTheme.typography.titleMedium,
                            color = Red500,
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    SeugiPasswordTextField(
                        value = pwValue,
                        onValueChange = { pwValue = it },
                        placeholder = "비밀번호를 입력해 주세요",
                    )
                    if (pwError) {
                        Text(
                            text = "비밀번호을 입력해 주세요",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Red500,
                            modifier = Modifier.padding(start = 4.dp),
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp),
                verticalArrangement = Arrangement.Bottom,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "계정이 없으시다면?",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Gray600,
                    )
                    Text(
                        text = " 가입하기",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Primary500,
                        modifier = Modifier
                            .bounceClick({ navigateToEmailSignUp() }),
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                SeugiFullWidthButton(
                    onClick = {
                        emailError = emailValue.isEmpty()
                        pwError = pwValue.isEmpty()

                        if (!emailError && !pwError) {
                            viewModel.emailSignIn(
                                email = emailValue,
                                password = pwValue,
                            )
                        }
                    },
                    type = ButtonType.Primary,
                    text = "로그인",
                    modifier = Modifier.padding(horizontal = 20.dp),
                )
            }
        }
    }
}
