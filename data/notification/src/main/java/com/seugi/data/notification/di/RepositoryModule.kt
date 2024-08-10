package com.seugi.data.notification.di

import com.seugi.data.notification.NotificationRepository
import com.seugi.data.notification.repository.NotificationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindsNotificationRepository(noticeRepositoryImpl: NotificationRepositoryImpl): NotificationRepository
}
