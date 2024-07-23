package com.seugi.join

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.component.ButtonType
import com.seugi.designsystem.component.SeugiFullWidthButton
import com.seugi.designsystem.component.SeugiRoundedCircleImage
import com.seugi.designsystem.component.SeugiTopBar
import com.seugi.designsystem.component.Size
import com.seugi.designsystem.theme.Gray600
import com.seugi.designsystem.theme.SeugiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoinSuccessScreen(
    navigateToSelectingJob: (workspaceId: String, workspaceCode: String) -> Unit,
    popBackStack: () -> Unit,
    schoolCode: String,
    workspaceId: String,
    workspaceName: String,
    workspaceImageUrl: String,
    studentCount: Int,
    teacherCount: Int,
) {
    SeugiTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                SeugiTopBar(
                    title = { Text(text = "학교 가입", style = MaterialTheme.typography.titleLarge) },
                    onNavigationIconClick = popBackStack,
                    backIconCheck = true,
                )
            },
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
                    SeugiRoundedCircleImage(
                        size = Size.Small,
                        image = workspaceImageUrl,
                        onClick = {},
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = workspaceName, style = MaterialTheme.typography.titleLarge)
                    Text(
                        text = "학생 ${studentCount}명 선생님 ${teacherCount}명",
                        style = MaterialTheme.typography.titleMedium,
                        color = Gray600,
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                SeugiFullWidthButton(
                    onClick = {
                        navigateToSelectingJob(
                            workspaceId,
                            schoolCode,
                        )
                    },
                    type = ButtonType.Primary,
                    text = "계속하기",
                    modifier = Modifier.padding(vertical = 16.dp),
                )
            }
        }
    }
}
