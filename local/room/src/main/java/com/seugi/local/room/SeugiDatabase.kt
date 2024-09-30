package com.seugi.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.seugi.local.room.dao.FirebaseTokenDao
import com.seugi.local.room.dao.MealDao
import com.seugi.local.room.dao.TokenDao
import com.seugi.local.room.dao.WorkspaceDao
import com.seugi.local.room.model.FirebaseTokenEntity
import com.seugi.local.room.model.MealEntity
import com.seugi.local.room.model.TokenEntity
import com.seugi.local.room.model.WorkspaceEntity

@Database(
    entities = [
        TokenEntity::class,
        WorkspaceEntity::class,
        MealEntity::class,
        FirebaseTokenEntity::class
    ],
    version = 1,
    exportSchema = false,
)
internal abstract class SeugiDatabase : RoomDatabase() {
    abstract fun tokenDao(): TokenDao
    abstract fun workspaceDao(): WorkspaceDao
    abstract fun mealDao(): MealDao
    abstract fun firebaseTokenDao(): FirebaseTokenDao
}
