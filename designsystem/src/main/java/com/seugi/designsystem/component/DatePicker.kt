package com.seugi.designsystem.component

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.seugi.designsystem.R
import com.seugi.designsystem.animation.bounceClick
import com.seugi.designsystem.component.DatePickerDefaults.defaultLocale
import com.seugi.designsystem.component.modifier.`if`
import com.seugi.designsystem.theme.Black
import com.seugi.designsystem.theme.Gray600
import com.seugi.designsystem.theme.Primary500
import com.seugi.designsystem.theme.SeugiTheme
import com.seugi.designsystem.theme.White
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.Locale

@Composable
fun SeugiDatePicker(
    calendarModel: CalendarModel,
    month: CalendarMonth,
    selectDate: LocalDate? = null,
    isFixedSize: Boolean = false,
    isValidDate: (date: LocalDate) -> Boolean = {
        month.isValidDate(it, calendarModel)
    },
    onClickPrevMonth: () -> Unit,
    onClickNextMonth: () -> Unit,
    onClickDate: (date: LocalDate, isValid: Boolean) -> Unit,
    onClickSuccess: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        SeugiTimePickerHeader(
            month = month,
            onClickPrevMonth = onClickPrevMonth,
            onClickNextMonth = onClickNextMonth
        )
        Spacer(modifier = Modifier.height(16.dp))
        SeugiTimePickerMonth(
            month = month,
            selectDate = selectDate,
            weekdayNames = calendarModel.weekdayNames,
            isFixedSize = isFixedSize,
            isValidDate = isValidDate,
            onClickDate = onClickDate
        )
        Spacer(modifier = Modifier.height(8.dp))
        SeugiFullWidthButton(
            onClick = onClickSuccess,
            type = ButtonType.Primary,
            text = "선택"
        )
    }
}

@Composable
private fun SeugiTimePickerHeader(
    month: CalendarMonth,
    onClickPrevMonth: () -> Unit,
    onClickNextMonth: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "${month.year}년 ${month.month}월",
            style = MaterialTheme.typography.titleMedium,
            color = Black,
        )
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .size(36.dp)
                .bounceClick(
                    onClick = onClickPrevMonth
                ),
            contentAlignment = Alignment.Center
        ) {
            SeugiImage(
                modifier = Modifier.size(20.dp),
                resId = R.drawable.ic_expand_left_line,
                colorFilter = ColorFilter.tint(Primary500)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .size(36.dp)
                .bounceClick(
                    onClick = onClickNextMonth
                ),
            contentAlignment = Alignment.Center
        ) {
            SeugiImage(
                modifier = Modifier.size(20.dp),
                resId = R.drawable.ic_expand_right_line,
                colorFilter = ColorFilter.tint(Primary500)
            )
        }
    }
}

@Composable
private fun SeugiTimePickerMonth(
    month: CalendarMonth,
    selectDate: LocalDate?,
    weekdayNames: List<String>,
    isValidDate: (date: LocalDate) -> Boolean,
    isFixedSize: Boolean,
    onClickDate: (date: LocalDate, isValid: Boolean) -> Unit
) {
    var cellIndex = 0
    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth()
    ) {
        val layoutMaxWidth = maxWidth
        Column {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                weekdayNames.fastForEach { weekdayName ->
                    Text(
                        modifier = Modifier.weight(1f),
                        text = weekdayName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Gray600,
                        textAlign = TextAlign.Center
                    )
                }
            }
            val maxCalendarRows = if (isFixedSize) MaxCalendarRows else
                when {
                    (30 == month.numberOfDays && month.daysFromStartOfWeekToFirstOfMonth == 6) ||
                    (31 == month.numberOfDays && (month.daysFromStartOfWeekToFirstOfMonth == 5 || month.daysFromStartOfWeekToFirstOfMonth == 6)) -> 6
                    (28 == month.numberOfDays && month.daysFromStartOfWeekToFirstOfMonth == 0) -> 4
                    else -> 5
                }
            for (weekIndex in 0 until maxCalendarRows) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((34.33).dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (dayIndex in 0 until DaysInWeek) {
                        if (
                            cellIndex < month.daysFromStartOfWeekToFirstOfMonth ||
                            cellIndex >=
                            (month.daysFromStartOfWeekToFirstOfMonth + month.numberOfDays)
                        ) {
                            Spacer(
                                modifier = Modifier.weight(1f)
                            )
                        } else {
                            val dayNumber = cellIndex - month.daysFromStartOfWeekToFirstOfMonth + 1
                            val isSelect = dayNumber == selectDate?.dayOfMonth &&
                                    month.month == selectDate.monthValue &&
                                    month.year == selectDate.year
                            val date = LocalDate.of(month.year, month.month, dayNumber)
                            val isValid = isValidDate(date)
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .bounceClick(
                                        onClick = {
                                            onClickDate(date, isValid)
                                        }
                                    )
                                    .`if`(isSelect) {
                                        drawBehind {
                                            drawRoundRect(
                                                color = Primary500,
                                                cornerRadius = CornerRadius(10.dp.toPx()),
                                                size = Size(38.dp.toPx(), 38.dp.toPx()),
                                                topLeft = Offset(
                                                    x = (layoutMaxWidth.toPx() / 7) / 10,
                                                    y = -(9).dp.toPx()
                                                )
                                            )
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    modifier = Modifier,
                                    text = dayNumber.toString(),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = when {
                                        isSelect -> White
                                        isValid -> Gray600
                                        else -> Gray600.copy(alpha = 0.5f)
                                    }
                                )
                            }
                        }
                        cellIndex++
                    }
                }
            }
        }
    }
}


