package com.seugi.data.profile.di

import com.seugi.data.profile.ProfileRepository
import com.seugi.data.profile.repository.ProfileRepositoryImpl
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
