package com.seugi.network.di

import com.seugi.network.MemberDatasource
import com.seugi.network.datasource.MemberDatasourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface MemberDatasourceModule {

    @Binds
    @Singleton
    fun provideMemberDataSource(memberDatasourceImpl: MemberDatasourceImpl): MemberDatasource
}
