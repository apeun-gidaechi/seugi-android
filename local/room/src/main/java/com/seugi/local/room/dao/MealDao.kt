package com.seugi.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.seugi.local.room.base.BaseDao
import com.seugi.local.room.model.MealEntity
import com.seugi.local.room.model.TokenEntity
import com.seugi.local.room.util.SeugiTable
import java.time.LocalDate

@Dao
interface MealDao : BaseDao<MealEntity> {
    @Query("SELECT * FROM ${SeugiTable.MEAL_TABLE} WHERE workspaceId = :workspaceId AND mealDate = :date")
    suspend fun getDateMeals(workspaceId: String, date: String): List<MealEntity>?

    @Query("DELETE FROM ${SeugiTable.MEAL_TABLE}")
    suspend fun deleteToken()
}
