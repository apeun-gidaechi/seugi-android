package com.seugi.data.file.di

import com.seugi.data.file.FileRepository
import com.seugi.data.file.repository.FileRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Singleton
    @Binds
    fun provideFileRepository(fileRepositoryImpl: FileRepositoryImpl): FileRepository
}
