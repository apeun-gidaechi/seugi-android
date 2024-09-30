package com.seugi.data.firebase_token.di

import com.seugi.data.firebase_token.FirebaseTokenRepository
import com.seugi.data.firebase_token.repository.FirebaseTokenRepositoryImpl
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
