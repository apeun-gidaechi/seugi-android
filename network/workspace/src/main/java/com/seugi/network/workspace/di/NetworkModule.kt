package com.seugi.network.workspace.di

import com.seugi.network.workspace.WorkspaceDataSource
import com.seugi.network.workspace.datasource.WorkspaceDataSourceImpl
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
    fun providesWorkSpaceDataSource(workspaceDataSourceImpl: WorkspaceDataSourceImpl): WorkspaceDataSource
}
