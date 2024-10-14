package com.seugi.network.message.di

import com.seugi.network.message.MessageDataSource
import com.seugi.network.message.datasource.MessageDataSourceImpl
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
