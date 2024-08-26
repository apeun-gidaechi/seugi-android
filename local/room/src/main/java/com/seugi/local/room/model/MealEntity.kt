package com.seugi.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.seugi.local.room.util.SeugiTable

@Entity(tableName = SeugiTable.MEAL_TABLE)
data class MealEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val workspaceId: String,
    val mealType: String,
    val menu: String,
    val calorie: String,
    val mealInfo: String,
    val mealDate: String,
)
