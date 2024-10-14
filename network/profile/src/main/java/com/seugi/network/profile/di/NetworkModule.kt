package com.seugi.network.profile.di

import com.seugi.network.profile.ProfileDataSource
import com.seugi.network.profile.datasource.ProfileDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class NetworkModule {

    @Binds
    @Singleton
    abstract fun providesProfileDataSource(profileDataSourceImpl: ProfileDataSourceImpl): ProfileDataSource
}
