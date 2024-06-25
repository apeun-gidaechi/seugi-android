package com.apeun.gidaechi.network.workspace.di

import com.apeun.gidaechi.network.workspace.WorkspaceDatasource
import com.apeun.gidaechi.network.workspace.datasource.WorkspaceDatasourceImpl
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
    fun providesWorkSpaceDataSource(workspaceDataSourceImpl: WorkspaceDatasourceImpl): WorkspaceDatasource
}
