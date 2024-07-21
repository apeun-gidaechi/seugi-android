package com.seugi.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.seugi.local.room.dao.TokenDao
import com.seugi.local.room.model.TokenEntity

@Database(
    entities = [
        TokenEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
internal abstract class SeugiDatabase : RoomDatabase() {
    abstract fun tokenDao(): TokenDao
}
