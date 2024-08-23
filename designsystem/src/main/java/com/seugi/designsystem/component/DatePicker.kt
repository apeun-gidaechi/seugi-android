package com.seugi.designsystem.component

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.window.Dialog
import com.seugi.designsystem.R
import com.seugi.designsystem.animation.bounceClick
import com.seugi.designsystem.component.SeugiDatePickerDefaults.createCalendarModel
import com.seugi.designsystem.component.SeugiDatePickerDefaults.defaultLocale
import com.seugi.designsystem.component.modifier.`if`
import com.seugi.designsystem.theme.SeugiTheme
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.Locale
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun SeugiDatePickerDialog(
    modifier: Modifier = Modifier,
    state: SeugiDatePickerState = rememberSeugiDatePickerState(),
    isFixedSize: Boolean = false,
    colors: SeugiDatePickerColors = SeugiDatePickerDefaults.defaultColor(),
    isValidDate: (date: LocalDate) -> Boolean = { state.validDate(it) },
    onDismissRequest: () -> Unit,
    onClickPrevMonth: () -> Unit = { state.prevMonth() },
    onClickNextMonth: () -> Unit = { state.nextMonth() },
    onClickDate: (date: LocalDate, isValid: Boolean) -> Unit,
    onClickSuccess: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Surface(
            color = SeugiTheme.colors.white,
            shape = RoundedCornerShape(28.dp),
        ) {
            SeugiDatePicker(
                modifier = modifier,
                state = state,
                isFixedSize = isFixedSize,
                colors = colors,
                isValidDate = isValidDate,
                onClickPrevMonth = onClickPrevMonth,
                onClickNextMonth = onClickNextMonth,
                onClickDate = onClickDate,
                onClickSuccess = onClickSuccess,
            )
        }
    }
}

@Composable
fun SeugiDatePicker(
    modifier: Modifier = Modifier,
    state: SeugiDatePickerState = rememberSeugiDatePickerState(),
    isFixedSize: Boolean = false,
    colors: SeugiDatePickerColors = SeugiDatePickerDefaults.defaultColor(),
    isValidDate: (date: LocalDate) -> Boolean = { state.validDate(it) },
    onClickPrevMonth: () -> Unit = { state.prevMonth() },
    onClickNextMonth: () -> Unit = { state.nextMonth() },
    onClickDate: (date: LocalDate, isValid: Boolean) -> Unit,
    onClickSuccess: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp)
            .background(color = colors.containerColor),
    ) {
        SeugiTimePickerHeader(
            month = state.month,
            colors = colors,
            onClickPrevMonth = onClickPrevMonth,
            onClickNextMonth = onClickNextMonth,
        )
        Spacer(modifier = Modifier.height(16.dp))
        SeugiTimePickerMonth(
            month = state.month,
            selectDate = state.selectedDate,
            weekdayNames = state.weekdayNames,
            colors = colors,
            isFixedSize = isFixedSize,
            isValidDate = isValidDate,
            onClickDate = onClickDate,
        )
        Spacer(modifier = Modifier.height(8.dp))
        SeugiFullWidthButton(
            onClick = onClickSuccess,
            type = ButtonType.Primary,
            text = "선택",
        )
    }
}

@Composable
private fun SeugiTimePickerHeader(month: CalendarMonth, colors: SeugiDatePickerColors, onClickPrevMonth: () -> Unit, onClickNextMonth: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "${month.year}년 ${month.month}월",
            style = SeugiTheme.typography.subtitle2,
            color = colors.titleContentColor,
        )
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .size(36.dp)
                .bounceClick(
                    onClick = onClickPrevMonth,
                ),
            contentAlignment = Alignment.Center,
        ) {
            SeugiImage(
                modifier = Modifier.size(20.dp),
                resId = R.drawable.ic_expand_left_line,
                colorFilter = ColorFilter.tint(colors.titleImageColor),
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .size(36.dp)
                .bounceClick(
                    onClick = onClickNextMonth,
                ),
            contentAlignment = Alignment.Center,
        ) {
            SeugiImage(
                modifier = Modifier.size(20.dp),
                resId = R.drawable.ic_expand_right_line,
                colorFilter = ColorFilter.tint(colors.titleImageColor),
            )
        }
    }
}

