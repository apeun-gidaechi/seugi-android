package com.seugi.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.seugi.local.room.dao.FirebaseTokenDao
import com.seugi.local.room.dao.MealDao
import com.seugi.local.room.dao.TokenDao
import com.seugi.local.room.dao.WorkspaceDao
import com.seugi.local.room.dao.WorkspaceNotificationDao
import com.seugi.local.room.entity.FirebaseTokenEntity
import com.seugi.local.room.entity.MealEntity
import com.seugi.local.room.entity.TokenEntity
import com.seugi.local.room.entity.WorkspaceEntity
import com.seugi.local.room.entity.WorkspaceNotificationEntity
import com.seugi.local.room.util.LongListTypeConverter

@Database(
    entities = [
        TokenEntity::class,
        WorkspaceEntity::class,
        MealEntity::class,
        FirebaseTokenEntity::class,
        WorkspaceNotificationEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
@TypeConverters(LongListTypeConverter::class)
internal abstract class SeugiDatabase : RoomDatabase() {
    abstract fun tokenDao(): TokenDao
    abstract fun workspaceDao(): WorkspaceDao
    abstract fun mealDao(): MealDao
    abstract fun firebaseTokenDao(): FirebaseTokenDao
    abstract fun workspaceNotificationDao(): WorkspaceNotificationDao
}
