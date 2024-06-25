package com.apeun.gidaechi.data.workspace.di

import com.apeun.gidaechi.data.workspace.WorkspaceRepository
import com.apeun.gidaechi.data.workspace.repository.WorkspaceRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Singleton
    @Binds
    fun provideWorkspaceRepository(workspaceRepositoryImpl: WorkspaceRepositoryImpl): WorkspaceRepository
}
