package com.apeun.gidaechi.network.chatdetail.di

import com.apeun.gidaechi.network.chatdetail.datasource.ChatDetailDataSourceImpl
import com.apeun.gidaechi.network.chatdetail.ChatDetailDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface ChatDetailDataSourceModule {

    @Binds
    @Singleton
    fun provideChatDetailDataSource(chatDetailDataSourceImpl: ChatDetailDataSourceImpl): ChatDetailDataSource
}