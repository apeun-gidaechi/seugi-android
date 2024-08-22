package com.seugi.file.di

import com.seugi.file.FileDataSource
import com.seugi.file.datasource.FileDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface FileSourceModule {

    @Singleton
    @Binds
    fun providesGroupChatDataSource(fileDataSourceImpl: FileDataSourceImpl):FileDataSource
}