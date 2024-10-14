package com.seugi.network.personalchat.di

import com.seugi.network.personalchat.PersonalChatDataSource
import com.seugi.network.personalchat.datasource.PersonalChatDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {

    @Singleton
    @Binds
    fun providesPersonalChatDataSource(personalChatDataSourceImpl: PersonalChatDataSourceImpl): PersonalChatDataSource
}
