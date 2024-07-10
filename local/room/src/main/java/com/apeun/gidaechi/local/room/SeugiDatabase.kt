package com.apeun.gidaechi.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.apeun.gidaechi.local.room.dao.TokenDao
import com.apeun.gidaechi.local.room.model.TokenEntity

@Database(
    entities = [
        TokenEntity::class,
    ],
    version = 14,
    exportSchema = false,
)
internal abstract class SeugiDatabase : RoomDatabase() {
    abstract fun tokenDao(): TokenDao
}
