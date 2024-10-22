package com.seugi.network.task.di

import com.seugi.network.task.TaskDataSource
import com.seugi.network.task.datasource.TaskDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    @Singleton
    fun bindsTaskDataSource(taskDataSourceImpl: TaskDataSourceImpl): TaskDataSource
}