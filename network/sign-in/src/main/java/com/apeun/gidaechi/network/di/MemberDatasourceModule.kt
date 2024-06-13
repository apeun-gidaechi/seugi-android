package com.apeun.gidaechi.network.di

import com.apeun.gidaechi.network.MemberDatasource
import com.apeun.gidaechi.network.datasource.MemberDatasourceImpl
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
