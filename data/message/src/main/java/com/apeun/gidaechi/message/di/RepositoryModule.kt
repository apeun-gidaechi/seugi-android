package com.apeun.gidaechi.message.di

import com.apeun.gidaechi.message.MessageRepository
import com.apeun.gidaechi.message.repository.MessageRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    fun provideMessageRepository(chatDetailRepositoryImpl: MessageRepositoryImpl): MessageRepository
}
