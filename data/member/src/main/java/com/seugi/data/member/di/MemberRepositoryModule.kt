package com.seugi.data.member.di

import com.seugi.data.member.MemberRepository
import com.seugi.data.member.repository.MemberRepositoryImpl
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
    fun provideMemberRepository(memberRepositoryImpl: MemberRepositoryImpl): MemberRepository
}
