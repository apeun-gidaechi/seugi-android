package com.seugi.network.notice.di

import com.seugi.network.notice.NoticeDataSource
import com.seugi.network.notice.datasource.NoticeDataSourceImpl
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
    fun bindsNoticeDataSource(noticeDataSourceImpl: NoticeDataSourceImpl): NoticeDataSource
}
