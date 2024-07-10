package com.apeun.gidaechi.local.room.di

import com.apeun.gidaechi.local.room.SeugiDatabase
import com.apeun.gidaechi.local.room.dao.TokenDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
internal object SeugiDaoModule {
    @Provides
    fun providesTokenDao(
        database: SeugiDatabase
    ): TokenDao = database.tokenDao()
}