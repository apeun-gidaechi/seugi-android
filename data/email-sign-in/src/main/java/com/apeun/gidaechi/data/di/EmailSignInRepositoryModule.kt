package com.apeun.gidaechi.data.di

import com.apeun.gidaechi.data.EmailSignInRepository
import com.apeun.gidaechi.data.repository.EmailSignInRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface EmailSignInRepositoryModule {

    @Binds
    fun provideEmailSignInRepository(emailSignInRepositoryImpl: EmailSignInRepositoryImpl): EmailSignInRepository
}