package com.seugi.data.schedule.di

import com.seugi.data.schedule.ScheduleRepository
import com.seugi.data.schedule.repository.ScheduleRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ScheduleRepositoryModule {

    @Binds
    @Singleton
    fun bindsScheduleRepository(scheduleRepositoryImpl: ScheduleRepositoryImpl): ScheduleRepository
}