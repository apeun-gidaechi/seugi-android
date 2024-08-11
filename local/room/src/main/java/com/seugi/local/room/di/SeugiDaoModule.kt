package com.seugi.local.room.di

import com.seugi.local.room.SeugiDatabase
import com.seugi.local.room.dao.TokenDao
import com.seugi.local.room.dao.WorkspaceDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object SeugiDaoModule {
    @Provides
    fun providesTokenDao(database: SeugiDatabase): TokenDao = database.tokenDao()
    @Provides
    fun providesWorkspaceDao(database: SeugiDatabase): WorkspaceDao = database.workspaceDao()
}
