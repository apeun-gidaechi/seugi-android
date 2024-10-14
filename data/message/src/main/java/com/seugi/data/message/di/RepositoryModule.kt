package com.seugi.data.message.di

import com.seugi.data.message.MessageRepository
import com.seugi.data.message.repository.MessageRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Singleton
    @Binds
    fun provideMessageRepository(chatDetailRepositoryImpl: MessageRepositoryImpl): MessageRepository
}
