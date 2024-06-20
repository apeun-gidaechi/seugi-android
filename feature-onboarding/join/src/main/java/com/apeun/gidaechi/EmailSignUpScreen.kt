package com.apeun.gidaechi

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.apeun.gidaechi.designsystem.component.ButtonType
import com.apeun.gidaechi.designsystem.component.SeugiFullWidthButton
import com.apeun.gidaechi.designsystem.component.SeugiTopBar
import com.apeun.gidaechi.designsystem.component.textfield.SeugiPasswordTextField
import com.apeun.gidaechi.designsystem.component.textfield.SeugiTextField
import com.apeun.gidaechi.designsystem.theme.Red500
import com.apeun.gidaechi.designsystem.theme.SeugiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EmailSignUpScreen(navigateToEmailVerification: (name: String, email: String, password: String) -> Unit, popBackStack: () -> Unit) {
    var nameText by remember { mutableStateOf("") }
    var emailText by remember { mutableStateOf("") }
    var pwText by remember { mutableStateOf("") }
    var pwCheckText by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    SeugiTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                SeugiTopBar(
                    title = { Text(text = "회원가입", style = MaterialTheme.typography.titleLarge) },
                    onNavigationIconClick = popBackStack,
                    backIconCheck = true,
                )
            },
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(top = 6.dp)
                    .padding(horizontal = 20.dp),
            ) {
                Column(
                    modifier = Modifier.padding(bottom = 16.dp),
                ) {
                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.padding(start = 4.dp),
                    ) {
                        Text(text = "이름", style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = " *",
                            style = MaterialTheme.typography.titleMedium,
                            color = Red500,
                        )
                    }
                    SeugiTextField(
                        value = nameText,
                        onValueChange = { nameText = it },
                        onClickDelete = { nameText = "" },
                        placeholder = "이름을 입력해 주세요",
                        modifier = Modifier.padding(vertical = 4.dp),
                    )
                    if (error == "blank_name") {
                        Text(
                            text = "이름을 입력해 주세요",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Red500,
                            modifier = Modifier.padding(start = 4.dp),
                        )
                    }
                }
                Column(
                    modifier = Modifier.padding(bottom = 16.dp),
                ) {
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
                    SeugiTextField(
                        value = emailText,
                        onValueChange = { emailText = it },
                        onClickDelete = { emailText = "" },
                        placeholder = "이메일을 입력해 주세요",
                        modifier = Modifier.padding(vertical = 4.dp),
                    )
                    if (error == "blank_email" || error == "email_fomat_is_not") {
                        Text(
                            text = if (error == "blank_email") "이메일을 입력해수세요" else if (error == "email_fomat_is_not")"이메일 형식을 맞춰주세요" else "",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Red500,
                            modifier = Modifier.padding(start = 4.dp),
                        )
                    }
                }
                Column(
                    modifier = Modifier.padding(bottom = 16.dp),
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
                    SeugiPasswordTextField(
                        value = pwText,
                        onValueChange = { pwText = it },
                        placeholder = "비밀번호를 입력해 주세요",
                        modifier = Modifier.padding(vertical = 4.dp),
                    )
                    if (error == "blank_password") {
                        Text(
                            text = "비밀번호를 입력해 주세요",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Red500,
                            modifier = Modifier.padding(start = 4.dp),
                        )
                    }
                }
                Column(
                    modifier = Modifier.padding(bottom = 16.dp),
                ) {
                    Row(
                        modifier = Modifier.padding(start = 4.dp),
                    ) {
                        Text(text = "비밀번호 확인", style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = " *",
                            style = MaterialTheme.typography.titleMedium,
                            color = Red500,
                        )
                    }
                    SeugiPasswordTextField(
                        value = pwCheckText,
                        onValueChange = { pwCheckText = it },
                        placeholder = "비밀번호를 다시 입력해 주세요",
                        modifier = Modifier.padding(vertical = 4.dp),
                    )
                    if (error == "different_password") {
                        Text(
                            text = "비밀번호가 다릅니다",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Red500,
                            modifier = Modifier.padding(start = 4.dp),
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                SeugiFullWidthButton(
                    onClick = {
                        error = errorCheck(
                            name = nameText,
                            email = emailText,
                            password = pwText,
                            checkPassword = pwCheckText,
                        )
                        if (error == "success") {
                            navigateToEmailVerification(
                                nameText,
                                emailText,
                                pwText,
                            )
                        }
                    },
                    type = ButtonType.Primary,
                    text = "계속하기",
                    modifier = Modifier
                        .padding(vertical = 16.dp),
                )
            }
        }
    }
}

private fun errorCheck(name: String, email: String, password: String, checkPassword: String): String {
    if (name.isEmpty()) {
        return "blank_name"
    } else if (email.isEmpty()) {
        return "blank_email"
    } else if (!email.contains("@") || email.split("@").size != 2 || email.split("@")[1].isEmpty()) {
        return "email_fomat_is_not"
    } else if (password.isEmpty()) {
        return "blank_password"
    } else if (password != checkPassword) {
        return "different_password"
    }
    return "success"
}
