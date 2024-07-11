package com.apeun.gidaechi.network.notice.di

import com.apeun.gidaechi.network.notice.NoticeDataSource
import com.apeun.gidaechi.network.notice.datasource.NoticeDataSourceImpl
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
    fun providesNoticeDataSource(noticeDataSourceImpl: NoticeDataSourceImpl): NoticeDataSource

}