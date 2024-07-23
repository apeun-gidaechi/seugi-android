package com.seugi.data.token.di

import com.seugi.data.token.TokenRepository
import com.seugi.data.token.repository.TokenRepositoryImpl
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
    fun provideTokenRepository(tokenRepositoryImpl: TokenRepositoryImpl): TokenRepository
}
