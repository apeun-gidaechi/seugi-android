package com.apeun.gidaechi

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
import com.apeun.gidaechi.designsystem.component.ButtonType
import com.apeun.gidaechi.designsystem.component.SeugiFullWidthButton
import com.apeun.gidaechi.designsystem.component.SeugiRoundedCircleImage
import com.apeun.gidaechi.designsystem.component.SeugiTopBar
import com.apeun.gidaechi.designsystem.component.Size
import com.apeun.gidaechi.designsystem.theme.Gray600
import com.apeun.gidaechi.designsystem.theme.SeugiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoinSuccessScreen(navigateToSelectingJob: () -> Unit, popBackStack: () -> Unit) {
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
                        image = "https://images-na.ssl-images-amazon.com/images/I/41VTLQ%2BH-oL._UL1200_.jpg",
                        onClick = {},
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "대구 소프트웨어 마이스터 고등학교", style = MaterialTheme.typography.titleLarge)
                    Text(
                        text = "학생 213명 선생님 32명",
                        style = MaterialTheme.typography.titleMedium,
                        color = Gray600,
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                SeugiFullWidthButton(
                    onClick = navigateToSelectingJob,
                    type = ButtonType.Primary,
                    text = "계속하기",
                    modifier = Modifier.padding(vertical = 16.dp),
                )
            }
        }
    }
}
