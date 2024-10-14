package com.seugi.workspace.feature.joinsuccess

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.seugi.designsystem.component.ButtonType
import com.seugi.designsystem.component.SeugiFullWidthButton
import com.seugi.designsystem.component.SeugiRoundedCircleImage
import com.seugi.designsystem.component.SeugiTopBar
import com.seugi.designsystem.component.Size
import com.seugi.designsystem.theme.SeugiTheme
import com.seugi.workspace.feature.joinsuccess.model.JoinSuccessSideEffect
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoinSuccessScreen(
    navigateToWaiting: () -> Unit,
    popBackStack: () -> Unit,
    schoolCode: String,
    workspaceId: String,
    workspaceName: String,
    workspaceImageUrl: String,
    studentCount: Int,
    teacherCount: Int,
    role: String,
    viewModel: JoinSuccessViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    SeugiTheme {
        LaunchedEffect(key1 = Unit) {
            viewModel.joinSuccessSideEffect.collectLatest {
                when (it) {
                    is JoinSuccessSideEffect.SuccessApplication -> {
                        navigateToWaiting()
                    }

                    is JoinSuccessSideEffect.FiledApplication -> {
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
            containerColor = SeugiTheme.colors.white
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    if (workspaceImageUrl == "basics") {
                        SeugiRoundedCircleImage(
                            size = Size.Medium,
                            onClick = {},
                        )
                    } else {
                        SeugiRoundedCircleImage(
                            size = Size.Medium,
                            image = workspaceImageUrl,
                            onClick = {},
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = workspaceName, style = SeugiTheme.typography.subtitle1)
                    Text(
                        text = "학생 ${studentCount}명 선생님 ${teacherCount}명",
                        style = SeugiTheme.typography.subtitle2,
                        color = SeugiTheme.colors.gray600,
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                SeugiFullWidthButton(
                    onClick = {
                        viewModel.workspaceApplication(
                            role = role,
                            workspaceId = workspaceId,
                            workspaceCode = schoolCode,
                        )
                    },
                    type = ButtonType.Primary,
                    text = "계속하기",
                    modifier = Modifier.padding(bottom = 16.dp),
                )
            }
        }
    }
}
