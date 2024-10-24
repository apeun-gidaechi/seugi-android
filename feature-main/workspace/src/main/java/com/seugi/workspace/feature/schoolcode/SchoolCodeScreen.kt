package com.seugi.workspace.feature.schoolcode

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.seugi.designsystem.animation.NoInteractionSource
import com.seugi.designsystem.component.ButtonType
import com.seugi.designsystem.component.SeugiFullWidthButton
import com.seugi.designsystem.component.SeugiTopBar
import com.seugi.designsystem.component.textfield.SeugiCodeTextField
import com.seugi.designsystem.theme.SeugiTheme
import com.seugi.workspace.feature.schoolcode.model.SchoolCodeSideEffect
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchoolScreen(
    navigateToJoinSuccess: (
        schoolCode: String,
        workspaceId: String,
        workspaceName: String,
        workspaceImageUrl: String,
        studentCount: Int,
        teacherCount: Int,
        role: String,
    ) -> Unit,
    popBackStack: () -> Unit,
    viewModel: SchoolCodeViewModel = hiltViewModel(),
    role: String,
) {
    var schoolCode by remember {
        mutableStateOf(TextFieldValue())
    }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    SeugiTheme {
        LaunchedEffect(key1 = Unit) {
            viewModel.schoolCodeSideEffect.collectLatest {
                when (it) {
                    is SchoolCodeSideEffect.SuccessSearchWorkspace -> {
                        val data = viewModel.schoolCodeModel.value

                        navigateToJoinSuccess(
                            schoolCode.text,
                            data.workspaceId,
                            data.workspaceName,
                            data.workspaceImageUrl,
                            data.studentCount,
                            data.teacherCount,
                            role,
                        )
                    }
                    is SchoolCodeSideEffect.FiledSearchWorkspace -> {
                        Toast.makeText(context, it.throwable.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                SeugiTopBar(
                    title = { Text(text = "학교 가입", style = SeugiTheme.typography.subtitle1) },
                    onNavigationIconClick = popBackStack,
                )
            },
            containerColor = SeugiTheme.colors.white,
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
                    style = SeugiTheme.typography.subtitle2,
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
                    onClick = {
                        viewModel.checkWorkspace(schoolCode = schoolCode.text)
                    },
                    type = ButtonType.Primary,
                    text = "계속하기",
                )
            }
        }
    }
}
