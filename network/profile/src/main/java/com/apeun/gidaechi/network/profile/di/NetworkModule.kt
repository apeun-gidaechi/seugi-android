package com.apeun.gidaechi.network.profile.di

import com.apeun.gidaechi.network.profile.ProfileDataSource
import com.apeun.gidaechi.network.profile.datasource.ProfileDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
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