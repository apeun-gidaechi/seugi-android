package com.apeun.gidaechi.network.workspace.di

import com.apeun.gidaechi.network.workspace.WorkSpaceDataSource
import com.apeun.gidaechi.network.workspace.datasource.WorkSpaceDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {

    @Binds
    @Singleton
    fun providesWorkSpaceDataSource(workSpaceDataSourceImpl: WorkSpaceDataSourceImpl): WorkSpaceDataSource
}