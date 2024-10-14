package com.seugi.home.card

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.R
import com.seugi.designsystem.component.ButtonType
import com.seugi.designsystem.component.LoadingDotsIndicator
import com.seugi.designsystem.component.SeugiButton
import com.seugi.designsystem.theme.SeugiTheme
import com.seugi.home.HomeCard
import com.seugi.home.model.CommonUiState

@Composable
internal fun SchoolCard(workspaceId: String, uiState: CommonUiState<String>, navigateToWorkspaceDetail: (String) -> Unit) {
    HomeCard(
        text = "내 학교",
        image = painterResource(id = R.drawable.ic_school),
        colorFilter = ColorFilter.tint(SeugiTheme.colors.gray600),
    ) {
        when (uiState) {
            is CommonUiState.Success -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = (6.5).dp),
                        text = uiState.data,
                        style = SeugiTheme.typography.subtitle2,
                        color = SeugiTheme.colors.gray600,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    SeugiButton(
                        onClick = {
                            navigateToWorkspaceDetail(workspaceId)
                        },
                        type = ButtonType.Gray,
                        text = "전환",
                    )
                }
            }

            is CommonUiState.NotFound -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "내 학교를 등록해주세요",
                        style = SeugiTheme.typography.body2,
                        color = SeugiTheme.colors.gray600,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            else -> {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    LoadingDotsIndicator()
                }
            }
        }
    }
}
