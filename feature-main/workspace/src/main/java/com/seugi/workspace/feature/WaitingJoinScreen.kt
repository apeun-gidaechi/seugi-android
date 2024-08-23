package com.seugi.workspace.feature

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.R
import com.seugi.designsystem.component.ButtonType
import com.seugi.designsystem.component.SeugiFullWidthButton
import com.seugi.designsystem.component.SeugiToolTip
import com.seugi.designsystem.component.SeugiTopBar
import com.seugi.designsystem.component.ToolTipType
import com.seugi.designsystem.theme.SeugiTheme
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaitingJoinScreen(joinToHome: () -> Unit, popBackStack: () -> Unit) {
    val painter = painterResource(id = R.drawable.img_school)

    SeugiTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                SeugiTopBar(
                    title = { Text(text = "학교 가입", style = SeugiTheme.typography.subtitle1) },
                    onNavigationIconClick = popBackStack,
                )
            },
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(horizontal = 16.dp),
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 50.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        painter = painter,
                        contentDescription = "",
                        modifier = Modifier
                            .size(145.dp)
                            .aspectRatio(painter.intrinsicSize.width / painter.intrinsicSize.height),
                        contentScale = ContentScale.Fit,
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "대구소프트웨어마이스터고등학교", style = SeugiTheme.typography.subtitle1)
                    Spacer(modifier = Modifier.height(15.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                    ) {
                        SeugiToolTip(text = "가입 수락을 대기중이에요", type = ToolTipType.Side)
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    SeugiFullWidthButton(
                        onClick = { joinToHome() },
                        type = ButtonType.Primary,
                        text = "완료",
                        modifier = Modifier.padding(vertical = 16.dp),
                    )
                }
            }
        }
    }
}
