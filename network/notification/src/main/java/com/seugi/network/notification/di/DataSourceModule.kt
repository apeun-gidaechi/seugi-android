package com.seugi.network.notification.di

import com.seugi.network.notification.NotificationDataSource
import com.seugi.network.notification.datasource.NotificationDataSourceImpl
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
    fun bindsNoticeDataSource(noticeDataSourceImpl: NotificationDataSourceImpl): NotificationDataSource
}
