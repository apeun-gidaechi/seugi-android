package com.seugi.network.oauth.di

import com.seugi.network.oauth.OauthDatasource
import com.seugi.network.oauth.datasource.OauthDatasourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
interface OauthDatasourceModule {

    @Binds
    @Singleton
    fun bindsOauthDataSource(oauthDatasourceImpl: OauthDatasourceImpl): OauthDatasource
}