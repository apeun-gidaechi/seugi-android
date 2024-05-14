package com.apeun.gidaechi.chatdetail.di

import com.apeun.gidaechi.chatdetail.ChatDetailRepository
import com.apeun.gidaechi.chatdetail.repository.ChatDetailRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    fun provideChatDetailRepository(chatDetailRepositoryImpl: ChatDetailRepositoryImpl): ChatDetailRepository
}