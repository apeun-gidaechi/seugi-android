package com.seugi.local.room.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.seugi.local.room.SeugiDatabase
import com.seugi.local.room.util.SeugiTable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object SeugiDatabaseModule {

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                """
                CREATE TABLE IF NOT EXISTS ${SeugiTable.WORKSPACE_TABLE} (
                    idx INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    workspaceId TEXT NOT NULL,
                    workspaceName TEXT NOT NULL,
                    workspaceUrl TEXT NOT NULL,
                )
            """,
            )
        }
    }

    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // 새로운 테이블을 생성하여 기존 데이터를 복사
            database.execSQL(
                """
                CREATE TABLE ${SeugiTable.WORKSPACE_TABLE}_new (
                    idx INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    workspaceId TEXT NOT NULL,
                    workspaceName TEXT NOT NULL
                )
            """,
            )
            database.execSQL(
                """
                INSERT INTO ${SeugiTable.WORKSPACE_TABLE}_new (idx, workspaceId, workspaceName)
                SELECT idx, workspaceId, workspaceName
                FROM ${SeugiTable.WORKSPACE_TABLE}
            """,
            )
            database.execSQL("DROP TABLE ${SeugiTable.WORKSPACE_TABLE}")
            database.execSQL(
                """
                ALTER TABLE ${SeugiTable.WORKSPACE_TABLE}_new
                RENAME TO ${SeugiTable.WORKSPACE_TABLE}
            """,
            )
        }
    }

    @Provides
    @Singleton
    fun providesSeugiDatabase(@ApplicationContext context: Context): SeugiDatabase = Room.databaseBuilder(
        context,
        SeugiDatabase::class.java,
        SeugiTable.SEUGI_TABLE,
    )
        .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
        .build()
}
