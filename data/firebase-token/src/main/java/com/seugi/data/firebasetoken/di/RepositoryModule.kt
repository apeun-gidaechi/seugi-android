package com.seugi.data.firebasetoken.di

import com.seugi.data.firebasetoken.FirebaseTokenRepository
import com.seugi.data.firebasetoken.repository.FirebaseTokenRepositoryImpl
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
    fun bindsFirebaseTokenRepository(firebaseTokenRepositoryImpl: FirebaseTokenRepositoryImpl): FirebaseTokenRepository
}
