package com.seugi.data.message.mapper

import com.seugi.data.core.model.MealType
import com.seugi.data.message.model.CatSeugiResponse

fun CatSeugiResponse.toModel(): String {
    return when (this) {
        is CatSeugiResponse.ETC -> {
            ""
        }
        is CatSeugiResponse.Meal -> {
            val breakfast = this.data.firstOrNull { it.mealType == MealType.BREAKFAST }
            val lunch = this.data.firstOrNull { it.mealType == MealType.LUNCH }
            val dinner = this.data.firstOrNull { it.mealType == MealType.DINNER }

            val visibleMessage = buildString {
                breakfast?.let {
                    append("- 오늘의 조식\n")
                    append(it.menu.joinToString(separator = "\n"))
                    if (lunch != null || dinner != null) {
                        append("\n\n")
                    }
                }

                lunch?.let {
                    append("- 오늘의 중식\n")
                    append(it.menu.joinToString(separator = "\n"))
                    if (dinner != null) {
                        append("\n\n")
                    }
                }

                dinner?.let {
                    append("- 오늘의 석식\n")
                    append(it.menu.joinToString(separator = "\n"))
                }

                if (isEmpty()) {
                    append("오늘의 급식 없습니다.")
                }
            }

            visibleMessage
        }
        is CatSeugiResponse.NotSupport -> {
            this.data
        }
        is CatSeugiResponse.Timetable -> {
            this.data.joinToString(separator = ", ") { it.time }
        }
    }
}
