package com.seugi.meal.widget.model

import com.seugi.data.core.model.MealModel
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
sealed interface MealWidgetState {

    @Serializable
    data object Loading : MealWidgetState

    @Serializable
    data object Error : MealWidgetState

    @Serializable
    data class Success(
        val timestamp: LocalDateTime,
        val data: List<com.seugi.data.core.model.MealModel>,
    ) : MealWidgetState

    //
    // 유저 타임 스템프 저장[0, 14323, 3123213, 464543]
}
