package com.seugi.data.message.model

import com.seugi.data.core.model.MealModel
import com.seugi.data.core.model.NotificationModel
import com.seugi.data.core.model.TimetableModel
import kotlinx.collections.immutable.ImmutableList


sealed interface CatSeugiResponse {
    data class Meal(
        val data: ImmutableList<MealModel>
    ): CatSeugiResponse


    data class Timetable(
        val data: ImmutableList<TimetableModel>
    ): CatSeugiResponse

    data class ETC(
        val data: String
    ): CatSeugiResponse

    data class NotSupport(
        val data: String
    ): CatSeugiResponse

    data class Picking(
        val data: String
    ): CatSeugiResponse

    data class Team(
        val data: String
    ): CatSeugiResponse

    data class Notification(
        val data: ImmutableList<NotificationModel>
    ): CatSeugiResponse
}