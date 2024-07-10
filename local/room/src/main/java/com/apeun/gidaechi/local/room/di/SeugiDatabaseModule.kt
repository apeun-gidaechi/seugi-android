package com.apeun.gidaechi.local.room.di

import android.content.Context
import androidx.room.Room
import com.apeun.gidaechi.local.room.SeugiDatabase
import com.apeun.gidaechi.local.room.util.SeugiTable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal object SeugiDatabaseModule {
    @Provides
    @Singleton
    fun providesSeugiDatabase(
        @ApplicationContext context: Context
    ):SeugiDatabase = Room.databaseBuilder(
        context,
        SeugiDatabase::class.java,
        SeugiTable.SEUGI_TABLE
    ).build()
}