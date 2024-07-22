package com.seugi.data.notice.di

import com.seugi.data.notice.NoticeRepository
import com.seugi.data.notice.repository.NoticeRepositoryImpl
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
    fun bindsNoticeRepository(noticeRepositoryImpl: NoticeRepositoryImpl): NoticeRepository
}
