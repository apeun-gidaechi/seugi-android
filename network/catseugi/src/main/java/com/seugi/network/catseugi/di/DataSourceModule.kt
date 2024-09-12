package com.seugi.network.catseugi.di

import com.seugi.network.catseugi.CatSeugiDataSource
import com.seugi.network.catseugi.datasource.CatSeugiDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    @Singleton
    fun bindsCatSeugiDataSource(catSeugiDataSourceImpl: CatSeugiDataSourceImpl): CatSeugiDataSource
}