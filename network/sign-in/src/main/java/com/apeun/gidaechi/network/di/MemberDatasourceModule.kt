package com.apeun.gidaechi.network.di

import com.apeun.gidaechi.network.EmailSignInDatasource
import com.apeun.gidaechi.network.EmailSignUpDatasource
import com.apeun.gidaechi.network.datasource.EmailSignInDatasourceImpl
import com.apeun.gidaechi.network.datasource.EmailSignUpDatasourceImpl
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
    fun provideEmailSignInDataSource(emailSignInDatasourceImpl: EmailSignInDatasourceImpl): EmailSignInDatasource

    @Binds
    @Singleton
    fun provideEmailSignUpDatasource(emailSignUpDatasourceImpl: EmailSignUpDatasourceImpl): EmailSignUpDatasource
}
