package com.apeun.gidaechi.network.message.di

import com.apeun.gidaechi.network.message.datasource.MessageDataSourceImpl
import com.apeun.gidaechi.network.message.MessageDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface MessageDataSourceModule {

    @Binds
    @Singleton
    fun provideMessageDataSource(chatDetailDataSourceImpl: MessageDataSourceImpl): MessageDataSource
}