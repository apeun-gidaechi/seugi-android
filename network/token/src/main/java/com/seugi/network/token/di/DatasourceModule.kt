package com.seugi.network.token.di

import com.seugi.network.token.TokenDatasource
import com.seugi.network.token.datasource.TokenDatasourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DatasourceModule {

    @Singleton
    @Binds
    fun bindsTokenDatasource(tokenDatasourceImpl: TokenDatasourceImpl): TokenDatasource
}
