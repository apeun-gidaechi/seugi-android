package com.apeun.gidaechi

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.apeun.gidaechi.designsystem.animation.NoInteractionSource
import com.apeun.gidaechi.designsystem.component.ButtonType
import com.apeun.gidaechi.designsystem.component.SeugiFullWidthButton
import com.apeun.gidaechi.designsystem.component.SeugiTopBar
import com.apeun.gidaechi.designsystem.component.textfield.SeugiCodeTextField
import com.apeun.gidaechi.designsystem.theme.SeugiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchoolScreen(navigateToJoinSuccess: () -> Unit, popBackStack: () -> Unit) {
    var schoolCode by remember {
        mutableStateOf(TextFieldValue())
    }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    SeugiTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                SeugiTopBar(
                    title = { Text(text = "학교 가입", style = MaterialTheme.typography.titleLarge) },
                    onNavigationIconClick = popBackStack,
                )
            },
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                    .focusRequester(focusRequester)
                    .clickable(
                        interactionSource = NoInteractionSource(),
                        indication = null,
                    ) {
                        focusManager.clearFocus(true)
                    },
            ) {
                Text(
                    text = "학교 코드",
                    modifier = Modifier.padding(start = 4.dp, bottom = 4.dp),
                    style = MaterialTheme.typography.titleMedium,
                )
                SeugiCodeTextField(
                    value = schoolCode,
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
                        schoolCode = schoolCode.copy(
                            text = newValue,
                            selection = TextRange(newValue.length),
                        )
                    },
                )
                Spacer(modifier = Modifier.weight(1f))
                SeugiFullWidthButton(
                    enabled = if (schoolCode.text.length == 6) true else false,
                    onClick = navigateToJoinSuccess,
                    type = ButtonType.Primary,
                    text = "계속하기",
                    modifier = Modifier.padding(vertical = 16.dp),
                )
            }
        }
    }
}
