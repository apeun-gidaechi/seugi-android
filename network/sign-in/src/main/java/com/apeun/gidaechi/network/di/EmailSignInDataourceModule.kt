package com.apeun.gidaechi.network.di

import com.apeun.gidaechi.network.EmailSignInDatasource
import com.apeun.gidaechi.network.datasource.EmailSignInDatasourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface EmailSignInDataourceModule {

    @Binds
    @Singleton
    fun provideEmailSignInDataSource(emailSignInDatasourceImpl: EmailSignInDatasourceImpl): EmailSignInDatasource
}
