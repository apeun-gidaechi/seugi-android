package com.apeun.gidaechi.network.workspace.di

import com.apeun.gidaechi.network.workspace.WorkspaceDatasource
import com.apeun.gidaechi.network.workspace.datasource.WorkspaceDatasourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface WorkspaceModule {

    @Binds
    @Singleton
    fun provideWorkspaceDataSource(workspaceDatasourceImpl: WorkspaceDatasourceImpl): WorkspaceDatasource
}