@Composable
private fun SeugiTimePickerMonth(
    month: CalendarMonth,
    selectDate: LocalDate?,
    weekdayNames: List<String>,
    colors: SeugiDatePickerColors,
    isValidDate: (date: LocalDate) -> Boolean,
    isFixedSize: Boolean,
    onClickDate: (date: LocalDate, isValid: Boolean) -> Unit,
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth(),
    ) {
        maxWidth
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(18.dp)
                    .background(colors.weeklyContainerColor),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                weekdayNames.fastForEach { weekdayName ->
                    Text(
                        modifier = Modifier.weight(1f),
                        text = weekdayName,
                        style = SeugiTheme.typography.body2,
                        color = colors.weeklyContentColor,
                        textAlign = TextAlign.Center,
                    )
                }
            }
            val maxCalendarRows = if (isFixedSize) {
                MAX_CALENDAR_ROWS
            } else {
                when {
                    (30 == month.numberOfDays && month.daysFromStartOfWeekToFirstOfMonth == 6) ||
                        (31 == month.numberOfDays && (month.daysFromStartOfWeekToFirstOfMonth == 5 || month.daysFromStartOfWeekToFirstOfMonth == 6)) -> 6
                    (28 == month.numberOfDays && month.daysFromStartOfWeekToFirstOfMonth == 0) -> 4
                    else -> 5
                }
            }
            for (weekIndex in 0 until maxCalendarRows) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((34.33).dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    for (dayIndex in 0 until DAYS_IN_WEEK) {
                        val cellIndex = weekIndex * DAYS_IN_WEEK + dayIndex
                        if (
                            cellIndex < month.daysFromStartOfWeekToFirstOfMonth ||
                            cellIndex >=
                            (month.daysFromStartOfWeekToFirstOfMonth + month.numberOfDays)
                        ) {
                            Spacer(
                                modifier = Modifier.weight(1f),
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
                                        },
                                    )
                                    .`if`(isSelect) {
                                        drawBehind {
                                            val boxWidth = size.width
                                            val indicatorSize = 38.dp.toPx()
                                            val offsetX = (boxWidth - indicatorSize) / 2
                                            drawRoundRect(
                                                color = colors.selectDayContainerColor,
                                                cornerRadius = CornerRadius(10.dp.toPx()),
                                                size = Size(38.dp.toPx(), 38.dp.toPx()),
                                                topLeft = Offset(
                                                    x = offsetX,
                                                    y = -(9).dp.toPx(),
                                                ),
                                            )
                                        }
                                    },
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    modifier = Modifier,
                                    text = dayNumber.toString(),
                                    style = SeugiTheme.typography.subtitle2,
                                    color = colors.dayContentColor(
                                        selected = isSelect,
                                        active = isValid,
                                    ),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

object SeugiDatePickerDefaults {

    @Stable
    fun createCalendarModel(locale: Locale): CalendarModel = CalendarModelImpl(locale)

    @Composable
    @ReadOnlyComposable
    fun defaultLocale(): Locale {
        return LocalConfiguration.current.locales[0]
    }

    @Composable
    @Stable
    fun defaultColor() = SeugiDatePickerColors(
        containerColor = SeugiTheme.colors.white,
        titleContentColor = SeugiTheme.colors.black,
        titleImageColor = SeugiTheme.colors.primary500,
        weeklyContainerColor = SeugiTheme.colors.transparent,
        weeklyContentColor = SeugiTheme.colors.gray600,
        activeDayContentColor = SeugiTheme.colors.gray600,
        unActiveDayContentColor = SeugiTheme.colors.gray600.copy(alpha = 0.5f),
        selectDayContainerColor = SeugiTheme.colors.primary500,
        selectDayContentColor = SeugiTheme.colors.white,
    )
}

abstract class CalendarModel {

    abstract val today: Int

    abstract val absoluteToday: Int

    abstract val date: LocalDate

    abstract val firstDayOfWeek: Int

    abstract val weekdayNames: ImmutableList<String>

    @Stable
    abstract fun getMonth(year: Int, month: Int): CalendarMonth

    @Stable
    abstract fun prevMonth(calendarMonth: CalendarMonth): CalendarMonth

    @Stable
    abstract fun nextMonth(calendarMonth: CalendarMonth): CalendarMonth
}

internal class CalendarModelImpl(private val locale: Locale) : CalendarModel() {

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

    override val firstDayOfWeek: Int = WeekFields.of(locale).firstDayOfWeek.value

    override val weekdayNames: ImmutableList<String> =
        DayOfWeek.entries.map {
            it.getDisplayName(TextStyle.NARROW, locale)
        }.toMutableList().apply {
            // set sunday to First Day
            add(0, removeLast())
        }.toImmutableList()

    override fun getMonth(year: Int, month: Int): CalendarMonth {
        return getMonth(LocalDate.of(year, month, 1))
    }

    override fun prevMonth(calendarMonth: CalendarMonth): CalendarMonth {
        if (calendarMonth.month == 1) {
            return getMonth(calendarMonth.year - 1, 12)
        }
        return getMonth(calendarMonth.year, calendarMonth.month - 1)
    }

    override fun nextMonth(calendarMonth: CalendarMonth): CalendarMonth {
        if (calendarMonth.month == 12) {
            return getMonth(calendarMonth.year + 1, 1)
        }
        return getMonth(calendarMonth.year, calendarMonth.month + 1)
    }

    private fun getMonth(firstDayLocalDate: LocalDate): CalendarMonth {
        val difference = firstDayLocalDate.dayOfWeek.value - firstDayOfWeek
        val daysFromStartOfWeekToFirstOfMonth =
            if (difference < 0) {
                difference + DAYS_IN_WEEK
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
    val daysFromStartOfWeekToFirstOfMonth: Int,
) {
    fun isValidDate(date: LocalDate, calendarModel: CalendarModel): Boolean = calendarModel.date <= date
}

@Immutable
class SeugiDatePickerColors(
    val containerColor: Color,
    val titleContentColor: Color,
    val titleImageColor: Color,
    val weeklyContainerColor: Color,
    val weeklyContentColor: Color,
    val activeDayContentColor: Color,
    val unActiveDayContentColor: Color,
    val selectDayContainerColor: Color,
    val selectDayContentColor: Color,
) {
    fun copy(
        containerColor: Color = this.containerColor,
        titleContentColor: Color = this.titleContentColor,
        titleImageColor: Color = this.titleImageColor,
        weeklyContainerColor: Color = this.weeklyContainerColor,
        weeklyContentColor: Color = this.weeklyContentColor,
        activeDayContentColor: Color = this.activeDayContentColor,
        unActiveDayContentColor: Color = this.unActiveDayContentColor,
        selectDayContainerColor: Color = this.selectDayContainerColor,
        selectDayContentColor: Color = this.selectDayContainerColor,
    ) = SeugiDatePickerColors(
        containerColor.takeOrElse { this.containerColor },
        titleContentColor.takeOrElse { this.titleContentColor },
        titleImageColor.takeOrElse { this.titleImageColor },
        weeklyContainerColor.takeOrElse { this.weeklyContainerColor },
        weeklyContentColor.takeOrElse { this.weeklyContentColor },
        activeDayContentColor.takeOrElse { this.activeDayContentColor },
        unActiveDayContentColor.takeOrElse { this.unActiveDayContentColor },
        selectDayContainerColor.takeOrElse { this.selectDayContainerColor },
        selectDayContentColor.takeOrElse { this.selectDayContentColor },
    )

    @Stable
    internal fun dayContentColor(selected: Boolean, active: Boolean): Color {
        return when {
            selected -> this.selectDayContentColor
            active -> this.activeDayContentColor
            else -> this.unActiveDayContentColor
        }
    }
}

interface SeugiDatePickerState {

    var selectedDate: LocalDate

    val weekdayNames: ImmutableList<String>

    var month: CalendarMonth

    fun prevMonth()

    fun nextMonth()

    fun validDate(date: LocalDate): Boolean
}

class SeugiDatePickerStateImpl(
    selectDate: LocalDate,
    locale: Locale,
    year: Int,
    month: Int,
) : SeugiDatePickerState {

    private val calendarModel = createCalendarModel(locale)

    override val weekdayNames: ImmutableList<String>
        get() = calendarModel.weekdayNames.toImmutableList()

    override var month: CalendarMonth by mutableStateOf(calendarModel.getMonth(year, month))

    override fun prevMonth() {
        month = calendarModel.prevMonth(month)
    }

    override fun nextMonth() {
        month = calendarModel.nextMonth(month)
    }

    override fun validDate(date: LocalDate): Boolean = month.isValidDate(date, calendarModel)

    override var selectedDate: LocalDate by mutableStateOf(selectDate)

    companion object {
        fun Saver(locale: Locale): Saver<SeugiDatePickerStateImpl, Any> = listSaver(
            save = {
                listOf(
                    it.selectedDate.toEpochDay(),
                    it.month.year,
                    it.month.month,
                )
            },
            restore = {
                SeugiDatePickerStateImpl(
                    selectDate = LocalDate.ofEpochDay(it[0] as Long),
                    locale = locale,
                    year = it[1] as Int,
                    month = it[2] as Int,
                )
            },
        )
    }
}

@Composable
fun rememberSeugiDatePickerState(selectDate: LocalDate = LocalDate.now()): SeugiDatePickerState {
    val locale = defaultLocale()

    return rememberSaveable(saver = SeugiDatePickerStateImpl.Saver(locale)) {
        SeugiDatePickerStateImpl(
            selectDate = selectDate,
            locale = locale,
            year = selectDate.year,
            month = selectDate.monthValue,
        )
    }
}

@Preview
@Composable
private fun SeugiDatePickerPreview() {
    val datePickerState = rememberSeugiDatePickerState()
    var isShowDialog by remember { mutableStateOf(true) }

    SeugiTheme {
        if (isShowDialog) {
            SeugiDatePickerDialog(
                state = datePickerState,
                isFixedSize = false,
                onDismissRequest = {
                    isShowDialog = false
                },
                onClickDate = { date, isValid ->
                    if (isValid) {
                        datePickerState.selectedDate = date
                    }
                },
                onClickSuccess = {
                    isShowDialog = false
                },
            )
        }
        SeugiFullWidthButton(onClick = { isShowDialog = true }, type = ButtonType.Primary, text = "test")
        SeugiDatePicker(
            state = datePickerState,
            isFixedSize = false,
            onClickDate = { date, isValid ->
                if (isValid) {
                    datePickerState.selectedDate = date
                }
            },
            onClickSuccess = {
            },
        )
    }
}

private const val MAX_CALENDAR_ROWS = 6
private const val DAYS_IN_WEEK = 7
