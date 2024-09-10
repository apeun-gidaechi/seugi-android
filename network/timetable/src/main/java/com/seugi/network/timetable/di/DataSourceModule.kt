package com.seugi.network.timetable.di

import com.seugi.network.timetable.TimetableDataSource
import com.seugi.network.timetable.datasource.TimetableDataSourceImpl
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
    fun bindsTimetableDataSource(timetableDataSourceImpl: TimetableDataSourceImpl): TimetableDataSource
}