object DatePickerDefaults {

    @Stable
    fun createCalendarModel(locale: Locale): CalendarModel = CalendarModelImpl(locale)

    @Composable
    @ReadOnlyComposable
    fun defaultLocale(): Locale {
        return LocalConfiguration.current.locales[0]
    }
}


abstract class CalendarModel {

    abstract val today: Int

    abstract val absoluteToday: Int

    abstract val date: LocalDate

    abstract val firstDayOfWeek: Int

    abstract val weekdayNames: List<String>

    @Stable
    abstract fun getMonth(year: Int, month: Int): CalendarMonth

    @Stable
    abstract fun prevMonth(calendarMonth: CalendarMonth): CalendarMonth

    @Stable
    abstract fun nextMonth(calendarMonth: CalendarMonth): CalendarMonth
}


internal class CalendarModelImpl(private val locale: Locale): CalendarModel() {

    private val _today
        get() = LocalDate.now()

    override val today: Int
        get() = _today.dayOfMonth

    override val absoluteToday: Int
        get() {
            return _today.dayOfMonth * _today.monthValue * 30 * _today.year
        }

    override val date: LocalDate
        get() = _today

    override val firstDayOfWeek: Int =  WeekFields.of(locale).firstDayOfWeek.value

    override val weekdayNames: List<String> =
        DayOfWeek.entries.map {
            it.getDisplayName(TextStyle.NARROW, locale)
        }.toMutableList().apply {
            // set sunday to First Day
            add(0, removeLast())
        }

    override fun getMonth(year: Int, month: Int): CalendarMonth {
        return getMonth(LocalDate.of(year, month, 1))
    }

    override fun prevMonth(calendarMonth: CalendarMonth): CalendarMonth {
        if (calendarMonth.month == 1) {
            return getMonth(calendarMonth.year-1, 12)
        }
        return getMonth(calendarMonth.year, calendarMonth.month-1)
    }

    override fun nextMonth(calendarMonth: CalendarMonth): CalendarMonth {
        if (calendarMonth.month == 12) {
            return getMonth(calendarMonth.year+1, 1)
        }
        return getMonth(calendarMonth.year, calendarMonth.month+1)
    }

    private fun getMonth(firstDayLocalDate: LocalDate): CalendarMonth {
        val difference = firstDayLocalDate.dayOfWeek.value - firstDayOfWeek
        val daysFromStartOfWeekToFirstOfMonth =
            if (difference < 0) {
                difference + DaysInWeek
            } else {
                difference
            }
        return CalendarMonth(
            year = firstDayLocalDate.year,
            month = firstDayLocalDate.monthValue,
            numberOfDays = firstDayLocalDate.lengthOfMonth(),
            daysFromStartOfWeekToFirstOfMonth = daysFromStartOfWeekToFirstOfMonth,
        )
    }

}


data class CalendarMonth(
    val year: Int,
    val month: Int,
    val numberOfDays: Int,
    val daysFromStartOfWeekToFirstOfMonth: Int
) {
    fun isValidDate(date: LocalDate, calendarModel: CalendarModel): Boolean =
        calendarModel.date <= date
}

private const val MaxCalendarRows = 6
private const val DaysInWeek = 7


@Preview
@Composable
private fun SeugiDatePickerPreview() {
    val locale = defaultLocale()
    val calendarModel = remember { DatePickerDefaults.createCalendarModel(locale) }
    var month by remember { mutableStateOf(calendarModel.getMonth(2024, 7)) }
    var selectDate: LocalDate? by remember { mutableStateOf(null) }
    SeugiTheme {
        SeugiDatePicker(
            calendarModel = calendarModel,
            selectDate = selectDate,
            month = month,
            isFixedSize = false,
            onClickPrevMonth = {
                month = calendarModel.prevMonth(month)
            },
            onClickNextMonth = {
                month = calendarModel.nextMonth(month)
            },
            onClickDate = { date, isValid ->
                if (isValid) {
                    selectDate = date
                }
            },
            onClickSuccess = {

            }
        )
    }
}