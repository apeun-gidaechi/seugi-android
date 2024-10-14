package com.seugi.network.schedule.di

import com.seugi.network.schedule.ScheduleDataSource
import com.seugi.network.schedule.datasource.ScheduleDataSourceImpl
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
    fun bindsScheduleDataSource(scheduleDataSourceImpl: ScheduleDataSourceImpl): ScheduleDataSource
}