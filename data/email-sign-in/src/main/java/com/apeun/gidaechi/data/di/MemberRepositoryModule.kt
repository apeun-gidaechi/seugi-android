package com.apeun.gidaechi.data.di

import com.apeun.gidaechi.data.MemberRepository
import com.apeun.gidaechi.data.repository.MemberRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface MemberRepositoryModule {

    @Binds
    @Singleton
    fun provideEmailSignInRepository(emailSignInRepositoryImpl: MemberRepositoryImpl): MemberRepository
}
