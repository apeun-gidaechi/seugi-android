package com.apeun.gidaechi.data.groupchat.di

import com.apeun.gidaechi.data.groupchat.GroupChatRepository
import com.apeun.gidaechi.data.groupchat.repository.GroupChatRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun providesGroupChatRepository(groupChatRepositoryImpl: GroupChatRepositoryImpl): GroupChatRepository
}