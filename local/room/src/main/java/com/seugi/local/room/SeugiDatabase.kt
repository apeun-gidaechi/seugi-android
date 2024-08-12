package com.seugi.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.seugi.local.room.dao.TokenDao
import com.seugi.local.room.dao.WorkspaceDao
import com.seugi.local.room.model.TokenEntity
import com.seugi.local.room.model.WorkspaceEntity

@Database(
    entities = [
        TokenEntity::class,
        WorkspaceEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
internal abstract class SeugiDatabase : RoomDatabase() {
    abstract fun tokenDao(): TokenDao
    abstract fun workspaceDao(): WorkspaceDao
}
