package com.seugi.designsystem.component

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.seugi.designsystem.theme.Pretendard
import com.seugi.designsystem.theme.SeugiTheme
import kotlinx.collections.immutable.toImmutableList

@Composable
fun SeugiTimePicker(modifier: Modifier = Modifier, startTime: Int = 1, startMinute: Int = 0, onSelectTime: (hour: Int, minute: Int) -> Unit, onDismissRequest: () -> Unit) {
    val hours = (1..23).toImmutableList()
    val minutes = (0..59).toImmutableList()

    var chooseHour by remember { mutableIntStateOf(startTime) }
    var chooseMinute by remember { mutableIntStateOf(startMinute) }

    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Surface(
            modifier = modifier,
            color = SeugiTheme.colors.white,
            shape = RoundedCornerShape(28.dp),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
            ) {
                Text(
                    text = "외출 일시",
                    color = SeugiTheme.colors.black,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Box(modifier = Modifier) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth()
                            .height(44.dp)
                            .absoluteOffset(y = (-7).dp)
                            .background(
                                color = SeugiTheme.colors.gray300,
                                shape = RoundedCornerShape(10.dp),
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = ":",
                            color = SeugiTheme.colors.gray800,
                            style = MaterialTheme.typography.headlineMedium,
                        )
                    }
                    Row(
                        modifier = Modifier.align(Alignment.Center),
                    ) {
                        SeugiWheelRangePicker(
                            startIndex = startTime - 1,
                            items = hours,
                            textStyles = SeugiTimePickerTextStyles.defaultTextStyles(),
                            size = DpSize(36.dp, 199.dp),
                            colors = SeugiTimePickerColors.defaultColor(),
                            onScrollFinished = {
                                chooseHour = it % 24
                                null
                            },
                        )
                        Spacer(modifier = Modifier.width(55.dp))
                        SeugiWheelRangePicker(
                            startIndex = startMinute,
                            items = minutes,
                            textStyles = SeugiTimePickerTextStyles.defaultTextStyles(),
                            size = DpSize(36.dp, 199.dp),
                            colors = SeugiTimePickerColors.defaultColor(),
                            onScrollFinished = {
                                chooseMinute = it % 60
                                null
                            },
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                SeugiFullWidthButton(
                    onClick = {
                        onSelectTime(chooseHour, chooseMinute)
                    },
                    type = ButtonType.Primary,
                    text = "선택",
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun SeugiWheelRangePicker(
    modifier: Modifier = Modifier,
    startIndex: Int,
    items: List<Int>,
    rowCount: Int = 5,
    textStyles: SeugiTimePickerTextStyles,
    colors: SeugiTimePickerColors,
    size: DpSize = DpSize(128.dp, 128.dp),
    onScrollFinished: (snappedIndex: Int) -> Int? = { null },
) {
    val lazyListState = rememberLazyListState(startIndex)
    val isScrollInProgress = lazyListState.isScrollInProgress
    val flingBehavior = rememberSnapFlingBehavior(lazyListState)

    val centralItemIndex by remember {
        derivedStateOf { getCentralItemIndex(lazyListState)?.index ?: startIndex }
    }

    LaunchedEffect(isScrollInProgress) {
        if (!isScrollInProgress) {
            onScrollFinished(items[centralItemIndex % items.size])?.let {
                lazyListState.scrollToItem(it)
            }
        }
    }

    LazyColumn(
        state = lazyListState,
        flingBehavior = flingBehavior,
        modifier = modifier
            .height(size.height)
            .width(size.width),
        contentPadding = PaddingValues(vertical = size.height / rowCount * ((rowCount - 1) / 2)),
    ) {
        items(Int.MAX_VALUE) { index ->
            val active = (index == centralItemIndex)
            Text(
                modifier = Modifier
                    .height(size.height / rowCount)
                    .width(size.width),
                text = (items[index % items.size]).toTimeString(),
                color = colors.textColor(active),
                style = textStyles.textStyle(active),
                textAlign = TextAlign.Center,
            )
        }
    }
}

class SeugiWheelItemInfo(
    private val lazyListItem: LazyListItemInfo,
) {
    val index: Int get() = lazyListItem.index
    val offset: Int get() = lazyListItem.offset
    val size: Int get() = lazyListItem.size
}

private val Center: (LazyListState, SeugiWheelItemInfo) -> Int = { layout, item ->
    layout.startScrollOffset() + (layout.endScrollOffset() - layout.startScrollOffset() - item.size) / 2
}

val getCentralItemIndex: (LazyListState) -> SeugiWheelItemInfo? = { layout ->
    layout.layoutInfo.visibleItemsInfo.asSequence().map(::SeugiWheelItemInfo).lastOrNull { it.offset <= Center(layout, it) }
}

fun LazyListState.startScrollOffset() = 0

fun LazyListState.endScrollOffset() = this.layoutInfo.let { it.viewportEndOffset - it.afterContentPadding }

private fun Int.toTimeString(): String = this.toString().padStart(2, '0')

@Stable
object SeugiTimePickerDefaults {

    @Composable fun colors() = SeugiTimePickerColors.defaultColor()

    @Composable
    fun colors(
        activeTextColor: Color = Color.Unspecified,
        activeContainerColor: Color = Color.Unspecified,
        unActiveTextColor: Color = Color.Unspecified,
        unActiveContainerColor: Color = Color.Unspecified,
    ) = SeugiTimePickerColors.defaultColor().copy(
        activeTextColor = activeTextColor,
        activeContainerColor = activeContainerColor,
        unActiveTextColor = unActiveTextColor,
        unActiveContainerColor = unActiveContainerColor,
    )

    @Composable fun textStyles() = SeugiTimePickerTextStyles.defaultTextStyles()

    @Composable
    fun textStyles(activeTextStyle: TextStyle = TextStyle.Default, unActiveTextStyle: TextStyle = TextStyle.Default) = SeugiTimePickerTextStyles.defaultTextStyles().copy(
        activeTextStyle = activeTextStyle,
        unActiveTextStyle = unActiveTextStyle,
    )
}

@Immutable
class SeugiTimePickerColors(
    val activeTextColor: Color,
    val activeContainerColor: Color,
    val unActiveTextColor: Color,
    val unActiveContainerColor: Color,
) {

    fun copy(
        activeTextColor: Color = this.activeTextColor,
        activeContainerColor: Color = this.activeContainerColor,
        unActiveTextColor: Color = this.unActiveTextColor,
        unActiveContainerColor: Color = this.unActiveContainerColor,
    ) = SeugiTimePickerColors(
        activeTextColor.takeOrElse { this.activeTextColor },
        activeContainerColor.takeOrElse { this.activeContainerColor },
        unActiveTextColor.takeOrElse { this.unActiveTextColor },
        unActiveContainerColor.takeOrElse { this.unActiveContainerColor },
    )

    @Stable
    internal fun textColor(active: Boolean) = if (active) activeTextColor else unActiveTextColor

    @Stable
    internal fun containerColor(active: Boolean) = if (active) activeContainerColor else unActiveContainerColor

    companion object {

        @Composable
        @Stable
        internal fun defaultColor(): SeugiTimePickerColors = SeugiTimePickerColors(
            activeTextColor = SeugiTheme.colors.gray800,
            activeContainerColor = SeugiTheme.colors.transparent,
            unActiveTextColor = SeugiTheme.colors.gray600,
            unActiveContainerColor = SeugiTheme.colors.transparent,
        )
    }
}

@Immutable
class SeugiTimePickerTextStyles(
    private val activeTextStyle: TextStyle,
    private val unActiveTextStyle: TextStyle,
) {

    fun copy(activeTextStyle: TextStyle = this.activeTextStyle, unActiveTextStyle: TextStyle = this.unActiveTextStyle) = SeugiTimePickerTextStyles(
        activeTextStyle = activeTextStyle,
        unActiveTextStyle = unActiveTextStyle,
    )

    @Stable
    internal fun textStyle(active: Boolean) = if (active) this.activeTextStyle else unActiveTextStyle

    companion object {
        @Stable
        internal fun defaultTextStyles(): SeugiTimePickerTextStyles = SeugiTimePickerTextStyles(
            activeTextStyle = Pretendard.headlineMedium,
            unActiveTextStyle = Pretendard.titleLarge,
        )
    }
}

@Preview
@Composable
private fun SeugiTimePickerPreview() {
    var isShowDialog by remember { mutableStateOf(true) }
    SeugiTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SeugiTheme.colors.white),
        ) {
            if (isShowDialog) {
                SeugiTimePicker(
                    modifier = Modifier,
                    onSelectTime = { hour, minute ->
                        Log.d("TAG", "SeugiTimePickerPreview: $hour, $minute")
                        isShowDialog = false
                    },
                    onDismissRequest = {
                        isShowDialog = false
                    },
                )
            }
        }
    }
}
