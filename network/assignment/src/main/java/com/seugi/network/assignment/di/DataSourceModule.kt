package com.seugi.network.assignment.di

import com.seugi.network.assignment.AssignmentDataSource
import com.seugi.network.assignment.datasource.AssignmentDataSourceImpl
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
    fun bindsAssignmentDataSource(taskDataSourceImpl: AssignmentDataSourceImpl): AssignmentDataSource
}
