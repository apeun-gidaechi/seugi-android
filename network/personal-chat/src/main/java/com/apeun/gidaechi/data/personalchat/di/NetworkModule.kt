package com.seugi.data.personalchat.di

import com.seugi.data.personalchat.PersonalChatDataSource
import com.seugi.data.personalchat.datasource.PersonalChatDataSourceImpl
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
