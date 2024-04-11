package com.apeun.gidaechi.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apeun.gidaechi.designsystem.component.ButtonType
import com.apeun.gidaechi.designsystem.component.SeugiFullWidthButton
import com.apeun.gidaechi.designsystem.theme.Gradient
import com.apeun.gidaechi.designsystem.theme.SeugiTheme
import com.apeun.gidaechi.designsystem.theme.White

@Composable
fun OnboardingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(Gradient))
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Image(
                painter = painterResource(id = com.apeun.gidaechi.designsystem.R.drawable.image_cloud1),
                contentDescription = ""
            )

        }
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier.padding(start = 24.dp)
        ) {
            Text(text = "스기", style = MaterialTheme.typography.displayLarge, color = White)
            Text(
                text = "학생, 선생님 모두 함께하는\n스마트 스쿨 플랫폼",
                style = MaterialTheme.typography.titleMedium,
                color = White
            )
        }
        Spacer(modifier = Modifier.weight(1f))


        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = com.apeun.gidaechi.designsystem.R.drawable.image_cloud2),
                contentDescription = ""
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        SeugiFullWidthButton(
            onClick = { /*TODO*/ },
            type = ButtonType.Transparent,
            text = "시작하기",
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
@Preview
private fun OnboardingPreview() {
    SeugiTheme {
        OnboardingScreen()
    }
}