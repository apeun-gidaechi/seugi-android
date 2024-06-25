package com.apeun.gidaechi.data.workspace.module

import com.apeun.gidaechi.data.workspace.WorkSpaceRepository
import com.apeun.gidaechi.data.workspace.repository.WorkSpaceRepositoryImpl
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
    fun providesWorkSpaceRepository(workSpaceRepositoryImpl: WorkSpaceRepositoryImpl): WorkSpaceRepository
}
