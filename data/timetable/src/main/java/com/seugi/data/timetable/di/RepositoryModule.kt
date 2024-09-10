package com.seugi.data.timetable.di

import com.seugi.data.timetable.TimetableRepository
import com.seugi.data.timetable.repository.TimetableRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindsTimetableRepository(timetableRepositoryImpl: TimetableRepositoryImpl): TimetableRepository
}