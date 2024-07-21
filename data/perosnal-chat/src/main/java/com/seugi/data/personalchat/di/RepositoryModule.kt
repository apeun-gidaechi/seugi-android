package com.seugi.data.personalchat.di

import com.seugi.data.personalchat.PersonalChatRepository
import com.seugi.data.personalchat.repository.PersonalChatRepositoryImpl
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
    fun providesPersonalChatRepository(personalChatRepositoryImpl: PersonalChatRepositoryImpl): PersonalChatRepository
}
