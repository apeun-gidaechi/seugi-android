package com.apeun.gidaechi.common.utiles

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

fun LocalDateTime.toDeviceLocalDateTime(): LocalDateTime = this.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()

fun LocalDateTime.toFullFormatString(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 E요일", Locale.KOREAN)
    return this.toDeviceLocalDateTime().format(formatter)
}

fun LocalDateTime.toShortString(): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return this.toDeviceLocalDateTime().format(formatter)
}

fun LocalDateTime.toAmShortString(): String {
    val time = this.toDeviceLocalDateTime()
    val isAm = time.hour < 12
    return (if (isAm) "오전" else "오후") + " " + time.minusHours(if (!isAm && time.hour != 12) 12 else 0).format(DateTimeFormatter.ofPattern("HH:mm"))
}

fun LocalDateTime.toTimeString(): String {
    val time = this.toDeviceLocalDateTime()

    val dateFormatter = DateTimeFormatter.ofPattern("M월 d일")
    val formattedDate = time.format(dateFormatter)
    val dayOfWeek = time.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN)

    val result = "$formattedDate $dayOfWeek"
    return result
}