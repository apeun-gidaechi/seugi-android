package com.seugi.network.core.response

import kotlinx.datetime.LocalDate

/**
 * It use Date "yyyyMMdd" Format
 */
class FakeLocalDateResponse(
    val data: String,
) {
    fun getLocalDate() = LocalDate(data.substring(0, 4).toInt(), data.substring(4, 6).toInt(), data.substring(6, 8).toInt())
}
