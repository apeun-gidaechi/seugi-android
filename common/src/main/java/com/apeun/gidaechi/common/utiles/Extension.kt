package com.apeun.gidaechi.common.utiles

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun LocalDateTime.toFullFormatString(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 E요일", Locale.KOREAN)
    return this.format(formatter)
}

fun LocalDateTime.toShortString(): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return this.format(formatter)
}

fun LocalDateTime.toAmShortString(): String {
    return (if (hour < 12) "오전" else "오후") + " " + this.toShortString()
}
