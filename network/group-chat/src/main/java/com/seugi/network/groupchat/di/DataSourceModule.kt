package com.seugi.network.groupchat.di

import com.seugi.network.groupchat.GroupChatDataSource
import com.seugi.network.groupchat.datasource.GroupChatDataSourceImpl
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
