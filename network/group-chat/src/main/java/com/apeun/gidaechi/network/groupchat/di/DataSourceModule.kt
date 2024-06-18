package com.apeun.gidaechi.network.groupchat.di

import com.apeun.gidaechi.network.groupchat.GroupChatDataSource
import com.apeun.gidaechi.network.groupchat.datasource.GroupChatDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {
    @Singleton
    @Binds
    fun providesGroupChatDataSource(groupChatDataSourceImpl: GroupChatDataSourceImpl): GroupChatDataSource
}
