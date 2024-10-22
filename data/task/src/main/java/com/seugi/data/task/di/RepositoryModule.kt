package com.seugi.data.task.di

import com.seugi.data.task.TaskRepository
import com.seugi.data.task.repository.TaskRepositoryImpl
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
    fun bindsTaskRepository(taskRepositoryImpl: TaskRepositoryImpl): TaskRepository
}