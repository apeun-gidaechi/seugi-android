package com.seugi.meal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.seugi.designsystem.component.DividerType
import com.seugi.designsystem.component.SeugiDatePicker
import com.seugi.designsystem.component.SeugiDatePickerColors
import com.seugi.designsystem.component.SeugiDatePickerDefaults
import com.seugi.designsystem.component.SeugiDatePickerMonth
import com.seugi.designsystem.component.SeugiDivider
import com.seugi.designsystem.component.SeugiTopBar
import com.seugi.designsystem.component.rememberSeugiDatePickerState
import com.seugi.designsystem.theme.SeugiTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun MealScreen(
    popBackStack: () -> Unit
) {

    val datePickerState = rememberSeugiDatePickerState()

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

                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier.background(
                    color = SeugiTheme.colors.white,
                    shape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp)
                )
            ) {
                Spacer(modifier = Modifier.height(22.dp))
                MealCard(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    category = "아침",
                    kcal = "960Kcal",
                    menu = persistentListOf("쇠고기우엉볶음밥", "불고기치즈파니니", "계란실파국", "오이생채", "배추김치")
                )
                SeugiDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    type = DividerType.WIDTH
                )

                MealCard(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    category = "점심",
                    kcal = "960Kcal",
                    menu = persistentListOf("쇠고기우엉볶음밥", "불고기치즈파니니", "계란실파국", "오이생채", "배추김치")
                )
                SeugiDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    type = DividerType.WIDTH
                )

                MealCard(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    category = "저녁",
                    kcal = "960Kcal",
                    menu = persistentListOf("쇠고기우엉볶음밥", "불고기치즈파니니", "계란실파국", "오이생채", "배추김치")
                )
                SeugiDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    type = DividerType.WIDTH
                )


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