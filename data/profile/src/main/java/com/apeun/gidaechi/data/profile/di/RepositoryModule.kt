package com.apeun.gidaechi.data.profile.di

import com.apeun.gidaechi.data.profile.ProfileRepository
import com.apeun.gidaechi.data.profile.repository.ProfileRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun providesProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository
}
