package com.seugi.data.meal.response

import com.seugi.common.utiles.ImmutableListSerializer
import kotlinx.collections.immutable.ImmutableList
import kotlinx.datetime.LocalDate
import kotlinx.datetime.serializers.LocalDateIso8601Serializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class MealModel(
    val id: Long,
    val workspaceId: String,
    val mealType: MealType,
    @Serializable(with = ImmutableListSerializer::class) val menu: ImmutableList<String>,
    val calorie: String,
    @Serializable(with = ImmutableListSerializer::class) val mealInfo: ImmutableList<String>,
    @Serializable(with = LocalDateIso8601Serializer::class) val mealDate: LocalDate,
)


