package com.seugi.home.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.R
import com.seugi.designsystem.animation.bounceClick
import com.seugi.designsystem.component.GradientPrimary
import com.seugi.designsystem.component.LoadingDotsIndicator
import com.seugi.designsystem.component.modifier.brushDraw
import com.seugi.designsystem.theme.SeugiTheme
import com.seugi.home.HomeCard
import com.seugi.home.HomeNotFoundText
import com.seugi.home.model.CommonUiState
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun CatSeugiCard(
    uiState: CommonUiState<ImmutableList<String>>,
    navigateToChatSeugi: () -> Unit
) {
    HomeCard(
        text = "캣스기",
        image = painterResource(id = R.drawable.ic_appicon_round),
        modifier = Modifier,
    ) {
        when (uiState) {
            is CommonUiState.Success -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = (1.5).dp,
                                brush = GradientPrimary,
                                shape = CircleShape,
                            )
                            .bounceClick(
                                onClick = navigateToChatSeugi,
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            modifier = Modifier.padding(vertical = (15.5).dp),
                            text = "2학년 4반에 아무나 한명 뽑아줘...",
                            style = SeugiTheme.typography.subtitle2,
                            color = SeugiTheme.colors.gray500,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Image(
                            modifier = Modifier.brushDraw(GradientPrimary),
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "",
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "지난주",
                        style = SeugiTheme.typography.body2,
                        color = SeugiTheme.colors.gray600,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = SeugiTheme.colors.gray100,
                                    shape = RoundedCornerShape(4.dp),
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                modifier = Modifier.padding(vertical = (12).dp),
                                text = "급식에 복어가 나오는 날이 언제..",
                                style = SeugiTheme.typography.body1,
                                color = SeugiTheme.colors.black,
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = "6월 21일",
                                style = SeugiTheme.typography.body2,
                                color = SeugiTheme.colors.gray600,
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = SeugiTheme.colors.gray100,
                                    shape = RoundedCornerShape(4.dp),
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                modifier = Modifier.padding(vertical = (12).dp),
                                text = "우리 학교 대회 담당하는 분이 누구...",
                                style = SeugiTheme.typography.body1,
                                color = SeugiTheme.colors.black,
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = "6월 21일",
                                style = SeugiTheme.typography.body2,
                                color = SeugiTheme.colors.gray600,
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                        }
                    }
                }
            }

            is CommonUiState.NotFound -> {
                HomeNotFoundText(text = "학교를 등록하고 캣스기와 대화해 보세요")
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