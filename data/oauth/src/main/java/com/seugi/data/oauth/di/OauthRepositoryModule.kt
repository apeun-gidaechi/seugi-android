package com.seugi.data.oauth.di

import com.seugi.data.oauth.OauthRepository
import com.seugi.data.oauth.repository.OauthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface OauthRepositoryModule {

    @Singleton
    @Binds
    fun bindsOauthRepository(oauthRepositoryImpl: OauthRepositoryImpl): OauthRepository
}
