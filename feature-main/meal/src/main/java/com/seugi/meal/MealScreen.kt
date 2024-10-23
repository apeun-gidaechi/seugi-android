package com.seugi.meal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seugi.designsystem.component.DividerType
import com.seugi.designsystem.component.SeugiDatePicker
import com.seugi.designsystem.component.SeugiDatePickerColors
import com.seugi.designsystem.component.SeugiDatePickerDefaults
import com.seugi.designsystem.component.SeugiDatePickerMonth
import com.seugi.designsystem.component.SeugiDivider
import com.seugi.designsystem.component.SeugiTopBar
import com.seugi.designsystem.component.rememberSeugiDatePickerState
import com.seugi.designsystem.component.shimmerEffectBrush
import com.seugi.designsystem.theme.SeugiTheme
import kotlinx.collections.immutable.ImmutableList

@Composable
fun MealScreen(
    viewModel: MealViewModel = hiltViewModel(),
    workspaceId: String,
    popBackStack: () -> Unit
) {

    val uiState by viewModel.state.collectAsStateWithLifecycle()

    val datePickerState = rememberSeugiDatePickerState()

    LaunchedEffect(workspaceId) {
        viewModel.loadMeal(workspaceId)
    }

    LaunchedEffect(datePickerState.selectedDate) {
        viewModel.changeDate(datePickerState.selectedDate)
    }

    Scaffold(
        topBar = {
            SeugiTopBar(
                title = {
                    Text(
                        text = "급식",
                        style = SeugiTheme.typography.subtitle1,
                        color = SeugiTheme.colors.black
                    )
                },
                onNavigationIconClick = popBackStack,
                containerColors = SeugiTheme.colors.primary050
            )
        },
        containerColor = SeugiTheme.colors.primary050
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            SeugiDatePickerMonth(
                modifier = Modifier.padding(horizontal = 16.dp),
                month = datePickerState.month,
                selectDate = datePickerState.selectedDate,
                weekdayNames = datePickerState.weekdayNames,
                colors = SeugiDatePickerDefaults.defaultColor(),
                isValidDate = datePickerState::validDate,
                isFixedSize = false,
                onClickDate = { date, isValid ->
                    datePickerState.selectedDate = date
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = SeugiTheme.colors.white,
                        shape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp)
                    )
            ) {
                Spacer(modifier = Modifier.height(22.dp))
                if (uiState.isLoading) {
                    MealLoadingCard(Modifier.padding(horizontal = 16.dp))
                    MealLoadingCard(Modifier.padding(horizontal = 16.dp))
                    MealLoadingCard(Modifier.padding(horizontal = 16.dp))
                }
                if (uiState.filterMealData.breakfast != null) {
                    MealCard(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        category = "아침",
                        kcal = uiState.filterMealData.breakfast!!.calorie,
                        menu = uiState.filterMealData.breakfast!!.menu
                    )
                    if (uiState.filterMealData.lunch != null || uiState.filterMealData.dinner != null) {
                        SeugiDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            type = DividerType.WIDTH
                        )
                    }
                }

                if (uiState.filterMealData.lunch != null) {
                    MealCard(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        category = "점심",
                        kcal = uiState.filterMealData.lunch!!.calorie,
                        menu = uiState.filterMealData.lunch!!.menu
                    )
                    if (uiState.filterMealData.dinner != null) {
                        SeugiDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            type = DividerType.WIDTH
                        )
                    }
                }

                if (uiState.filterMealData.dinner != null) {
                    MealCard(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        category = "저녁",
                        kcal = uiState.filterMealData.dinner!!.calorie,
                        menu = uiState.filterMealData.dinner!!.menu
                    )
                }
            }
        }
    }

}

@Composable
private fun MealCard(
    modifier: Modifier = Modifier,
    category: String,
    kcal: String,
    menu: ImmutableList<String>,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = category,
                style = SeugiTheme.typography.subtitle2,
                color = SeugiTheme.colors.black
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = kcal,
                style = SeugiTheme.typography.body2,
                color = SeugiTheme.colors.gray600
            )
        }
        menu.fastForEach {
            Text(
                text = it,
                style = SeugiTheme.typography.body2,
                color = SeugiTheme.colors.black
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun MealLoadingCard(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(28.dp)
                    .height(21.dp)
                    .background(
                        brush = shimmerEffectBrush(),
                        shape = CircleShape
                    )
            )
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .width(51.dp)
                    .height(18.dp)
                    .background(
                        brush = shimmerEffectBrush(),
                        shape = CircleShape
                    )
            )
        }
        Box(
            modifier = Modifier
                .width(120.dp)
                .height(18.dp)
                .background(
                    brush = shimmerEffectBrush(),
                    shape = CircleShape
                )
        )

        Box(
            modifier = Modifier
                .width(120.dp)
                .height(18.dp)
                .background(
                    brush = shimmerEffectBrush(),
                    shape = CircleShape
                )
        )
        Box(
            modifier = Modifier
                .width(120.dp)
                .height(18.dp)
                .background(
                    brush = shimmerEffectBrush(),
                    shape = CircleShape
                )
        )
        Box(
            modifier = Modifier
                .width(120.dp)
                .height(18.dp)
                .background(
                    brush = shimmerEffectBrush(),
                    shape = CircleShape
                )
        )
        Box(
            modifier = Modifier
                .width(120.dp)
                .height(18.dp)
                .background(
                    brush = shimmerEffectBrush(),
                    shape = CircleShape
                )
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}