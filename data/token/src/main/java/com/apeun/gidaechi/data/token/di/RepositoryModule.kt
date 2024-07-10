package com.apeun.gidaechi.data.token.di

import com.apeun.gidaechi.data.token.TokenRepository
import com.apeun.gidaechi.data.token.repository.TokenRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Singleton
    @Binds
    fun provideTokenRepository(tokenRepositoryImpl: TokenRepositoryImpl): TokenRepository
}